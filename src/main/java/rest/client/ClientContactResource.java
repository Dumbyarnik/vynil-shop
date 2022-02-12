package rest.client;

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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;

import control.DTO.ContactDTO;
import control.client.ClientBoundry;
import control.client.ClientController;

import javax.ws.rs.core.Context;

// http://localhost:8080/client/contact
@ApplicationScoped
@Path("/client/contact")
public class ClientContactResource {

    @Inject
    ClientBoundry clientController = new ClientController();

    @PostConstruct
    public void init() {  
    }

    @POST
    @RolesAllowed("Client")
    @Operation(summary = "Creates the contact of the client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response createContact(@Context SecurityContext sec,
        ContactDTO contactDTO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();
        
        if (clientController.createContact(username, contactDTO))
            return Response.ok().build();
        return Response.status(406).entity("Client doesn't exist").build();
    } 

    @PUT
    @RolesAllowed("Client")
    @Operation(summary = "Changes the contact of the client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client or contact doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response updateAdresse(@Context SecurityContext sec,
        ContactDTO contactDTO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (clientController.updateContact(username, contactDTO))
            return Response.ok().build();
        return Response.status(406).entity("Client or contact doesn't exist").build();
    }

    @DELETE
    @RolesAllowed("Client")
    @Operation(summary = "Deletes the contact of the client")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", 
            description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "406", 
            description = "Client or contact doesn't exist",
            content = @Content(mediaType = "text/plain"))
        }
    )
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
    public Response deleteContact(@Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();
        if (clientController.deleteContact(username))
            return Response.ok().build();
        return Response.status(406).entity("Client or contact doesn't exist").build();
    }

    //////////////////// NOT AVAILABLE ///////////////////////////////////

    @GET
    @PermitAll
    @Operation(summary = "Doesn't exist")
    @APIResponses(value = @APIResponse(responseCode = "404", 
            description = "Not Found",
            content = @Content(mediaType = "text/plain")))
    public Response getContact() {
        return Response.status(404).entity("Method doesn't exist").build();
    }

    public Response notAvailable(@Context SecurityContext sec,
        ContactDTO contactDTO){
        return Response.status(408).build();
    }

    public Response notAvailable(@Context SecurityContext sec){
        return Response.status(408).build();
    }
    
}
