package cz.muni.fi.pa165.deliveryservice.dao.access;

import cz.muni.fi.pa165.deliveryservice.entity.DBEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Matej Leško on 2015-10-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

/**
 * This class integrates common methods used by DAO objects to access entities stored in database
 *
 * @param <E> Class representing entity of database.
 * @author Matej Leško
 * @version 0.1
 * @see cz.muni.fi.pa165.deliveryservice.dao.EntityTemplate
 * @see cz.muni.fi.pa165.deliveryservice.dao.PersonTemplate
 * @see cz.muni.fi.pa165.deliveryservice.dao.EmployeeDao
 * @see cz.muni.fi.pa165.deliveryservice.dao.ProductDao
 * @see cz.muni.fi.pa165.deliveryservice.dao.OrderDao
 */
public class DBHandler<E extends DBEntity> {
    private EntityManager em;
    private Class<E> eClass;

    /**
     * @param em     Entity Manager should be container managed. Application managed object is not supported
     * @param eClass is a class object of database entity.
     * @see java.lang.Class
     */
    public DBHandler(EntityManager em, Class<E> eClass) {
        this.em = em;
        this.eClass = eClass;
    }

    /**
     * @see cz.muni.fi.pa165.deliveryservice.dao.EntityTemplate#findById(Long)
     */
    public E findById(Long id) {
        return em.find(eClass, id);
    }

    /**
     * @see cz.muni.fi.pa165.deliveryservice.dao.EntityTemplate#findAll()
     */
    public List<E> findAll() {
        CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(eClass);
        Root<E> from = criteria.from(eClass);
        criteria.select(from);
        return em.createQuery(criteria).getResultList();
    }

    /**
     * @see cz.muni.fi.pa165.deliveryservice.dao.PersonTemplate#findByName(String)
     */
    public List<E> findByName(String name) {
        CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(eClass);
        Root<E> from = criteria.from(eClass);
        criteria.select(from);

        Expression<String> exp = builder.concat(from.get("firstName"), " ");
        exp = builder.concat(exp, from.get("surname"));

        criteria.where(builder.like(exp, "%" + name + "%"));
        return em.createQuery(criteria).getResultList();
    }

    /**
     * @see cz.muni.fi.pa165.deliveryservice.dao.PersonTemplate#findByEmail(String)
     */
    public E findByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Cannot search for null e-mail");

        CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<E> criteria = builder.createQuery(eClass);
        Root<E> from = criteria.from(eClass);
        criteria.select(from);
        criteria.where(builder.equal(from.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
}
