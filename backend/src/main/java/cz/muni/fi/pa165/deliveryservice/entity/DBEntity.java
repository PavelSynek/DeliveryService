package cz.muni.fi.pa165.deliveryservice.entity;

import javax.persistence.*;

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

@Entity
@Table(schema="CNTRCT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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
