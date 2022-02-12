package rest.html;

import java.security.Principal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import control.DTO.ClientDTO;
import control.DTO.ReviewDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.review.ReviewBoundary;
import control.review.ReviewController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;


// http://localhost:8080/genrepage/{id}
@ApplicationScoped
@Path("/reviewpage/{id}")



public class ReviewPage {

    @Inject
    Template review;

    
    @Inject
    VinylBoundary vinylController = new VinylController();

    @Inject
    ClientBoundry clientController = new ClientController();


    @Inject
    ReviewBoundary reviewController = new ReviewController();
 
    @GET
    public TemplateInstance getReviewHTML(@PathParam("id") Long id){
       ClientDTO clientDTO = clientController.getClient(id);
     if (clientDTO != null)  
      return review.data("user",clientDTO);
      
      return null;
    }
    
    @POST
    @Path("/edit")
    public Response postReview (@Context SecurityContext sec,@PathParam("id") Long id , @FormParam("review") String review){
    ReviewDTO reviewDTO =new ReviewDTO();
      reviewDTO.review=review;
      reviewDTO.stars=4;
      Principal user = sec.getUserPrincipal();
      String username = user.getName();
     reviewController.createReview(username, reviewDTO, id);
      return Response.ok().build();
    }

}
