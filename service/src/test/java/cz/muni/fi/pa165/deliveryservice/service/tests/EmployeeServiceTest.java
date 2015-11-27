package cz.muni.fi.pa165.deliveryservice.service.tests;

import cz.muni.fi.pa165.deliveryservice.persist.dao.EmployeeDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.service.EmployeeService;
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
public class EmployeeServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EmployeeDao employeeDao;

    @Autowired
    @InjectMocks
    private EmployeeService employeeService;

    Employee employee, anotherEmployee;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareEmployees() {

        employee = new Employee();
        employee.setEmail("example@gmail.com");
        employee.setFirstName("Joe");
        employee.setSurname("Smith");
        employee.setOrders(new ArrayList<Order>());
        employee.setRegistrationDate(LocalDate.now());

        anotherEmployee = new Employee();
        anotherEmployee.setEmail("another@gmail.com");
        anotherEmployee.setFirstName("Joe");
        anotherEmployee.setSurname("Stone");
        anotherEmployee.setOrders(new ArrayList<Order>());
        anotherEmployee.setRegistrationDate(LocalDate.now());
    }

    @Test
    public void createTest() {
        employeeService.create(anotherEmployee, "password");
        verify(employeeDao).create(anotherEmployee);
    }

    @Test
    public void deleteTest() {
        employeeService.delete(employee);
        verify(employeeDao).remove(employee);
    }

    @Test
    public void findByEmailTest() {
        when(employeeDao.findByEmail(employee.getEmail())).thenReturn(employee);

        employeeService.create(employee, "password");
        Employee found = employeeService.findByEmail(employee.getEmail());
        Assert.assertEquals(found, employee);
    }

    @Test
    public void findByIdTest() {
        when(employeeDao.findById(employee.getId())).thenReturn(employee);

        employeeService.create(employee, "password");
        Employee found = employeeService.findById(employee.getId());
        Assert.assertEquals(found, employee);
    }

    @Test
    public void findByNameTest() {
        String name = employee.getFirstName() + " " + employee.getSurname();
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        when(employeeDao.findByName(name)).thenReturn(list);

        employeeService.create(employee, "password");
        List<Employee> expected = new ArrayList<>();
        expected.add(employee);
        List<Employee> found = employeeService.findByName(employee.getFirstName() + " " + employee.getSurname());
        Assert.assertEquals(found, expected);
    }

    @Test
    public void getAllTest() {
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        list.add(anotherEmployee);
        when(employeeDao.findAll()).thenReturn(list);

        employeeService.create(employee, "password");
        employeeService.create(anotherEmployee, "passw");
        List<Employee> expected = new ArrayList<>();
        expected.add(employee);
        expected.add(anotherEmployee);
        List<Employee> found = employeeService.getAll();
        Assert.assertEquals(found, expected);
    }

    @Test
    public void authenticateCorrectPassword() {
        employeeService.create(employee, "password");

        Assert.assertTrue(employeeService.authenticate(employee, "password"));
    }

    @Test
    public void authenticateWrongPassword() {
        employeeService.create(employee, "password");

        Assert.assertFalse(employeeService.authenticate(employee, "wrong password"));
    }
}
