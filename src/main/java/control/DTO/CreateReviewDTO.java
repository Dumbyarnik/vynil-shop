package control.DTO;

import java.io.Serializable;

public class CreateReviewDTO implements Serializable {
    public int stars;
    public String review;
    public Long creator_id;
    public Long reviews_client_id;
}
