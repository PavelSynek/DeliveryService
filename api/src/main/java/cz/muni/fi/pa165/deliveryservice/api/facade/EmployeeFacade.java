package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;

import java.util.Collection;
import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public interface EmployeeFacade {

    /**
     * Get employee by given ID.
     */
    EmployeeDTO findById(Long employeeId);

    /**
     * Get employee by given email.
     */
    EmployeeDTO findByEmail(String email);

    /**
     * Register the given employee with the given unencrypted password.
     */
    void create(EmployeeDTO u, String unencryptedPassword);

    /**
     * Get all registered employees
     */
    Collection<EmployeeDTO> getAll();

    /**
     * Try to authenticate a employee. Return true only if the hashed password matches the records.
     */
    boolean authenticate(PersonAuthenticateDTO p);

    /**
     * Remove employee by given email.
     */
    void delete(Long id);

    /**
     * Get all employees with specific name.
     */
    List<EmployeeDTO> findByName(String name);
}
