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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;

import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import entities.VinylGateway;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.ws.rs.core.Context;

// http://localhost:8080/vinyl
@ApplicationScoped
@Path("/vinyl")
public class VinylResource {
    
    @Inject
    VinylBoundary vinylController = new VinylController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    @Operation(summary = "Gets all the vinyls")
    @APIResponses(value = 
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = VinylDTO.class))))
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "fallbackVinyls")
    public Response getVinyls() {
        return Response.ok(vinylController.getVinyls()).build();
    }
    
    @POST
    @RolesAllowed("Client")
    @Operation(summary = "Creates new vinyl")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success"),
        @APIResponse(responseCode = "406", 
            description = "Client doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response createVinyl(@Context SecurityContext sec,
        CreateVinylDTO createVinylDTO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (vinylController.createVinyl(username, createVinylDTO))
            return Response.ok().build();
        return Response.status(406).entity("Client doesn't exist").build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateVinyl() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @DELETE
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response deleteVinyl() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    // Fallback methods
    public Response fallbackVinyls(){
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
        CreateVinylDTO createVinylDTO){
        return Response.status(408).build();
    }
    
}
