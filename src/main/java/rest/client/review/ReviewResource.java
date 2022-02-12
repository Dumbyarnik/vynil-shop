package rest.client.review;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;

import control.DTO.CreateReviewDTO;
import control.client.review.ReviewBoundary;
import control.client.review.ReviewController;

import javax.ws.rs.core.Context;

// http://localhost:8080/client/{id}/review
@ApplicationScoped
@Path("/client/{id}/review")
public class ReviewResource {
    @Inject
    ReviewBoundary reviewController = new ReviewController();

    @PostConstruct
    public void init() {  
    }

    @POST
    @RolesAllowed("Client")
    @Operation(summary = "Creates the review for the client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "One of the clients doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 1)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response createReview(@PathParam("id") Long id,
        CreateReviewDTO createReviewDTO, @Context SecurityContext sec) {
        // the customer who creates the review
        Principal user = sec.getUserPrincipal();
        String username = user.getName();
        // id of the client who is is in id
        if (reviewController.createReview(username, createReviewDTO, id))
            return Response.ok().build();
        return Response.status(406).entity("One of the clients doesn't exist").build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////
    @GET
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response getReview(@PathParam("id") Long id) {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateReview(@PathParam("id") Long id) {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @DELETE
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response deleteReview(@PathParam("id") Long id) {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    public Response notAvailable(@PathParam("id") Long id,
        CreateReviewDTO createReviewDTO, @Context SecurityContext sec){
        return Response.status(408).build();
    }
}
