package cz.muni.fi.pa165.deliveryservice.service.tests;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.OrderDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.deliveryservice.service.CustomerService;
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
public class CustomerFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerService customerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private CustomerFacade customerFacade;

    CustomerDTO customerDTO;
    CustomerDTO anotherCustomerDTO;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers() {
        customerDTO = new CustomerDTO();
        customerDTO.setEmail("example@gmail.com");
        customerDTO.setFirstName("Joe");
        customerDTO.setSurname("Smith");
        customerDTO.setOrders(new ArrayList<OrderDTO>());
        customerDTO.setRegistrationDate(LocalDate.now());

        anotherCustomerDTO = new CustomerDTO();
        anotherCustomerDTO.setEmail("other@gmail.com");
        anotherCustomerDTO.setFirstName("Adam");
        anotherCustomerDTO.setSurname("Smith");
        anotherCustomerDTO.setOrders(new ArrayList<OrderDTO>());
        anotherCustomerDTO.setRegistrationDate(LocalDate.now());
    }

    @Test
    public void createTest() {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);

        customerFacade.create(customerDTO, "password");
        verify(customerService).create(c, "password");
    }

    @Test
    public void deleteTest() {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);

        customerFacade.delete(customerDTO.getId());
        verify(customerService).delete(c);
    }

    @Test
    public void findByEmailTest() {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);
        when(customerService.findByEmail(customerDTO.getEmail())).thenReturn(c);

        customerFacade.create(customerDTO, "password");
        Assert.assertEquals(customerFacade.findByEmail(customerDTO.getEmail()), customerDTO);
    }

    @Test
    public void findByNameTest() {
        List<CustomerDTO> expected = new ArrayList<>();
        expected.add(customerDTO);
        List<Customer> list = beanMappingService.mapTo(expected, Customer.class);
        when(customerService.findByName(customerDTO.getFirstName() + " " + customerDTO.getSurname())).thenReturn(list);

        customerFacade.create(customerDTO, "password");
        Assert.assertEquals(customerFacade.findByName("Joe Smith"), expected);
    }

    @Test
    public void findByIdTest() {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);
        when(customerService.findById(customerDTO.getId())).thenReturn(c);

        customerFacade.create(customerDTO, "password");
        Assert.assertEquals(customerFacade.findById(customerDTO.getId()), customerDTO);
    }

    @Test
    public void getAllTest() {
        List<CustomerDTO> expected = new ArrayList<>();
        expected.add(customerDTO);
        expected.add(anotherCustomerDTO);
        List<Customer> list = beanMappingService.mapTo(expected, Customer.class);
        when(customerService.getAll()).thenReturn(list);

        Assert.assertEquals(customerFacade.getAll(), expected);
    }

    @Test
    public void authenticateTest() {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);

        PersonAuthenticateDTO p = new PersonAuthenticateDTO();
        p.setPersonId(customerDTO.getId());
        p.setPassword("password");

        customerFacade.authenticate(p);
        verify(customerService).authenticate(c, "password");
    }
}
