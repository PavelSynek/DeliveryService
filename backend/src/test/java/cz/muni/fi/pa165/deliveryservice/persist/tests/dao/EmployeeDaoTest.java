package cz.muni.fi.pa165.deliveryservice.persist.tests.dao;

import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.persist.dao.EmployeeDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
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
import java.time.LocalDate;
import java.time.Month;
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
        courier.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        employeeDao.create(courier);

        accountant = new Employee();
        accountant.setEmail("accountant@mail.com");
        accountant.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        employeeDao.create(accountant);
        employeeDao.initDBAccessHandlers();
    }

    @Test
    public void findAllTest() {
        List<Employee> expected = new ArrayList<>();
        expected.add(courier);
        expected.add(accountant);

        assertEquals(employeeDao.findAll(), expected);
    }

    @Test
    public void findByIdTest() {
        assertEquals(employeeDao.findById(courier.getId()), courier);
    }

    @Test(expectedExceptions = ViolentDataAccessException.class)
    public void removeTest() {
        assertNotNull(employeeDao.findById(accountant.getId()));
        employeeDao.remove(accountant);
        assertNull(employeeDao.findById(accountant.getId()));
    }

    @Test
    public void findByName() {
        List<Employee> found = employeeDao.findByName("Jon Snow");
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), courier);
    }

    @Test
    public void updateTest() {
        Employee found = employeeDao.findByName("Jon Snow").get(0);
        found.setSurname("Stark");
        employeeDao.update(found);

        Employee found2 = employeeDao.findByName("Jon Stark").get(0);
        assertEquals(found2, found);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void nullEmailTest() {
        Employee emp = new Employee();
        emp.setEmail(null);
        employeeDao.create(emp);

        employeeDao.findByEmail("courier@mail.com");
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void emailIsUniqueTest() {
        Employee courier2 = new Employee();
        courier2.setEmail("courier@mail.com");
        courier2.setFirstName("Jon");
        courier2.setSurname("Snow");
        courier2.setRegistrationDate(LocalDate.of(2015, Month.JANUARY, 1));
        employeeDao.create(courier2);

        employeeDao.findByEmail("courier@mail.com");
    }
}