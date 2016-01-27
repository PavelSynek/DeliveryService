package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.service.util.*;
import cz.muni.fi.pa165.deliveryservice.persist.dao.OrderDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Matej Leško on 2015-11-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: DeliveryService
 */


/**
 * Implementation of order service contract.
 *
 * @author Matej Leško
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    @PostConstruct
    public void initDBAccessHandlers() {
        orderDao.initDBAccessHandlers();
    }

    @Override
    public void createOrder(Order order) throws AlreadyExistsException {
        OrderState backup = order.getState();
        try {
            order.setState(OrderState.PROCESSING);
            orderDao.create(order);
        } catch (ViolentDataAccessException e) {
            order.setState(backup);
            throw new AlreadyExistsException("Order: " + order.getId() + " already exists");
        }

    }

    @Override
    public List<Order> findByCustomer(long customerId) throws NotFoundException {
        List<Order> list;
        try {
            list = orderDao.findByCustomer(customerId);
        } catch (ViolentDataAccessException e) {
            throw new NotFoundException("Customer: " + customerId + " was not found");
        }
        return list;
    }

    @Override
    public List<Order> findByEmployee(long employeeId) {
        return orderDao.findByEmployee(employeeId);
    }

    @Override
    public List<Order> getOrdersLastMonthWithState(OrderState state) {
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetweenWithState(start, end, state);
    }

    @Override
    public List<Order> getOrdersLastWeekWithState(OrderState state) {
        LocalDate start = LocalDate.now().minusWeeks(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetweenWithState(start, end, state);
    }

    @Override
    public List<Order> getOrdersLastDayWithState(OrderState state) {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetweenWithState(start, end, state);
    }

    @Override
    public List<Order> getOrdersLastMonth() {
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetween(start, end);
    }

    @Override
    public List<Order> getOrdersLastWeek() {
        LocalDate start = LocalDate.now().minusWeeks(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetween(start, end);
    }

    @Override
    public List<Order> getOrdersLastDay() {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        return orderDao.getOrdersCreatedBetween(start, end);
    }

    @Override
    public List<Order> getOrdersWithState(OrderState state) {
        return orderDao.getOrdersWithState(state);
    }

    @Override
    public List<Order> getOrdersCreatedBetween(LocalDate start, LocalDate end) {
        return orderDao.getOrdersCreatedBetween(start, end);
    }

    @Override
    public List<Order> getOrdersCreatedBetweenWithState(LocalDate start, LocalDate end, OrderState state) {
        return orderDao.getOrdersCreatedBetweenWithState(start, end, state);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(long id) throws NotFoundException {
        Order found;
        try {
            found = orderDao.findById(id);
        } catch (ViolentDataAccessException e) {
            throw new NotFoundException("Order: " + id + " does not exists");
        }
        return found;
    }

    @Override
    public int getTotalPrice(long id) throws NotFoundException {
        int price = 0;
        Order order;

        try {
            order = orderDao.findById(id);
        } catch (ViolentDataAccessException e) {
            throw new NotFoundException("Order: " + id + " does not exists");
        }

        for (Product product : order.getProducts()) {
            price += product.getPrice();
        }

        return price;
    }

    @Override
    public int getTotalWeight(long id) throws NotFoundException {
        int weight = 0;
        Order order;

        try {
            order = orderDao.findById(id);
        } catch (ViolentDataAccessException e) {
            throw new NotFoundException("Order: " + id + " does not exists");
        }

        for (Product product : order.getProducts()) {
            weight += product.getWeight();
        }

        return weight;
    }

    @Override
    public void updateOrder(Order updatedOrder) throws FailedUpdateException {
        try {
            orderDao.update(updatedOrder);
        } catch (ViolentDataAccessException e) {
            throw new FailedUpdateException();
        }
    }

    public void shipOrder(Order o)
            throws ShippedOrderException, CancelledOrderException, ClosedOrderException, FailedUpdateException, NotFoundException {
        Order order = findById(o.getId());
        if (order.getState().equals(OrderState.PROCESSING))
            order.setState(OrderState.SHIPPED);
        else if (order.getState().equals(OrderState.SHIPPED))
            throw new ShippedOrderException("Order: " + order.getId() + " was already shipped");
        else if (order.getState().equals(OrderState.CANCELLED))
            throw new CancelledOrderException("Order: " + order.getId() + " is cancelled");
        else if (order.getState().equals(OrderState.CLOSED))
            throw new ClosedOrderException("Order: " + order.getId() + " is closed");
        updateOrder(order);
    }

    @Override
    public void closeOrder(Order o)
            throws UnprocessedOrderException, CancelledOrderException, ClosedOrderException, FailedUpdateException, NotFoundException {
        Order order = findById(o.getId());
        if (order.getState().equals(OrderState.SHIPPED))
            order.setState(OrderState.CLOSED);
        else if (order.getState().equals(OrderState.CLOSED))
            throw new ClosedOrderException("Order: " + order.getId() + " was already closed");
        else if (order.getState().equals(OrderState.CANCELLED))
            throw new CancelledOrderException("Order: " + order.getId() + " is cancelled");
        else if (order.getState().equals(OrderState.PROCESSING))
            throw new UnprocessedOrderException("Order: " + order.getId() + " is closed");
        updateOrder(order);
    }

    @Override
    public void cancelOrder(Order o) throws CancelledOrderException, ClosedOrderException, FailedUpdateException, NotFoundException {
        Order order = findById(o.getId());
        if (order.getState().equals(OrderState.PROCESSING) || order.getState().equals(OrderState.SHIPPED))
            order.setState(OrderState.CANCELLED);
        else if (order.getState().equals(OrderState.CLOSED))
            throw new ClosedOrderException("Order: " + order.getId() + " is already closed");
        else if (order.getState().equals(OrderState.CANCELLED))
            throw new CancelledOrderException("Order: " + order.getId() + " is cancelled");
        updateOrder(order);
    }
}
