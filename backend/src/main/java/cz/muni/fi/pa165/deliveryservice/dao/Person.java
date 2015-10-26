package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.entity.DBPerson;

import java.util.List;

/**
 * Created by Matej Leško on 2015-10-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */
public abstract class Person<E extends DBPerson> extends Entity<E> implements PersonTemplate<E> {

    public Person(Class<E> eClass) {
        super(eClass);
    }

    @Override
    public List<E> findByName(String name) {
        return dbHandler.findByName(name);
    }

    @Override
    public E findByEmail(String email) {
        return dbHandler.findByEmail(email);
    }
}
