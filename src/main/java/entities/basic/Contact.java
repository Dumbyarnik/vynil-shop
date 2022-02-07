package entities.basic;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Contact implements Serializable{

    private String email;
    private String phone;

    @OneToOne(mappedBy = "contact")
    private Client Client;

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return Client return the Client
     */
    public Client getClient() {
        return Client;
    }

    /**
     * @param Client the Client to set
     */
    public void setClient(Client Client) {
        this.Client = Client;
    }

}
