package gateway;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.ClientGateway;
import entities.ReviewGateway;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Review;
import entities.security.UserLogin;
import io.quarkus.security.User;

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
