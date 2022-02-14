/*
    @author: Dennis Dreier
*/
package rest.client.favourites;

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

import control.client.favourites.FavouritesBoundary;
import control.client.favourites.FavouritesController;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;

import javax.ws.rs.core.Context;

// http://localhost:8080/client/favourites/{vinyl_id}
@ApplicationScoped
@Path("/client/favourites/{vinyl_id}")
public class FavouritesIdResource {

    @Inject
    FavouritesBoundary favouritesController = new FavouritesController();

    @PostConstruct
    public void init() {  
    }

    @POST
    @RolesAllowed("Client")
    @Operation(summary = "Creates the favourite")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client or Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    //@Retry(maxRetries = 3)
    //@Timeout(100000)
    //@Fallback(fallbackMethod = "notAvailable")
    public Response createFavourite(@Context SecurityContext sec,
        @PathParam("vinyl_id") Long vinyl_id) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (favouritesController.createFavourite(username, vinyl_id))
            return Response.ok().build();
        
        return Response.status(406).entity("Vinyl doesn't exist").build(); 
    }

    @DELETE
    @RolesAllowed("Client")
    @Operation(summary = "Deletes the favourite")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success"),
        @APIResponse(responseCode = "406", 
            description = "Vinyl or client doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response deleteVinyl(@Context SecurityContext sec,
        @PathParam("vinyl_id") Long vinyl_id) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (favouritesController.deleteFavourite(username, vinyl_id))
            return Response.ok().build();
        return Response.status(406).entity("Vinyl or client doesn't exist").build();
    }


    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @GET
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response getFavourite() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateFavourite() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    public Response notAvailable(@Context SecurityContext sec,
        @PathParam("vinyl_id") Long vinyl_id){
        return Response.status(408).build();
    }  
    
}
