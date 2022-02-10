package rest.favourites;

import java.security.Principal;

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

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.favourites.FavouritesBoundary;
import control.favourites.FavouritesController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/client/favourites/{vinyl_id}
@ApplicationScoped
@Path("/client/favourites/{vinyl_id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = VinylDTO.class))),
        @APIResponse(responseCode = "406", 
            description = "Client or Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response createVinyl(@Context SecurityContext sec,
        @PathParam("vinyl_id") Long vinyl_id) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (favouritesController.createFavourite(username, vinyl_id))
            return Response.ok().build();
        
        return Response.status(406).entity("Vinyl doesn't exist").build(); 
    }

    @DELETE
    @RolesAllowed("Client")
    @Operation(summary = "Deletes the vinyl")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response deleteVinyl(@Context SecurityContext sec,
    @PathParam("vinyl_id") Long vinyl_id) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (favouritesController.deleteFavourite(username, vinyl_id ))
            return Response.ok().build();
        return Response.status(406).entity("Vinyl doesn't exist").build();
    }




/*

    @GET
    @PermitAll
    @Operation(summary = "Gets the vinyl")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = VinylDTO.class))),
        @APIResponse(responseCode = "406", 
            description = "Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response getVinyl(@PathParam("id") Long id) {
        VinylDTO vinylDTO = vinylController.getVinyl(id);
        if (vinylDTO == null)
            return Response.status(406).entity("Vinyl doesn't exist").build(); 
        return Response.ok(vinylController.getVinyl(id)).build();
    }

    @PUT
    @RolesAllowed("Client")
    @Operation(summary = "Updates the vinyl")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response updateVinyl(@PathParam("id") Long id,
        VinylDTO vinylDTO) {
        if (vinylController.updateVinyl(id, vinylDTO))
            return Response.ok().build();
        return Response.status(406).entity("Vinyl doesn't exist").build();
    }

    

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    */
    
}
