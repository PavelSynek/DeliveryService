package cz.muni.fi.pa165.deliveryservice.service.tests;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.persist.dao.OrderDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.service.OrderService;
import cz.muni.fi.pa165.deliveryservice.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class OrderServiceTests extends AbstractTestNGSpringContextTests {
    @Mock
    private OrderDao orderDao;

    @Autowired
    @InjectMocks
    private OrderService orderService;

    private Order orderReceived;
    private Order orderShipped;
    private Order orderClosed;
    private Order orderCancelled;

    @BeforeMethod
    public void createOrders() {
        orderReceived = new Order();
        orderShipped = new Order();
        orderClosed = new Order();
        orderCancelled = new Order();

        orderReceived.setState(OrderState.RECEIVED);
        orderShipped.setState(OrderState.SHIPPED);
        orderClosed.setState(OrderState.CLOSED);
        orderCancelled.setState(OrderState.CANCELED);
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllOrdersLastWeekTest() {

        LocalDate defaultDate = LocalDate.now();
        LocalDate monthAgo = defaultDate.minusMonths(1);
        LocalDate weekAgo = defaultDate.minusWeeks(1);
        LocalDate daykAgo = defaultDate.minusDays(1);

        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setState(OrderState.CANCELED);
        testOrder.setCreated(weekAgo);

        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastWeekWithState(OrderState.CANCELED);
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetweenWithState(weekAgo, defaultDate, OrderState.CANCELED);
    }

}
