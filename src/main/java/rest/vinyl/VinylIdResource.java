package rest.vinyl;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.VinylDTO;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;

// http://localhost:8080/vinyl/{id}
@ApplicationScoped
@Path("/vinyl/{id}")
public class VinylIdResource {

    @Inject
    VinylBoundary vinylController = new VinylController();

    @PostConstruct
    public void init() {  
    }

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
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "fallbackVinyl")
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
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response updateVinyl(@Context SecurityContext sec, 
        @PathParam("id") Long id,
        VinylDTO vinylDTO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (vinylController.updateVinyl(username, id, vinylDTO))
            return Response.ok().build();
        return Response.status(406).entity("Vinyl doesn't exist").build();
    }

    @DELETE
    @RolesAllowed("Client")
    @Operation(summary = "Deletes the vinyl")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success"),
        @APIResponse(responseCode = "406", 
            description = "Vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response deleteVinyl(@Context SecurityContext sec, 
        @PathParam("id") Long id) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (vinylController.deleteVinyl(username, id))
            return Response.ok().build();
        return Response.status(406).entity("Vinyl doesn't exist").build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @POST
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response createVinyl() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    // Fallback methods
    public Response fallbackVinyl(@PathParam("id") Long id){
        VinylDTO vinylDTO = new VinylDTO();
        vinylDTO.id = 0L;
        vinylDTO.title = "Example Vinyl";
        vinylDTO.description = "Cool Vinyl";
        vinylDTO.price = 0L;
        vinylDTO.genre = "ROCK";
        vinylDTO.creator_id = 0L;

        return Response.status(408).entity(vinylDTO).build();
    }

    public Response notAvailable(@Context SecurityContext sec, 
        @PathParam("id") Long id,
        VinylDTO vinylDTO){
        return Response.status(408).build();
    }

    public Response notAvailable(@Context SecurityContext sec, 
        @PathParam("id") Long id){
        return Response.status(408).build();
    }
    
}
