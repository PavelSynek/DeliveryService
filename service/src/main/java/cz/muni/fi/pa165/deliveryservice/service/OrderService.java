package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * An interface that defines a service access to the {@link Order} entity.
 *
 * @author Matej Leško
 */
@Service
public interface OrderService {

    /**
     * Create order in the system
     *
     * @param order entity representing Order in real world
     */
    void createOrder(Order order);

    /**
     * List orders of specific customer
     *
     * @param customerId Id of the customer.
     * @return list of entities representing orders of specific customer.
     * @see List
     */
    List<Order> findByCustomer(long customerId);

    /**
     * List all orders delivered by employee
     *
     * @param employeeId Id of the customer.
     * @return list of entities representing orders of specific customer.
     * @see List
     */
    List<Order> findByEmployee(long employeeId);

    /**
     * Get all orders saved within last month that have the given state.
     * @return list of orders
     */
    List<Order> getOrdersLastMonthWithState(OrderState state);

    /**
     * Get all orders saved within last week that have the given state.
     * @return list of orders
     */
    List<Order> getOrdersLastWeekWithState(OrderState state);

    /**
     * Get all orders saved within last day that have the given state.
     * @return list of orders
     */
    List<Order> getOrdersLastDayWithState(OrderState state);

    /**
     * Get all orders saved within last month.
     * @return list of orders
     */
    List<Order> getOrdersLastMonth();

    /**
     * Get all orders saved within last week.
     * @return list of orders
     */
    List<Order> getOrdersLastWeek();

    /**
     * Get all orders saved within last day.
     * @return list of orders
     */
    List<Order> getOrdersLastDay();

    /**
     * List of all orders with particular state
     *
     * @param state state in which actual order exist in time.
     * @return state of the order
     */
    List<Order> getOrdersWithState(OrderState state);

    /**
     * List all orders between two dates.
     *
     * @param start a date from which are orders taken
     * @param end   a date till are orders taken
     * @return list of orders between two dates
     */
    public List<Order> getOrdersCreatedBetween(LocalDate start, LocalDate end);

    /**
     * List all orders between two dates and state.
     *
     * @param start start a date from which are orders taken
     * @param end   end a date till are orders taken
     * @param state state in which actual order exist in time.
     * @return list of orders between two dates with given state.
     */
    public List<Order> getOrdersCreatedBetweenWithState(LocalDate start, LocalDate end, OrderState state);

    /**
     * Find all orders in the system and return them
     *
     * @return list of orders
     */
    List<Order> findAll();

    /**
     * Ship order to the customer
     *
     * @param order
     */
    void shipOrder(Order order);

    /**
     * Let customer finish the order and store it to the sytem
     *
     * @param order
     */
    void finishOrder(Order order);

    /**
     * Cancels order made in system
     *
     * @param order
     */
    void deleteOrder(Order order);

    /**
     * Check order in the system by its ID
     *
     * @param id unique identifier for order
     * @return Entity representing {@link Order}
     */
    Order findById(long id);

    /**
     * Return total price of the order.
     * Order can have additional expenses, use it for final checkout.
     *
     * @param id unique identifier for order
     * @return
     */
    int getTotalPrice(long id);
}

