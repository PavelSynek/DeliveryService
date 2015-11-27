package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;

import java.util.Collection;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public interface EmployeeFacade {

    /**
     * Get employee by given ID.
     */
    EmployeeDTO findEmployeeById(Long employeeId);

    /**
     * Get employee by given email.
     */
    EmployeeDTO findEmployeeByEmail(String email);

    /**
     * Register the given employee with the given unencrypted password.
     */
    void createEmployee(EmployeeDTO u, String unencryptedPassword);

    /**
     * Get all registered employees
     */
    Collection<EmployeeDTO> getAllEmployees();

    /**
     * Try to authenticate a employee. Return true only if the hashed password matches the records.
     */
    boolean authenticate(PersonAuthenticateDTO p);
}
