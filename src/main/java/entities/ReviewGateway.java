package entities;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import entities.basic.Review;

@Model
@Dependent
public interface ReviewGateway extends Serializable {
    // review
    public void createReview(Review review);
}
