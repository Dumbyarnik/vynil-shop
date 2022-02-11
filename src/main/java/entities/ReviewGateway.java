package entities;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

@Model
@Dependent
public interface ReviewGateway extends Serializable {
    // review
    public boolean createReview(String username, String review,
        int stars, Long reviewed_client_id);
}
