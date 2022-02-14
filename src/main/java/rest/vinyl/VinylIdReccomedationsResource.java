package rest.vinyl;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
import control.vinyl.VinylReccomendationsBoundary;

// http://localhost:8080/vinyl/{id}/reccomedations
@ApplicationScoped
@Path("/vinyl/{id}/reccomedations")
public class VinylIdReccomedationsResource {

    @Inject
    VinylReccomendationsBoundary vinylController = new VinylController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    @Operation(summary = "Gets the vinyl reccomedations")
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
    @Fallback(fallbackMethod = "fallbackReccomendations")
    public Response getReccomedations(@PathParam("id") Long id) {
        Collection<VinylDTO> vinylDTO = vinylController
            .getVinylReccomedations(id);
        if (vinylDTO == null)
            return Response.status(406).entity("Vinyl doesn't exist").build();
        return Response.ok(vinylDTO).build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////
    @POST
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response createVinylReccomedations() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateVinylReccomedations() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @DELETE
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response deleteVinylReccomedations() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    // Fallback methods
    public Response fallbackReccomendations(@PathParam("id") Long id){
        VinylDTO vinylDTO = new VinylDTO();
        vinylDTO.id = 0L;
        vinylDTO.title = "Example Vinyl";
        vinylDTO.description = "Cool Vinyl";
        vinylDTO.price = 0L;
        vinylDTO.genre = "ROCK";
        vinylDTO.creator_id = 0L;

        return Response.status(408).entity(vinylDTO).build();
    }
}
