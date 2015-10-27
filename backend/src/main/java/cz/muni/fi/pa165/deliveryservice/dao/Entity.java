package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.dao.access.DBHandler;
import cz.muni.fi.pa165.deliveryservice.entity.DBEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Matej Leško on 2015-10-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

/**
 * Abstract implementation of Entity DAO. Implements all basic
 * operation over any {@link DBEntity} object.
 *
 * In this case suffix Impl
 * was not appropriate, because {@link DBEntity} alone is not accessible from DB.
 *
 * @param <E> Class representing entity of database.
 * @author Matej Leško
 * @version 0.1
 * @see Person
 * @see EmployeeImpl
 */
@Transactional
@Repository
public abstract class Entity<E extends DBEntity> implements EntityTemplate<E> {

    DBHandler<E> dbHandler;

    @PersistenceContext
    protected EntityManager em;
    protected Class<E> eClass;

    public Entity(Class<E> eClass) {
        this.eClass = eClass;
//        dbHandler = new DBHandler<E>(em, eClass);
    }

    @Override
    public E findById(Long id) {
        if (dbHandler == null)
            dbHandler =  new DBHandler<E>(em, eClass);
        return dbHandler.findById(id);
    }

    @Override
    public List<E> findAll() {
        if (dbHandler == null)
            dbHandler =  new DBHandler<E>(em, eClass);
        return dbHandler.findAll();
    }

    @Override
    public void create(E entity) {
        em.persist(entity);
    }

    @Override
    public void remove(E entity) {
        em.remove(findById(entity.getId()));
    }

    @Override
    public E update(E entity) {
        return em.merge(entity);
    }
}
