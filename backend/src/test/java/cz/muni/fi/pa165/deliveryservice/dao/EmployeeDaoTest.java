package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Tomas Milota
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class EmployeeDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private EmployeeDao employeeDao;

    private Employee courier;
    private Employee accountant;

    @BeforeClass
    public void setUp() {
        courier = new Employee();
        courier.setEmail("courier@mail.com");
        courier.setFirstName("Jon");
        courier.setSurname("Snow");
        employeeDao.create(courier);

        accountant = new Employee();
        accountant.setEmail("accountant@mail.com");
        employeeDao.create(accountant);
    }

    @Test
    public void findAllTest() {
        List<Employee> expected = new ArrayList<>();
        expected.add(courier);
        expected.add(accountant);

        assertEquals(expected, employeeDao.findAll());
    }

    @Test
    public void findByIdTest() {
        assertEquals(employeeDao.findById(courier.getId()), courier);
    }

    @Test
    public void removeTest() {
        assertNotNull(employeeDao.findById(accountant.getId()));
        employeeDao.remove(accountant);
        assertNull(employeeDao.findById(accountant.getId()));
    }

    @Test
    public void findByName() {
        List<Employee> found = employeeDao.findByName("Jon Snow");
        assertEquals(1, found.size());
        assertEquals(courier, found.get(0));
    }

    @Test
    public void updateTest() {
        Employee found = employeeDao.findByName("Jon Snow").get(0);
        found.setSurname("Stark");
        employeeDao.update(found);

        Employee found2 = employeeDao.findByName("Jon Stark").get(0);
        assertEquals(found, found2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullEmailTest() {
        Employee emp = new Employee();
        emp.setEmail(null);
        employeeDao.create(emp);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void emailIsUniqueTest() {
        Employee courier2 = new Employee();
        courier2.setEmail("courier@mail.com");
        employeeDao.create(courier2);
    }
}