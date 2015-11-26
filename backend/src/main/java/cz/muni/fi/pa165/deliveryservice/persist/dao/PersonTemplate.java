package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.persist.entity.DBPerson;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;

import java.util.List;

/**
 * Created by Matej Leško on 2015-10-24.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

/**
 * Contract containing common methods for entities in db representing living people.
 *
 * @param <E> Class representing Person stored in the database.
 * @author Matej Leško
 * @version 0.1
 * @see DBPerson
 * @see Employee
 */
public interface PersonTemplate<E extends DBPerson> extends EntityTemplate<E> {

    /**
     * List all people with given name
     *
     * @param name name of the person.
     * @return list of entities representing people with given name.
     * @see List
     * @see E
     */
    List<E> findByName(String name);

    /**
     * Finds a person with given e-mail
     *
     * @param email e-mail
     * @return entity representing a person with given e-mail, or null.
     * @throws IllegalArgumentException
     */
    E findByEmail(String email) throws IllegalArgumentException;

}
