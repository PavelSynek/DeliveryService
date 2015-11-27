package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.persist.entity.*;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;

import java.util.List;

/**
 * Created by Matej Leško on 2015-10-25.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */


/**
 * Contract containing common methods for basic manipulation with every entity in db.
 *
 * @param <E> Class representing Entity stored in the database.
 * @author Matej Leško
 * @version 0.1
 * @see DBEntity
 * @see DBPerson
 * @see Employee
 * @see Order
 * @see Product
 */
public interface EntityTemplate<E extends DBEntity> {
    /**
     * Search {@link E} by its ID
     *
     * @param id unique ID of {@link E}
     * @return {@link E} found entity
     */
    E findById(Long id) throws ViolentDataAccessException;

    /**
     * Search all record of {@link E} in the database.
     *
     * @return list of found entities
     * @see List
     * @see E
     */
    List<E> findAll();

    /**
     * Persists given entity {@link E} into the database
     *
     * @param entity entity to store
     */
    void create(E entity) throws ViolentDataAccessException;

    /**
     * Removes given entity {@link E}. From the database
     *
     * @param entity entity to remove
     */
    void remove(E entity) throws ViolentDataAccessException;

    /**
     * Updates entity {@link E} in the database
     *
     * @param entity entity to update
     */
    E update(E entity) throws ViolentDataAccessException;

    /**
     * Initialize all database access handlers that entity need for its queries.
     * This must be always called before entity uses its method.
     */
    void initDBAccessHandlers();

}
