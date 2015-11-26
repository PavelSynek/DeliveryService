package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.dao.OrderDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.service.util.AlreadyShippedException;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Matej Leško on 2015-11-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Override
    public void createOrder(Order order) {
        orderDao.create(order);
    }

    @Override
    public List<Order> findByCustomer(long customerId) {
        return orderDao.findByCustomer(customerId);
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
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public int getTotalPrice(long id) {
        int price = 0;
        Order order = orderDao.findById(id);
        for (Product product:order.getProducts()) {
            price += product.getPrice();
        }
        return price;
    }

    @Override
    public void shipOrder(Order order) {
        if (order.getState().equals(OrderState.DONE))
            order.setState(OrderState.SHIPPED);
        if (order.getState().equals(OrderState.DONE))
            throw new AlreadyShippedException("Order: " + order.getId() + " was already shipped");
    }

    @Override
    public void finishOrder(Order order) {

    }

    @Override
    public void deleteOrder(Order order) {

    }
}
