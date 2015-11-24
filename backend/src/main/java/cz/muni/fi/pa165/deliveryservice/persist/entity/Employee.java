package cz.muni.fi.pa165.deliveryservice.persist.entity;

/**
 * Created by Matej Leško on 2015-10-24.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

import javax.persistence.Entity;

/**
 * Entity representing Employee in the system.
 *
 * @author Matej Leško
 * @version 0.1
 */
@Entity
public class Employee extends DBPerson {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Employee))
            return false;
        Employee other = (Employee) obj;
        if (getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!getEmail().equals(other.getEmail()))
            return false;
        return true;
    }

}
