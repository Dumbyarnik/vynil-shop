package control.review;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.CreateReviewDTO;
import entities.ClientGateway;
import entities.ReviewGateway;
import gateway.ClientRepository;
import gateway.ReviewRepository;

@Model
@Dependent
public class ReviewController implements ReviewBoundary {

    @Inject
    ReviewGateway reviewRepository = new ReviewRepository();

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public boolean createReview(String username, 
        CreateReviewDTO createReviewDTO, Long reviewed_client_id) {

        return reviewRepository.createReview(username, createReviewDTO.review,
            createReviewDTO.stars, reviewed_client_id);
    }

 
    
}
