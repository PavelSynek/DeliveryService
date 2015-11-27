package cz.muni.fi.pa165.deliveryservice.service.tests;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
//import cz.muni.fi.pa165.deliveryservice.persist.entity.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.EmployeeService;
import cz.muni.fi.pa165.deliveryservice.service.config.ServiceConfiguration;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class EmployeeFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private EmployeeService employeeService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private EmployeeFacade employeeFacade;

    EmployeeDTO employeeDTO;
    EmployeeDTO anotherEmployeeDTO;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createEmployees() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("employeeDTO@gmail.com");
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
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);

        employeeFacade.create(employeeDTO, "password");
        verify(employeeService).create(c, "password");
    }

    @Test
    public void deleteTest() {
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);

        employeeFacade.delete(employeeDTO.getId());
        verify(employeeService).delete(c);
    }

    @Test
    public void findByEmailTest() {
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);
        when(employeeService.findByEmail(employeeDTO.getEmail())).thenReturn(c);

        employeeFacade.create(employeeDTO, "password");
        Assert.assertEquals(employeeFacade.findByEmail(employeeDTO.getEmail()), employeeDTO);
    }

    @Test
    public void findByNameTest() {
        List<EmployeeDTO> expected = new ArrayList<>();
        expected.add(employeeDTO);
        List<Employee> list = beanMappingService.mapTo(expected, Employee.class);
        when(employeeService.findByName(employeeDTO.getFirstName() + " " + employeeDTO.getSurname())).thenReturn(list);

        employeeFacade.create(employeeDTO, "password");
        Assert.assertEquals(employeeFacade.findByName("Joe Smith"), expected);
    }

    @Test
    public void findByIdTest() {
        Employee c = beanMappingService.mapTo(employeeDTO, Employee.class);
        when(employeeService.findById(employeeDTO.getId())).thenReturn(c);

        employeeFacade.create(employeeDTO, "password");
        Assert.assertEquals(employeeFacade.findById(employeeDTO.getId()), employeeDTO);
    }

    @Test
    public void getAllTest() {
        List<EmployeeDTO> expected = new ArrayList<>();
        expected.add(employeeDTO);
        expected.add(anotherEmployeeDTO);
        List<Employee> list = beanMappingService.mapTo(expected, Employee.class);
        when(employeeService.getAll()).thenReturn(list);

        Assert.assertEquals(employeeFacade.getAll(), expected);
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