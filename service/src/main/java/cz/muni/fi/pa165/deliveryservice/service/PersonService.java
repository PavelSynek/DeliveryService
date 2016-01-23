package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.DBPerson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Service
public interface PersonService<T extends DBPerson> {

    /**
     * Register the given person with the given unencrypted password.
     */
    void create(T person, String unencryptedPassword);

    /**
     * Get all registered customers or employees.
     */
    List<T> getAll();

    /**
     * Try to authenticate a person. Return true only if the hashed password matches the records.
     */
    boolean authenticate(T person, String password);

    /**
     * Get person by given ID.
     */
    T findById(Long personId);

    /**
     * Get person by given email.
     */
    T findByEmail(String email);

    /**
     * Find and remove person in the system.
     */
    void delete(T person);

    List<T> findByName(String name);

    void update(T person);
}
