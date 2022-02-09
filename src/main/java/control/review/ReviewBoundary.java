package control.review;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.CreateReviewDTO;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;

@Model
@Dependent
public interface ReviewBoundary {
    // review
    public boolean createReview(String username, 
        CreateReviewDTO reviewDTO, Long reviewed_client_id);
}
