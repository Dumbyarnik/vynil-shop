package control.client.review;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.CreateReviewDTO;

@Model
@Dependent
public interface ReviewBoundary {
    // review
    public boolean createReview(String username, 
        CreateReviewDTO reviewDTO, Long reviewed_client_id);
}
