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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import control.client.ClientBoundry;
import control.client.ClientController;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/client
@ApplicationScoped
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {
    @Inject
    ClientBoundry clientController = new ClientController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @Operation(summary = "Shows all the clients")
    @APIResponses(value = 
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ClientDAO.class))))
    @PermitAll
    public Response getClients() {
        return Response.ok(clientController.getClients()).build();
    }

    @POST
    @PermitAll
    @Operation(summary = "Creates new client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Username already exists",
            content = @Content(mediaType = "text/plain"))
        }
    )
    public Response createClient(CreateClientDAO createClientDAO) {
        if (clientController.createClient(createClientDAO))
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
