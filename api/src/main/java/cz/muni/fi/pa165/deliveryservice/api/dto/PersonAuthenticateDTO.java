package cz.muni.fi.pa165.deliveryservice.api.dto;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
public class PersonAuthenticateDTO {
    private Long personId;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
