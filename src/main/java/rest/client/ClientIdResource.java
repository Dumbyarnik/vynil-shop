package rest.client;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.ClientDTO;
import control.client.ClientBoundry;
import control.client.ClientController;

import javax.ws.rs.core.MediaType;

// http://localhost:8080/client/{id}
@ApplicationScoped
@Path("/client/{id}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientIdResource {

    @Inject
    ClientBoundry clientController = new ClientController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    @Operation(summary = "Gets the profile")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClientDTO.class))),
        @APIResponse(responseCode = "406", 
            description = "Client doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response getClient(@PathParam("id") Long id) {
        ClientDTO clientDTO = clientController.getClient(id);
        if (clientDTO == null)
            return Response.status(406).entity("Client doesn't exist").build();
        return Response.ok(clientDTO).build();
    }

    @DELETE
    // should me admin function
    @Operation(summary = "Deletes the client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response deleteClient(@PathParam("id") Long id) {
        if (clientController.deleteClient(id))
            return Response.ok().build();
            return Response.status(406).entity("Client doesn't exist").build();
    } 

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @POST
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response createClient() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateClient() {
        return Response.status(404).entity("Method doesn't exist").build();
    }
    
}
