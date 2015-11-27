package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;

import java.util.Collection;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public interface CustomerFacade {

    /**
     * Get customer by given ID.
     */
    CustomerDTO findCustomerById(Long customerId);

    /**
     * Get customer by given email.
     */
    CustomerDTO findCustomerByEmail(String email);

    /**
     * Register the given customer with the given unencrypted password.
     */
    void createCustomer(CustomerDTO u, String unencryptedPassword);

    /**
     * Get all registered customers.
     */
    Collection<CustomerDTO> getAllCustomers();

    /**
     * Try to authenticate a customer. Return true only if the hashed password matches the records.
     */
    boolean authenticate(PersonAuthenticateDTO p);
}
