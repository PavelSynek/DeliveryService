package cz.muni.fi.pa165.deliveryservice.persist.tests.dao;

import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.persist.dao.CustomerDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
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

import static org.testng.Assert.*;

/**
 * Created by Pavel on 30. 10. 2015.
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CustomerDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerDao customerDao;

    private Customer angryCustomer;
    private Customer happyCustomer;

    @BeforeClass
    public void setUp() {
        angryCustomer = new Customer();
        angryCustomer.setEmail("angrycustomer@gmail.com");
        angryCustomer.setFirstName("John");
        angryCustomer.setSurname("Smith");
        angryCustomer.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        angryCustomer.addOrder(null); //TO make test PASS TODO add another tests for orders later
        customerDao.create(angryCustomer);

        happyCustomer = new Customer();
        happyCustomer.setEmail("happycustomerxxx@gmail.com");
        happyCustomer.setFirstName("James");
        happyCustomer.setSurname("Doe");
        happyCustomer.setRegistrationDate(LocalDate.of(2014, Month.DECEMBER, 24));
        happyCustomer.addOrder(null);
        customerDao.create(happyCustomer);

        customerDao.initDBAccessHandlers();
    }

    @Test
    public void findAllTest() {
        List<Customer> expected = new ArrayList<>();
        expected.add(angryCustomer);
        expected.add(happyCustomer);

        assertEquals(customerDao.findAll(), expected);
    }

    @Test
    public void findByIdTest() {
        assertEquals(customerDao.findById(angryCustomer.getId()), angryCustomer);
    }

    @Test
    public void removeTest() {
        assertNotNull(customerDao.findById(angryCustomer.getId()));
        customerDao.remove(angryCustomer);
        assertNull(customerDao.findById(angryCustomer.getId()));
    }

    @Test
    public void findByName() {
        List<Customer> found = customerDao.findByName("John Smith");
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), angryCustomer);
    }

    @Test
    public void updateTest() {
        Customer john = customerDao.findByName("John Smith").get(0);
        john.setSurname("Doe");
        customerDao.update(john);

        List<Customer> found = customerDao.findByName("John Doe");
        assertEquals(found.size(), 1);
        Customer expected = found.get(0);
        assertEquals(john, expected);
    }
}
