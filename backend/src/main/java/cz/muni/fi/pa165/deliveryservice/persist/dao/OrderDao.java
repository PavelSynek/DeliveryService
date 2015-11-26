package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Pavel on 21. 10. 2015.
 *
 * @author Pavel
 * @author Matej Leško
 * @version 0.1
 */
public interface OrderDao extends EntityTemplate<Order> {

    /**
     * List orders of specific customer
     *
     * @param customerId Id of the customer.
     * @return list of entities representing orders of specific customer.
     * @see List
     */
    List<Order> findByCustomer(long customerId);

    /**
     * List of all orders with particular state
     *
     * @param state state in which actual order exist in time.
     * @return state of the order
     */
    public List<Order> getOrdersWithState(OrderState state);

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

}