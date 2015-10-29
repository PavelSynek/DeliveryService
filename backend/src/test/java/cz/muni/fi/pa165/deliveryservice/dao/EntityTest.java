package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

/**
 * Created by Matej Leško on 2015-10-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EntityTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private CustomerDao customerDao;

    private Employee employee;
    private Customer customer;

    @BeforeClass
    public void setUp() {

        System.out.println(employeeDao);

        employee = new Employee();
        employee.setFirstName("Matej");
        employee.setSurname("Lesko");
        employee.setEmail("lesko@test.cz");
        employee.setRegistrationDate(LocalDate.now());

        customer = new Customer();
        customer.setFirstName("Tomas");
        customer.setSurname("Milota");
        customer.setEmail("milota@test.cz");
        customer.setRegistrationDate(LocalDate.now());

        employeeDao.create(employee);
        customerDao.create(customer);


    }

    @Test
    public void findById() {
        assertEquals(employeeDao.findById(employee.getId()), employee);
    }
}