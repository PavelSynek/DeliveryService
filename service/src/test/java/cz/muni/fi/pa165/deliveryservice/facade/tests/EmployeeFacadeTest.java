package cz.muni.fi.pa165.deliveryservice.facade.tests;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.EmployeeService;
import cz.muni.fi.pa165.deliveryservice.service.facade.EmployeeFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public class EmployeeFacadeTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private EmployeeFacade employeeFacade;

    private EmployeeDTO employeeDTO;
    private EmployeeDTO anotherEmployeeDTO;
    private Employee employee, anotherEmployee;

    @BeforeClass
    public void setUp() throws ServiceException {
        employeeFacade = new EmployeeFacadeImpl();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createEmployees() {
        employee = new Employee();
        employee.setEmail("employeedto@gmail.com");
        employee.setFirstName("Joe");
        employee.setSurname("Smith");
        employee.setRegistrationDate(LocalDate.now());

        anotherEmployee = new Employee();
        anotherEmployee.setEmail("other@gmail.com");
        anotherEmployee.setFirstName("Adam");
        anotherEmployee.setSurname("Smith");
        anotherEmployee.setRegistrationDate(LocalDate.now());

        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("employeedto@gmail.com");
        employeeDTO.setFirstName("Joe");
        employeeDTO.setSurname("Smith");
        employeeDTO.setRegistrationDate(LocalDate.now());

        anotherEmployeeDTO = new EmployeeDTO();
        anotherEmployeeDTO.setEmail("other@gmail.com");
        anotherEmployeeDTO.setFirstName("Adam");
        anotherEmployeeDTO.setSurname("Smith");
        anotherEmployeeDTO.setRegistrationDate(LocalDate.now());
    }

    @Test
    public void createTest() {
        when(beanMappingService.mapTo(employeeDTO, Employee.class)).thenReturn(employee);

        Employee e = beanMappingService.mapTo(employeeDTO, Employee.class);

        employeeFacade.create(employeeDTO, "password");
        verify(employeeService).create(e, "password");
    }

    @Test
    public void deleteTest() {
        when(employeeService.findById(employee.getId())).thenReturn(employee);

        employeeFacade.delete(employeeDTO.getId());
        verify(employeeService).delete(employee);
    }

    @Test
    public void findByEmailTest() {
        Employee e = beanMappingService.mapTo(employeeDTO, Employee.class);
        when(employeeService.findByEmail(employeeDTO.getEmail())).thenReturn(e);
        when(beanMappingService.mapTo(e, EmployeeDTO.class)).thenReturn(employeeDTO);

        employeeFacade.create(employeeDTO, "password");
        assertEquals(employeeFacade.findByEmail(employeeDTO.getEmail()), employeeDTO);
    }

    @Test
    public void findByNameTest() {
        List<EmployeeDTO> expected = new ArrayList<>();
        expected.add(employeeDTO);
        List<Employee> list = beanMappingService.mapTo(expected, Employee.class);

        when(beanMappingService.mapTo(list, EmployeeDTO.class)).thenReturn(expected);
        when(employeeService.findByName(employeeDTO.getFirstName() + " " + employeeDTO.getSurname())).thenReturn(list);

        assertEquals(employeeFacade.findByName("Joe Smith"), expected);
    }

    @Test
    public void findByIdTest() {
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);
        when(employeeService.findById(employeeDTO.getId())).thenReturn(c);

        assertEquals(employeeFacade.findById(employeeDTO.getId()), employeeDTO);
    }

    @Test
    public void getAllTest() {
        List<EmployeeDTO> expected = new ArrayList<>();
        expected.add(employeeDTO);
        expected.add(anotherEmployeeDTO);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(anotherEmployee);

        when(beanMappingService.mapTo(employees, EmployeeDTO.class)).thenReturn(expected);
        when(employeeService.getAll()).thenReturn(employees);

        assertEquals(employeeFacade.getAll(), expected);
    }

    @Test
    public void authenticateTest() {
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);

        PersonAuthenticateDTO p = new PersonAuthenticateDTO();
        p.setPersonId(employeeDTO.getId());
        p.setPassword("password");

        employeeFacade.authenticate(p);
        verify(employeeService).authenticate(c, "password");
    }
}