package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Order} entity.
 */
public interface OrderService {

    /**
     * Create order in the system
     * @param order
     */
    void createOrder(Order order);

    /**
     * Get all saved orders belonging to the given user
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


    /**
     * Find all orders in the system and return them
     * @return list of orders
     */
    List<Order> findAll();

    /**
     * Ship order to the customer
     * @param order
     */
    void shipOrder(Order order);

    /**
     * Let customer finish the order and store it to the sytem
     * @param order
     */
    void finishOrder(Order order);

    /**
     * Cancels order made in system
     * @param order
     */
    void deleteOrder(Order order);

    /**
     * Check order in the system by its ID
     * @param id unique identifier for order
     * @return
     */
    Order findOrderById(long id);

    /**
     * Return total price of the order.
     * Order can have additional expenses, use it for final checkout.
     * @param id unique identifier for order
     * @return
     */
    Integer getTotalPrice(long id);
}

