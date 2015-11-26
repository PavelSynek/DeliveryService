package cz.muni.fi.pa165.deliveryservice.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tomas Milota on 27.11.2015.
 */
public class CustomerDTO extends PersonDTO {

    private List<OrderDTO> orders = new ArrayList<>();

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

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
        if (!(obj instanceof CustomerDTO))
            return false;
        CustomerDTO other = (CustomerDTO) obj;
        if (getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!getEmail().equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", registrationDate=" + getRegistrationDate() +
                '}';
    }
}
