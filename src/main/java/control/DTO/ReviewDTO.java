/*
    @author: Dennis Dreier
*/
package control.DTO;

public class ReviewDTO extends CreateReviewDTO {
    public Long id;
    public Long creator_id;   
    public Long reviewed_client_id; 
}
