package cz.muni.fi.pa165.deliveryservice.service.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.OrderCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.OrderDTO;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.facade.OrderFacade;
import cz.muni.fi.pa165.deliveryservice.api.service.util.*;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.OrderService;
import cz.muni.fi.pa165.deliveryservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * @author Matej Leško
 */
@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade {

    @Inject
    private OrderService orderService;

    @Inject
    private ProductService productService;

    @Inject
    private BeanMappingService beanMappingService;

    //TODO - refactor this to use custom builder
    @Override
    public long createOrder(OrderCreateDTO order) throws AlreadyExistsException {
        Order pOrder = new Order();
        pOrder.setCreated(LocalDate.now());

        pOrder.setState(OrderState.RECEIVED);

        Customer customer = beanMappingService.mapTo(order.getCustomer(), Customer.class);
        pOrder.setCustomer(customer);

        List<Product> products = beanMappingService.mapTo(order.getProducts(), Product.class);
        pOrder.setProducts(products);

        orderService.createOrder(pOrder);
        return pOrder.getId();
    }

    @Override
    public List<OrderDTO> findByCustomer(long customerId) {
        List<OrderDTO> list = null;
        try {
            list = beanMappingService.mapTo(
                    orderService.findByCustomer(customerId), OrderDTO.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderDTO> findByEmployee(long employeeId) {
        return beanMappingService.mapTo(
                orderService.findByEmployee(employeeId), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastMonthWithState(OrderState state) {
        return beanMappingService.mapTo(
                orderService.getOrdersLastMonthWithState(state), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastWeekWithState(OrderState state) {
        return beanMappingService.mapTo(
                orderService.getOrdersLastWeekWithState(state), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastDayWithState(OrderState state) {
        return beanMappingService.mapTo(
                orderService.getOrdersLastDayWithState(state), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastMonth() {
        return beanMappingService.mapTo(
                orderService.getOrdersLastMonth(), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastWeek() {
        return beanMappingService.mapTo(
                orderService.getOrdersLastWeek(), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersLastDay() {
        return beanMappingService.mapTo(
                orderService.getOrdersLastDay(), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersWithState(OrderState state) {
        return beanMappingService.mapTo(
                orderService.getOrdersWithState(state), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersCreatedBetween(LocalDate start, LocalDate end) {
        return beanMappingService.mapTo(
                orderService.getOrdersCreatedBetween(start, end), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> getOrdersCreatedBetweenWithState(LocalDate start, LocalDate end, OrderState state) {
        return beanMappingService.mapTo(
                orderService.getOrdersCreatedBetweenWithState(start, end, state), OrderDTO.class
        );
    }

    @Override
    public List<OrderDTO> findAll() {
        return beanMappingService.mapTo(
                orderService.findAll(), OrderDTO.class
        );
    }

    @Override
    public void shipOrder(OrderDTO order) throws ShippedOrderException, CancelledOrderException, ClosedOrderException, FailedUpdateException, NotFoundException {
        Order pOrder = beanMappingService.mapTo(order, Order.class);
        orderService.shipOrder(pOrder);
    }

    @Override
    public void closeOrder(OrderDTO order) throws UnprocessedOrderException, CancelledOrderException, ClosedOrderException, FailedUpdateException, NotFoundException {
        Order pOrder = beanMappingService.mapTo(order, Order.class);
        orderService.closeOrder(pOrder);
    }

    @Override
    public void cancelOrder(OrderDTO order) throws CancelledOrderException, ClosedOrderException, NotFoundException, FailedUpdateException {
        Order pOrder = beanMappingService.mapTo(order, Order.class);
        orderService.cancelOrder(pOrder);
        pOrder = orderService.findById(order.getId());
        orderService.updateOrder(pOrder);
    }

    @Override
    public OrderDTO findById(long id) throws NotFoundException {
        return beanMappingService.mapTo(orderService.findById(id), OrderDTO.class);
    }

    @Override
    public int getTotalPrice(long id) throws NotFoundException {
        return orderService.getTotalPrice(id);
    }
    @Override
    public int getTotalWeight(long id) throws NotFoundException {
        return orderService.getTotalWeight(id);
    }

    @Override
    public void updateOrder(OrderDTO updatedOrder) throws FailedUpdateException {
        Order mappedProduct = beanMappingService.mapTo(updatedOrder, Order.class);
        orderService.updateOrder(mappedProduct);
    }

}
