package cz.muni.fi.pa165.deliveryservice.persist.tests.dao;

import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.persist.dao.CustomerDao;
import cz.muni.fi.pa165.deliveryservice.persist.dao.OrderDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Tomas Milota
 * @author Matej Leško
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OrderDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private OrderDao orderDao;
    //    @Autowired TODO we do not have such functionality yet (OrderItem)
//    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;

    private Customer angryCustomer;
    private Customer happyCustomer;

    private Order order1;
    private Order order2;
    private Order order3;
    private Order order4;
    private Order order5;

    private LocalDate date1;
    private LocalDate date2;
    private LocalDate date3;

    @BeforeClass
    public void setUp() {
        date1 = LocalDate.of(2015, Month.JANUARY, 1);
        date2 = LocalDate.of(2015, Month.APRIL, 5);
        date3 = LocalDate.of(2015, Month.JUNE, 6);

        angryCustomer = new Customer();
        angryCustomer.setEmail("angrycustomerxxx@gmail.com");
        angryCustomer.setFirstName("Jake");
        angryCustomer.setSurname("Smith");
        angryCustomer.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));

        happyCustomer = new Customer();
        happyCustomer.setEmail("happycustomer@gmail.com");
        happyCustomer.setFirstName("James");
        happyCustomer.setSurname("Bond");
        happyCustomer.setRegistrationDate(LocalDate.of(2014, Month.DECEMBER, 24));

//        Product car =  new Product();
//        car.setName("Audi");
//        Product plane = new Product();
//        plane.setName("Boeing");
//        productDao.create(car);
//        productDao.create(plane);

        order1 = new Order();
        order1.setCreated(date1);
        order1.setState(OrderState.CANCELED);
        order1.setCustomer(angryCustomer);

        order2 = new Order();
        order2.setCreated(date2);
        order2.setState(OrderState.RECEIVED);
        order2.setCustomer(happyCustomer);

        order3 = new Order();
        order3.setCreated(date3);
        order3.setState(OrderState.CANCELED);
        order3.setCustomer(angryCustomer);

        order4 = new Order();
        order5 = new Order();

        order4.setCreated(LocalDate.of(2015, Month.JULY, 10));
        order4.setState(OrderState.RECEIVED);
        order4.setCustomer(happyCustomer);
//        order1.addProduct(car);

        order5.setCreated(LocalDate.of(2015, Month.APRIL, 5));
        order5.setState(OrderState.SHIPPED);
        order5.setCustomer(happyCustomer);
//        order2.addProduct(plane);

        customerDao.create(angryCustomer);
        angryCustomer.addOrder(order1);
        angryCustomer.addOrder(order3);
        customerDao.create(happyCustomer);
        happyCustomer.addOrder(order2);
        happyCustomer.addOrder(order4);
        happyCustomer.addOrder(order5);
//        customerDao.initDBAccessHandlers();


        orderDao.create(order1);
        orderDao.create(order2);
        orderDao.create(order3);
        orderDao.create(order4);
        orderDao.create(order5);

        orderDao.initDBAccessHandlers();

        customerDao.update(angryCustomer);
        customerDao.update(happyCustomer);
    }

    @Test
    public void nonExistentReturnsNullTest() {
        Assert.assertNull(orderDao.findById(99999l));
    }

    @Test
    public void findAllTest() {
        List<Order> expected = new ArrayList<>();
        expected.add(order1);
        expected.add(order2);
        expected.add(order3);
        expected.add(order4);
        expected.add(order5);

        assertEquals(orderDao.findAll(), expected);
        assertEquals(expected.size(), 5);
    }

    @Test
    public void findByIdTest() {
        assertEquals(orderDao.findById(order1.getId()), order1);

        Order found = orderDao.findById(order2.getId());

        assertEquals(found.getCreated(), date2);
        assertEquals(found.getState(), OrderState.RECEIVED);
        assertEquals(found.getCustomer().getEmail(), happyCustomer.getEmail());
    }

    @Test
    public void removeTest() {
        assertNotNull(orderDao.findById(order1.getId()));
        orderDao.remove(order1);
        assertNull(orderDao.findById(order1.getId()));
    }

    @Test
    public void updateTest() {
        Order found = orderDao.findById(order1.getId());
        found.setState(OrderState.CLOSED);
        orderDao.update(found);

        Order found2 = orderDao.findById(found.getId());
        assertEquals(found2, found);
    }

    @Test
    public void getOrdersWithStateTest() {
        assertEquals(orderDao.getOrdersWithState(OrderState.SHIPPED).size(), 1);

        List<Order> canceled = orderDao.getOrdersWithState(OrderState.CANCELED);
        List<Order> received = orderDao.getOrdersWithState(OrderState.RECEIVED);

        assertEquals(canceled.size(), 2);
        assertEquals(received.size(), 2);
    }

    @Test
    public void getOrdersCreatedBetweenTest() {
        LocalDate date1 = LocalDate.of(2016, Month.JANUARY, 1);
        LocalDate date2 = LocalDate.of(2017, Month.APRIL, 5);

        assertEquals(orderDao.getOrdersCreatedBetween(date1, date2).size(), 0);

        LocalDate date3 = LocalDate.of(2015, Month.JANUARY, 1);
        LocalDate date4 = LocalDate.of(2015, Month.MAY, 5);
        assertEquals(orderDao.getOrdersCreatedBetween(date3, date4).size(), 3);
    }

    @Test
    public void findByCustomerTest() {
        List<Order> temp = new ArrayList<>();
        temp.add(order1);
        temp.add(order3);
        assertEquals(orderDao.findByCustomer(angryCustomer.getId()), temp);

        temp.clear();

        temp.add(order2);
        temp.add(order4);
        temp.add(order5);
        assertEquals(orderDao.findByCustomer(happyCustomer.getId()), temp);
    }

    @Test
    public void getOrdersCreatedBetweenWithStateTest() {
        LocalDate date1 = LocalDate.of(2016, Month.JANUARY, 1);
        LocalDate date2 = LocalDate.of(2017, Month.APRIL, 5);

        assertEquals(
                orderDao.getOrdersCreatedBetweenWithState(
                        date1,
                        date2,
                        OrderState.CANCELED
                ).size(),
                0
        );

        LocalDate date3 = LocalDate.of(2015, Month.JANUARY, 1);
        LocalDate date4 = LocalDate.of(2015, Month.MAY, 5);
        assertEquals(
                orderDao.getOrdersCreatedBetweenWithState(
                        date3,
                        date4,
                        OrderState.RECEIVED
                ).size(),
                1
        );

        List<Order> temp = new ArrayList<>();
        temp.add(order1);
        temp.add(order3);

        assertEquals(
                orderDao.getOrdersCreatedBetweenWithState(
                        date3,
                        date2,
                        OrderState.CANCELED
                ),
                temp
        );
    }
}
