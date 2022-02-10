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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;

import control.DTO.VinylDTO;
import control.client.favourites.FavouritesBoundary;
import control.client.favourites.FavouritesController;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.ws.rs.core.Context;

// http://localhost:8080/client/favourites
@ApplicationScoped
@Path("/client/favourites")
public class FavouritesResource {

    @Inject
    FavouritesBoundary favouritesController = new FavouritesController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @RolesAllowed("Client")
    @Operation(summary = "Gets all the favourites of the client")
    @APIResponses(value = 
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = VinylDTO.class))))
    public Response getFavourites(@Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        return Response.ok(favouritesController.getFavourites(username)).build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @POST
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response postFavourites() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateFavourites() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @DELETE
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response deleteFavourites() {
        return Response.status(404).entity("Method doesn't exist").build();
    }
    
}
