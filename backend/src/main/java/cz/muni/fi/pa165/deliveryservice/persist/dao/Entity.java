package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
import cz.muni.fi.pa165.deliveryservice.persist.dao.access.DBHandler;
import cz.muni.fi.pa165.deliveryservice.persist.entity.DBEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.util.List;

/**
 * Created by Matej Leško on 2015-10-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: delivery-service
 */

/**
 * Abstract implementation of Entity DAO. Implements all basic
 * operation over any {@link DBEntity} object.
 * <p>
 * In this case suffix Impl
 * was not appropriate, because {@link DBEntity} alone is not accessible from DB.
 *
 * @param <E> Class representing entity of database.
 * @author Matej Leško
 * @version 0.1
 * @see Person
 * @see EmployeeDaoImpl
 */
@Transactional
@Repository
public abstract class Entity<E extends DBEntity> implements EntityTemplate<E> {

    @PersistenceContext
    protected EntityManager em;
    protected Class<E> eClass;
    DBHandler<E> dbHandler;

    public Entity(Class<E> eClass) {
        this.eClass = eClass;
    }

    @Override
    public E findById(Long id) throws ViolentDataAccessException {
        return dbHandler.findById(id);
    }

    @Override
    public List<E> findAll() {
        return dbHandler.findAll();
    }

    @Override
    public void create(E entity) throws ViolentDataAccessException{
        if (entity.getId() == null)
            em.persist(entity);
        else
            throw new ViolentDataAccessException("Entity: " + entity.getId() + " already exists");
    }

    @Override
    public void remove(E entity) throws ViolentDataAccessException {
        em.remove(findById(entity.getId()));
    }

    @Override
    public E update(E entity) throws ViolentDataAccessException {
        try {
            return em.merge(entity);
        } catch (IllegalArgumentException|TransactionRequiredException e) {
            throw new ViolentDataAccessException("Entity with ID: " + entity.getId() + " cannot be updated");
        }
    }

    @Override
    public void initDBAccessHandlers() {
        if (dbHandler == null)
            dbHandler = new DBHandler<>(em, eClass);
    }
}
