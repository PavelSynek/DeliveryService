package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.entity.Order;
import cz.muni.fi.pa165.deliveryservice.entity.Product;
import cz.muni.fi.pa165.deriveryservice.enums.OrderState;
import java.time.LocalDate;
import java.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Tomas Milota
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OrderDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;

    private Order order1;
    private Order order2;

    @BeforeClass
    public void setUp() {        
        order1 = new Order();
        order2 = new Order();
        
        Customer happyCustomer = new Customer();
        happyCustomer.setEmail("happycustomer@gmail.com");
        happyCustomer.setFirstName("James");
        happyCustomer.setSurname("Doe");
        happyCustomer.setRegistrationDate(LocalDate.of(2014, Month.DECEMBER, 24));
        customerDao.create(happyCustomer);
        
        Product car =  new Product();
        car.setName("Audi");
        Product plane = new Product();
        plane.setName("Boeing");
        productDao.create(car);
        productDao.create(plane);
        
        order1.setCreated(LocalDate.of(2015, Month.JULY, 10));
        order1.setOrderState(OrderState.RECEIVED);
        order1.setCustomer(happyCustomer);
        order1.addProduct(car);
        
        order2.setCreated(LocalDate.of(2015, Month.APRIL, 5));
        order2.setOrderState(OrderState.SHIPPED);
        order2.setCustomer(happyCustomer);
        order2.addProduct(plane);
        
        orderDao.create(order1);
        orderDao.create(order2);
    }
    
    @Test
    public void findAllTest() {
        List<Order> expected = new ArrayList<>();  
        expected.add(order1);
        expected.add(order2);
        
        assertEquals(expected, orderDao.findAll());
    }
    
    @Test
    public void findByIdTest() {
        assertEquals(orderDao.findById(order1.getId()), order1);
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
        found.setOrderState(OrderState.DONE);
        orderDao.update(found);
        
        Order found2 = orderDao.findById(found.getId());
        assertEquals(found, found2);
    }
    
    
}
