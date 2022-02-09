package entities.basic;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.smallrye.common.constraint.NotNull;

@Entity
@Dependent
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int stars;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private Client creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewed_client_id", nullable = false)
    private Client reviewed_client;
    

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return int return the stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * @return String return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * @return Client return the creator
     */
    public Client getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(Client creator) {
        this.creator = creator;
    }

    /**
     * @return Client return the reviewed_client
     */
    public Client getReviewed_client() {
        return reviewed_client;
    }

    /**
     * @param reviewed_client the reviewed_client to set
     */
    public void setReviewed_client(Client reviewed_client) {
        this.reviewed_client = reviewed_client;
    }

}
