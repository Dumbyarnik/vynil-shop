package control.review;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.CreateReviewDTO;
import entities.ClientGateway;
import entities.ReviewGateway;
import entities.basic.Client;
import entities.basic.Review;
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

        Client creator = clientRepository.getClientByName(username);
        Client reviewed_client = clientRepository
            .getClient(reviewed_client_id);

        if(creator == null || reviewed_client == null)
            return false;

        Review review = entityConverter.createReviewDTOtoReview(createReviewDTO, 
            creator, 
            reviewed_client);

        reviewRepository.createReview(review);

        return true;
    }

 
    
}
