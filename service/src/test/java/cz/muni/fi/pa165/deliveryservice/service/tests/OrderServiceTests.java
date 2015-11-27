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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
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

    private LocalDate defaultDate;
    private LocalDate yearAgo;
    private LocalDate monthAgo;
    private LocalDate weekAgo;
    private LocalDate dayAgo;

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

        defaultDate = LocalDate.now();
        yearAgo = defaultDate.minusMonths(12);
        monthAgo = defaultDate.minusMonths(1);
        weekAgo = defaultDate.minusWeeks(1);
        dayAgo = defaultDate.minusDays(1);
    }

    @Test
    public void testLastDayWithState_1_Single() {
        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setState(OrderState.CLOSED);
        testOrder.setCreated(dayAgo);

        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastDayWithState(OrderState.CLOSED);
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetweenWithState(dayAgo, defaultDate, OrderState.CLOSED);
    }

    @Test
    public void testLastDayWithState_2_List() {

        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setState(OrderState.CLOSED);
        testOrder1.setCreated(dayAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setState(OrderState.CLOSED);
        testOrder2.setCreated(dayAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastDayWithState(OrderState.CLOSED);
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetweenWithState(dayAgo, defaultDate, OrderState.CLOSED);
    }

    @Test
    public void testLastWeekWithState_1_Single() {
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

    @Test
    public void testLastWeekWithState_2_List() {

        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setState(OrderState.CANCELED);
        testOrder1.setCreated(weekAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setState(OrderState.CANCELED);
        testOrder2.setCreated(weekAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastWeekWithState(OrderState.CANCELED);
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetweenWithState(weekAgo, defaultDate, OrderState.CANCELED);
    }

    @Test
    public void testLastMonthWithState_1_Single() {
        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setState(OrderState.SHIPPED);
        testOrder.setCreated(monthAgo);

        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastMonthWithState(OrderState.SHIPPED);
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetweenWithState(monthAgo, defaultDate, OrderState.SHIPPED);
    }

    @Test
    public void testLastMonthWithState_2_List() {

        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setState(OrderState.SHIPPED);
        testOrder1.setCreated(monthAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setState(OrderState.SHIPPED);
        testOrder2.setCreated(monthAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastMonthWithState(OrderState.SHIPPED);
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetweenWithState(monthAgo, defaultDate, OrderState.SHIPPED);
    }

    @Test
    public void testLastDay_1_Single() {
        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setCreated(dayAgo);

        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastDay();
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetween(dayAgo, defaultDate);
    }

    @Test
    public void testLastDay_2_List() {

        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setCreated(dayAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setCreated(dayAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastDay();
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetween(dayAgo, defaultDate);
    }

    @Test
    public void testLastWeek_1_Single() {
        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setCreated(weekAgo);

        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastWeek();
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetween(weekAgo, defaultDate);
    }

    @Test
    public void testLastWeek_2_List() {
        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setCreated(weekAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setCreated(weekAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastWeek();
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetween(weekAgo, defaultDate);
    }

    @Test
    public void testLastMonth_1_Single() {
        Order testOrder = new Order();
        testOrder.setId(20L);
        testOrder.setCreated(monthAgo);

        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(Collections.singletonList(testOrder));

        List<Order> orders = orderService.getOrdersLastMonth();
        Assert.assertEquals(1, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));

        verify(orderDao).getOrdersCreatedBetween(monthAgo, defaultDate);
    }

    @Test
    public void testLastMonth_2_List() {
        Order testOrder1 = new Order();
        testOrder1.setId(20L);
        testOrder1.setCreated(monthAgo);

        Order testOrder2 = new Order();
        testOrder2.setId(30L);
        testOrder2.setCreated(monthAgo);

        List<Order> compList = new ArrayList<>();
        compList.add(testOrder1);
        compList.add(testOrder2);


        when(orderDao.getOrdersCreatedBetween(any(LocalDate.class), any(LocalDate.class))).
                thenReturn(compList);

        List<Order> orders = orderService.getOrdersLastMonth();
        Assert.assertEquals(2, orders.size());
        Assert.assertTrue(orders.get(0).getId().equals(20L));
        Assert.assertTrue(orders.get(1).getId().equals(30L));

        verify(orderDao, times(2)).getOrdersCreatedBetween(monthAgo, defaultDate);
    }


//    @Test
//    public void getAllOrdersLastWeekTest() {
//
//        Order testOrder = new Order();
//        testOrder.setId(20L);
//        testOrder.setState(OrderState.CANCELED);
//        testOrder.setCreated(weekAgo);
//
//        when(orderDao.getOrdersCreatedBetweenWithState(any(LocalDate.class), any(LocalDate.class), any())).
//                thenReturn(Collections.singletonList(testOrder));
//
//        List<Order> orders = orderService.getOrdersLastWeekWithState(OrderState.CANCELED);
//        Assert.assertEquals(1, orders.size());
//        Assert.assertTrue(orders.get(0).getId().equals(20L));
//
//        verify(orderDao).getOrdersCreatedBetweenWithState(weekAgo, defaultDate, OrderState.CANCELED);
//    }

}
