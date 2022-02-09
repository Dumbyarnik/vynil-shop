package entities.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Dependent
public class Client implements Serializable {

    // persistence variables
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, unique = true)
    private String username;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Contact contact;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private Collection<Vinyl> vinyls = new ArrayList<Vinyl>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Collection<Review> reviews_from_client;

    @OneToMany(mappedBy = "reviewed_client", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Collection<Review> reviews_about_client;

    public Client(){}

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return Contact return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void deleteContact(){
        this.contact = null;
    }


    /**
     * @return Collection<Vinyl> return the vinyls
     */
    public Collection<Vinyl> getVinyls() {
        return vinyls;
    }

    /**
     * @param vinyls the vinyls to set
     */
    public void setVinyls(Collection<Vinyl> vinyls) {
        this.vinyls = vinyls;
    }


    /**
     * @return Collection<Review> return the reviews_from_client
     */
    public Collection<Review> getReviews_from_client() {
        return reviews_from_client;
    }

    /**
     * @param reviews_from_client the reviews_from_client to set
     */
    public void setReviews_from_client(Collection<Review> reviews_from_client) {
        this.reviews_from_client = reviews_from_client;
    }

    /**
     * @return Collection<Review> return the reviews_about_client
     */
    public Collection<Review> getReviews_about_client() {
        return reviews_about_client;
    }

    /**
     * @param reviews_about_client the reviews_about_client to set
     */
    public void setReviews_about_client(Collection<Review> reviews_about_client) {
        this.reviews_about_client = reviews_about_client;
    }

}
