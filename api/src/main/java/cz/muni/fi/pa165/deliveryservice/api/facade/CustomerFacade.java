package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public interface CustomerFacade {

    /**
     * Get customer by given ID.
     */
    CustomerDTO findById(Long customerId);

    /**
     * Get customer by given email.
     */
    CustomerDTO findByEmail(String email);

    /**
     * Register the given customer with the given unencrypted password.
     */
    Long create(CustomerDTO customerDTO, String unencryptedPassword);

    /**
     * Get all registered customers.
     */
    Collection<CustomerDTO> getAll();

    /**
     * Try to authenticate a customer. Return true only if the hashed password matches the records.
     */
    boolean authenticate(PersonAuthenticateDTO p);

    /**
     * Remove customer by given email.
     */
    void delete(Long id);

    /**
     * Get all customers with specific name.
     */
    List<CustomerDTO> findByName(String name);

    void update(CustomerDTO customerDTO);
}
