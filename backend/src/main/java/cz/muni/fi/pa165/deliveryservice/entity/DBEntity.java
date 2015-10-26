package cz.muni.fi.pa165.deliveryservice.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Matej Leško on 2015-10-25.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

/**
 * Basic abstraction of entity from database
 * @author Matej Leško
 * @version 0.1
 */
public abstract class DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * @return ID of entity
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id ID of entity
     */
    public void setId(Long id) {
        this.id = id;
    }
}
