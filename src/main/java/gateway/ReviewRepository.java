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

        Review review_obj = new Review();
        // setting up relationships
        review_obj.setCreator(creator);
        review_obj.setReviewed_client(reviewed_client);
        // setting values
        review_obj.setReview(review);
        review_obj.setStars(stars);

        System.out.println(review_obj.toString());

        em.persist(review_obj);

        return true;
    }
    
}
