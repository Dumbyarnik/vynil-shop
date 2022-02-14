/*
    @author: Dennis Dreier
*/
package control.client.review;

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

        // checking if the stars are from 1 to 5
        if (createReviewDTO.stars < 1 || createReviewDTO.stars > 5)
            return false;
        
        // if review is empty
        if (createReviewDTO.review.equals(""))
            return false;

        return reviewRepository.createReview(username, createReviewDTO.review,
            createReviewDTO.stars, reviewed_client_id);
    }

 
    
}
