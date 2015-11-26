package cz.muni.fi.pa165.deliveryservice.dto;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public class EmployeeDTO extends PersonDTO {

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
        if (!(obj instanceof EmployeeDTO))
            return false;
        EmployeeDTO other = (EmployeeDTO) obj;
        if (getEmail() == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!getEmail().equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + getId() +
                ", email='" + getEmail() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", registrationDate=" + getRegistrationDate() +
                '}';
    }
}
