package cz.muni.fi.pa165.deliveryservice.entity;

/**
 * Created by Matej Leško on 2015-10-24.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Abstract class representing DBPerson using the system together with
 * common data fields and encapsulation.
 *
 * @author Matej Leško
 * @version 0.1
 */

@Entity
public abstract class DBPerson extends DBEntity {

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    @NotNull
    private String email;
    private String firstName;
    private String surname;

    @Pattern(regexp = "\\+?\\d+")
    private String phone;

    @NotNull
    private LocalDate registrationDate;

    /**
     * @return email of the person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email of the person.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return first name, known as a "given name" too.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName first name, known as a "given name" too.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return surname, known as a "family name" too.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname known as a "family name" too.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return date of registration
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate date of registration
     */
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
