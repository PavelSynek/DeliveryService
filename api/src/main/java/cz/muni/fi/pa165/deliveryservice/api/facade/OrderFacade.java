package cz.muni.fi.pa165.deliveryservice.api.facade;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

import cz.muni.fi.pa165.deliveryservice.api.dto.OrderCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.OrderDTO;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.service.util.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Matej Leško
 */
public interface OrderFacade {
    /**
     * Create order in the system
     *
     * @param order entity representing Order in real world
     */
    long createOrder(OrderCreateDTO order) throws AlreadyExistsException;

    /**
     * List orders of specific customer
     *
     * @param customerId Id of the customer.
     * @return list of entities representing orders of specific customer.
     * @see List
     */
    List<OrderDTO> findByCustomer(long customerId);

    /**
     * List all orders delivered by employee
     *
     * @param employeeId Id of the customer.
     * @return list of entities representing orders of specific customer.
     * @see List
     */
    List<OrderDTO> findByEmployee(long employeeId);

    /**
     * Get all orders saved within last month that have the given state.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastMonthWithState(OrderState state);

    /**
     * Get all orders saved within last week that have the given state.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastWeekWithState(OrderState state);

    /**
     * Get all orders saved within last day that have the given state.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastDayWithState(OrderState state);

    /**
     * Get all orders saved within last month.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastMonth();

    /**
     * Get all orders saved within last week.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastWeek();

    /**
     * Get all orders saved within last day.
     *
     * @return list of orders
     */
    List<OrderDTO> getOrdersLastDay();

    /**
     * List of all orders with particular state
     *
     * @param state state in which actual order exist in time.
     * @return state of the order
     */
    List<OrderDTO> getOrdersWithState(OrderState state);

    /**
     * List all orders between two dates.
     *
     * @param start a date from which are orders taken
     * @param end   a date till are orders taken
     * @return list of orders between two dates
     */
    List<OrderDTO> getOrdersCreatedBetween(LocalDate start, LocalDate end);

    /**
     * List all orders between two dates and state.
     *
     * @param start start a date from which are orders taken
     * @param end   end a date till are orders taken
     * @param state state in which actual order exist in time.
     * @return list of orders between two dates with given state.
     */
    List<OrderDTO> getOrdersCreatedBetweenWithState(LocalDate start, LocalDate end, OrderState state);

    /**
     * Find all orders in the system and return them
     *
     * @return list of orders
     */
    List<OrderDTO> findAll();

    /**
     * Ship order to the customer
     *
     * @param order
     */
    void shipOrder(OrderDTO order)
            throws ShippedOrderException, CancelledOrderException, ClosedOrderException;

    /**
     * Close order - {@link OrderDTO} received his package.
     *
     * @param order
     */
    void closeOrder(OrderDTO order)
            throws UnprocessedOrderException, CancelledOrderException, ClosedOrderException;

    /**
     * Cancels order made in system
     *
     * @param order
     */
    void cancelOrder(OrderDTO order)
            throws CancelledOrderException, ClosedOrderException;

    /**
     * Check order in the system by its ID
     *
     * @param id unique identifier for order
     * @return
     */
    OrderDTO findById(long id) throws NotFoundException;

    /**
     * Return total price of the order.
     * Order can have additional expenses, use it for final checkout.
     *
     * @param id unique identifier for order
     * @return
     */
    int getTotalPrice(long id) throws NotFoundException;
}
