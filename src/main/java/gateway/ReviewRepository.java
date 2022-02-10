package gateway;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.ReviewGateway;
import entities.basic.Review;

@Model
@Dependent
public class ReviewRepository implements ReviewGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Override
    @Transactional
    public void createReview(Review review) {
        em.persist(review);
    }
    
}
