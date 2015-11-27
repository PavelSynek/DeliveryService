package cz.muni.fi.pa165.deliveryservice.service.tests;

import cz.muni.fi.pa165.deliveryservice.persist.dao.CustomerDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.service.CustomerService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.pa165.deliveryservice.service.config.ServiceConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tomas Milota on 27.11.2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    Customer customer, anotherCustomer;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareCustomers() {

        customer = new Customer();
        customer.setEmail("example@gmail.com");
        customer.setFirstName("Joe");
        customer.setSurname("Smith");
        customer.setOrders(new ArrayList<Order>());
        customer.setRegistrationDate(LocalDate.now());

        anotherCustomer = new Customer();
        anotherCustomer.setEmail("another@gmail.com");
        anotherCustomer.setFirstName("Joe");
        anotherCustomer.setSurname("Stone");
        anotherCustomer.setOrders(new ArrayList<Order>());
        anotherCustomer.setRegistrationDate(LocalDate.now());
    }

    @Test
    public void createTest() {
        customerService.create(anotherCustomer, "password");
        verify(customerDao).create(anotherCustomer);
    }

    @Test
    public void deleteTest() {
        customerService.delete(customer);
        verify(customerDao).remove(customer);
    }

    @Test
    public void findByEmailTest() {
        when(customerDao.findByEmail(customer.getEmail())).thenReturn(customer);

        customerService.create(customer, "password");
        Customer found = customerService.findByEmail(customer.getEmail());
        Assert.assertEquals(found, customer);
    }

    @Test
    public void findByIdTest() {
        when(customerDao.findById(customer.getId())).thenReturn(customer);

        customerService.create(customer, "password");
        Customer found = customerService.findById(customer.getId());
        Assert.assertEquals(found, customer);
    }

    @Test
    public void findByNameTest() {
        String name = customer.getFirstName() + " " + customer.getSurname();
        List<Customer> list = new ArrayList<>();
        list.add(customer);
        when(customerDao.findByName(name)).thenReturn(list);

        customerService.create(customer, "password");
        List<Customer> expected = new ArrayList<>();
        expected.add(customer);
        List<Customer> found = customerService.findByName(customer.getFirstName() + " " + customer.getSurname());
        Assert.assertEquals(found, expected);
    }

    @Test
    public void getAllTest() {
        List<Customer> list = new ArrayList<>();
        list.add(customer);
        list.add(anotherCustomer);
        when(customerDao.findAll()).thenReturn(list);

        customerService.create(customer, "password");
        customerService.create(anotherCustomer, "passw");
        List<Customer> expected = new ArrayList<>();
        expected.add(customer);
        expected.add(anotherCustomer);
        List<Customer> found = customerService.getAll();
        Assert.assertEquals(found, expected);
    }

    @Test
    public void authenticateCorrectPassword() {
        customerService.create(customer, "password");

        Assert.assertTrue(customerService.authenticate(customer, "password"));
    }

    @Test
    public void authenticateWrongPassword() {
        customerService.create(customer, "password");

        Assert.assertFalse(customerService.authenticate(customer, "wrong password"));
    }
}
