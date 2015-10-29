/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.deliveryservice.entity;

import javax.persistence.Entity;

/**
 * @author Tomas Milota
 */
@Entity
public class Customer extends DBPerson {

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Customer))
            return false;
        Customer other = (Customer) obj;
        if (this.getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!this.getEmail().equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 29;
        int result = 1;
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }
}
