package cz.fi.muni.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Order} entity.
 */
public interface OrderService {

    void createOrder(Order order);

    /**
     * Get all saved orders belonging to the given user.
     */
    List<Order> getOrdersByCustomer(Customer customer);

    /**
     * Get all saved orders belonging to the given employee.
     */
    List<Order> getOrdersByEmployee(Employee employee);

    /**
     * Get all orders saved within last month that have the given state.
     */
    List<Order> getAllOrdersLastMonth(OrderState state);

    /**
     * Get all orders saved within last week that have the given state.
     */
    List<Order> getAllOrdersLastWeek(OrderState state);

    /**
     * Get all orders saved within last day that have the given state.
     */
    List<Order> getAllOrdersLastDay(OrderState state);


    /**
     * Get all orders with the given state.
     */
    List<Order> getOrdersByState(OrderState state);


    List<Order> findAllOrders();

    void shipOrder(Order order);

    void finishOrder(Order order);

    void cancelOrder(Order order);

    Order findOrderById(Long id);

    Integer getTotalPrice(long orderId);
}

