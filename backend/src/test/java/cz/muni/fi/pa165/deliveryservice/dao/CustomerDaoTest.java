package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.entity.Customer;
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
        angryCustomer.setEmail("angrycustomerxxx@gmail.com");
        angryCustomer.setFirstName("John");
        angryCustomer.setSurname("Smith");
        angryCustomer.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        customerDao.create(angryCustomer);

        happyCustomer = new Customer();
        happyCustomer.setEmail("happycustomer@gmail.com");
        happyCustomer.setFirstName("James");
        happyCustomer.setSurname("Doe");
        happyCustomer.setRegistrationDate(LocalDate.of(2014, Month.DECEMBER, 24));
        customerDao.create(happyCustomer);
    }

    @Test
    public void findAllTest() {
        List<Customer> expected = new ArrayList<>();
        expected.add(angryCustomer);
        expected.add(happyCustomer);

        assertEquals(expected, customerDao.findAll());
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
        assertEquals(1, found.size());
        assertEquals(angryCustomer, found.get(0));
    }

    @Test
    public void updateTest() {
        Customer john = customerDao.findByName("John Smith").get(0);
        john.setSurname("Doe");
        customerDao.update(john);

        List<Customer> found = customerDao.findByName("John Doe");
        assertEquals(1, found.size());
        Customer expected = found.get(0);
        assertEquals(expected, john);
    }
}
