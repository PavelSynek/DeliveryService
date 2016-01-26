package cz.muni.fi.pa165.deliveryservice.service.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Transactional
@Service
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public CustomerDTO findById(Long customerId) {
        Customer customer = customerService.findById(customerId);
        return (customer == null) ? null : beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerService.findByEmail(email);
        return (customer == null) ? null : beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public Long create(CustomerDTO customerDTO, String unencryptedPassword) {
        Customer customer = beanMappingService.mapTo(customerDTO, Customer.class);
        customerService.create(customer, unencryptedPassword);
        customerDTO.setId(customer.getId());

        return customerDTO.getId();
    }

    @Override
    public Collection<CustomerDTO> getAll() {
        return beanMappingService.mapTo(customerService.getAll(), CustomerDTO.class);
    }

    @Override
    public boolean authenticate(PersonAuthenticateDTO p) {
        Customer customer = customerService.findById(p.getPersonId());
        return customerService.authenticate(customer, p.getPassword());
    }

    @Override
    public void delete(Long id) {
        Customer customer = customerService.findById(id);
        customerService.delete(customer);
    }

    @Override
    public List<CustomerDTO> findByName(String name) {
        List<Customer> customers = customerService.findByName(name);
        return beanMappingService.mapTo(customers, CustomerDTO.class);
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        Customer c = beanMappingService.mapTo(customerDTO, Customer.class);
        customerService.update(c);
    }
}
