package rest.vinyl;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/vinyl/{id}
@ApplicationScoped
@Path("/vinyl/{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    public Response getVinyl(@PathParam("id") Long id) {
        return Response.ok(vinylController.getVinyl(id)).build();
    }

    @PUT
    @RolesAllowed("Client")
    @Operation(summary = "Gets the profile")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client or vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response updateVinyl(@PathParam("id") Long id,
        VinylDTO vinylDTO) {
        vinylController.updateVinyl(id, vinylDTO);
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed("Client")
    @Operation(summary = "Gets the profile")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client or vinyl doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response deleteVinyl(@PathParam("id") Long id) {
        if (vinylController.deleteVinyl(id))
            return Response.ok().build();
        return Response.status(406).entity("Client or vinyl doesn't exist").build();
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


    
}
