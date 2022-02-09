package rest.review;

import java.security.Principal;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.CreateReviewDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.review.ReviewBoundary;
import control.review.ReviewController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/client/{id}/review
@ApplicationScoped
@Path("/client/{id}/review")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {
    @Inject
    ReviewBoundary reviewController = new ReviewController();

    @PostConstruct
    public void init() {  
    }

    @POST
    @RolesAllowed("Client")
    @Operation(summary = "Creates the review for the customer")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = VinylDTO.class))),
        @APIResponse(responseCode = "406", 
            description = "One of the clients doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response createReview(@PathParam("id") Long id,
        CreateReviewDTO createReviewDTO, @Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (reviewController.createReview(username, createReviewDTO, id))
            return Response.ok().build();
        return Response.status(406).entity("One of the clients doesn't exist").build();
    }
}
