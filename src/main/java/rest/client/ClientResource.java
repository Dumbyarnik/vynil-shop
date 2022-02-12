package rest.client;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.ClientDTO;
import control.DTO.CreateClientDTO;
import control.client.ClientBoundry;
import control.client.ClientController;

// http://localhost:8080/client
@ApplicationScoped
@Path("/client")
public class ClientResource {
    @Inject
    ClientBoundry clientController = new ClientController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    @Operation(summary = "Shows all clients")
    @APIResponses(value = 
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ClientDTO.class))))
    public Response getClients(@Context SecurityContext sec) {
        return Response.ok(clientController.getClients()).build();
    }

    @POST
    @PermitAll
    @Operation(summary = "Creates a new client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Username already exists",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response createClient(CreateClientDTO createClientDTO) {
        if (clientController.createClient(createClientDTO))
            return Response.ok().build();
        return Response.status(406).entity("Username already exists").build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @PUT
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response updateClient() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    @DELETE
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response deleteClient() {
        return Response.status(404).entity("Method doesn't exist").build();
    }
}
