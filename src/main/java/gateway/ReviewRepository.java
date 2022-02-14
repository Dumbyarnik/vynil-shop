/*
    @author: Dennis Dreier
*/
package gateway;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.ReviewGateway;
import entities.basic.Client;
import entities.basic.Review;

@Model
@Dependent
public class ReviewRepository implements ReviewGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Inject
    private DatabaseService databaseService;

    @Override
    @Transactional
    public boolean createReview(String username, String review,
        int stars, Long reviewed_client_id) {

        Client creator = databaseService.getClientByName(username);
        Client reviewed_client = em.find(Client.class, reviewed_client_id);

        if(creator == null || reviewed_client == null)
            return false;

        // if the client writes review about himself
        if (creator.getUsername().equals(reviewed_client.getUsername()))
            return false;
        
        // if the client writes interview the second time 
        for (Review review_tmp : creator.getReviews_from_client()){
            if (review_tmp.getReviewed_client().getId() == reviewed_client_id)
                return false;
        }

        Review review_obj = new Review();
        // setting up relationships
        review_obj.setCreator(creator);
        review_obj.setReviewed_client(reviewed_client);
        // setting values
        review_obj.setReview(review);
        review_obj.setStars(stars);

        em.persist(review_obj);

        return true;
    }
    
}
