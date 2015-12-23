package cz.muni.fi.pa165.deliveryservice.persist.tests.dao;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidWeightException;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.persist.dao.CustomerDao;
import cz.muni.fi.pa165.deliveryservice.persist.dao.OrderDao;
import cz.muni.fi.pa165.deliveryservice.persist.dao.ProductDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by Pavel on 21. 10. 2015.
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ProductDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderDao orderDao;

    private Product car;
    private Product plane;
    private Customer angryCustomer;
    private Order order;

    @BeforeClass
    public void setUp() throws InvalidPriceException {

        angryCustomer = new Customer();
        angryCustomer.setEmail("angrycustomerxxxx@gmail.com");
        angryCustomer.setFirstName("Jakee");
        angryCustomer.setSurname("Smiths");
        angryCustomer.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        customerDao.create(angryCustomer);

        car = new Product();
        car.setName("Audi");
        car.setAddedDate(LocalDate.of(2015, 1, 1));
        car.setPrice(1000000);

        plane = new Product();
        plane.setName("Boeing");
        plane.setAddedDate(LocalDate.of(2014, 12, 24));
        plane.setPrice(10000000);

        order = new Order();
//        System.out.println("ORDER: " + plane.getOrder());
        order.setCreated(LocalDate.of(2014, 12, 24));
        order.setState(OrderState.CANCELLED);
        order.setCustomer(angryCustomer);
        orderDao.create(order);


        order.addProduct(car);
        order.addProduct(plane);

//        productDao.update(car);
//        productDao.update(plane);
        productDao.create(car);
        productDao.create(plane);
        orderDao.update(order);


        productDao.initDBAccessHandlers();
        orderDao.initDBAccessHandlers();
        productDao.initDBAccessHandlers();
    }

    @Test
    public void findById() {
        assertEquals(productDao.findById(car.getId()), car);
    }

    @Test
    public void findAll() {
        List<Product> expected = new ArrayList<>();
        expected.add(car);
        expected.add(plane);

        assertEquals(productDao.findAll(), expected);
    }

    @Test
    public void delete() {
        List<Product> expected = new ArrayList<>();
        expected.add(car);

        //THIS IS EXTREMELY IMPORTANT TO DO, otherwise constraint exception will be raised
        order = orderDao.findById(order.getId());

        order.getProducts().remove(plane);
        orderDao.update(order);
        productDao.remove(plane);


        assertEquals(productDao.findAll(), expected);
    }

    @Test(expectedExceptions = InvalidWeightException.class)
    public void invalidWeight() throws InvalidWeightException {
        Product product = productDao.findById(car.getId());
        product.setWeight(-10l);
    }

    @Test(expectedExceptions = InvalidPriceException.class)
    public void invalidPrice() throws InvalidPriceException {
        Product product = productDao.findById(car.getId());
        product.setPrice(-10l);
    }
}
