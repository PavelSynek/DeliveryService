package cz.muni.fi.pa165.deliveryservice.facade.tests;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.CustomerServiceImpl;
import cz.muni.fi.pa165.deliveryservice.service.facade.CustomerFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public class CustomerFacadeTest {

    @Mock
    private CustomerServiceImpl customerService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private CustomerFacade customerFacade;

    private CustomerDTO customerDTO;
    private CustomerDTO anotherCustomerDTO;
    private Customer customer;
    private Customer anotherCustomer;

    @BeforeClass
    public void setUp() throws ServiceException {
        customerFacade = new CustomerFacadeImpl();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCustomers() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1l);
        customerDTO.setEmail("example@gmail.com");
        customerDTO.setFirstName("Joe");
        customerDTO.setSurname("Smith");
        customerDTO.setOrders(new ArrayList<>());
        customerDTO.setRegistrationDate(LocalDate.of(2015, 1, 1));

        anotherCustomerDTO = new CustomerDTO();
        anotherCustomerDTO.setId(100l);
        anotherCustomerDTO.setEmail("other@gmail.com");
        anotherCustomerDTO.setFirstName("Adam");
        anotherCustomerDTO.setSurname("Smith");
        anotherCustomerDTO.setOrders(new ArrayList<>());
        anotherCustomerDTO.setRegistrationDate(LocalDate.of(2015, 1, 1));

        customer = new Customer();
        customer.setId(1l);
        customer.setEmail("example@gmail.com");
        customer.setFirstName("Joe");
        customer.setSurname("Smith");
        customer.setOrders(new ArrayList<>());
        customer.setRegistrationDate(LocalDate.of(2015, 1, 1));

        anotherCustomer = new Customer();
        anotherCustomer.setId(100l);
        anotherCustomer.setEmail("other@gmail.com");
        anotherCustomer.setFirstName("Adam");
        anotherCustomer.setSurname("Smith");
        anotherCustomer.setOrders(new ArrayList<>());
        anotherCustomer.setRegistrationDate(LocalDate.of(2015, 1, 1));
    }

    @Test
    public void createTest() {
        /*Customer c = beanMappingService.mapTo(customerDTO, Customer.class);

        customerFacade.create(customerDTO, "password");
        verify(customerService).create(c, "password");*/
    }

    @Test
    public void deleteTest() {
        /*Customer c = beanMappingService.mapTo(customerDTO, Customer.class);

        customerFacade.delete(customerDTO.getId());
        verify(customerService).delete(c);*/
    }

    @Test
    public void findByEmailTest() {
        /*Customer c = beanMappingService.mapTo(customerDTO, Customer.class);
        when(customerService.findByEmail(customerDTO.getEmail())).thenReturn(c);

        customerFacade.create(customerDTO, "password");
        assertEquals(customerFacade.findByEmail(customerDTO.getEmail()), customerDTO);*/
    }

    @Test
    public void findByNameTest() {
        /*List<CustomerDTO> expected = new ArrayList<>();
        expected.add(customerDTO);
        List<Customer> list = beanMappingService.mapTo(expected, Customer.class);
        when(customerService.findByName(customerDTO.getFirstName() + " " + customerDTO.getSurname())).thenReturn(list);

        customerFacade.create(customerDTO, "password");
        assertEquals(customerFacade.findByName("Joe Smith"), expected);*/
    }

    @Test
    public void findByIdTest() {
        when(customerService.findById(customerDTO.getId())).thenReturn(customer);
        when(beanMappingService.mapTo(customer, CustomerDTO.class)).thenReturn(customerDTO);

        assertEquals(customerFacade.findById(customerDTO.getId()), customerDTO);
    }

    @Test
    public void getAllTest() {
        List<CustomerDTO> expected = new ArrayList<>();
        expected.add(customerDTO);
        expected.add(anotherCustomerDTO);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(anotherCustomer);

        when(beanMappingService.mapTo(customers, CustomerDTO.class)).thenReturn(expected);
        when(customerService.getAll()).thenReturn(customers);

        assertEquals(customerFacade.getAll(), expected);
    }

    @Test
    public void authenticateTest() {
        when(customerService.findById(customer.getId())).thenReturn(customer);

        PersonAuthenticateDTO p = new PersonAuthenticateDTO();
        p.setPersonId(customerDTO.getId());
        p.setPassword("password");

        customerFacade.authenticate(p);
        verify(customerService).authenticate(customer, "password");
    }
}