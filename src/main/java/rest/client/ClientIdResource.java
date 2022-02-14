/*
    @author: Daniil Vorobyev
*/
package rest.client;

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
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.ReviewDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.client.ClientIdBoundary;

// http://localhost:8080/client/{id}
@ApplicationScoped
@Path("/client/{id}")
public class ClientIdResource {

    @Inject
    ClientIdBoundary clientController = new ClientController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    @Operation(summary = "Gets the profile of the client")
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
    @Retry(maxRetries = 3)
    @Timeout(250)
    @Fallback(fallbackMethod = "fallbackClient")
    public Response getClient(@PathParam("id") Long id) {
        ClientDTO clientDTO = clientController.getClient(id);
        if (clientDTO == null)
            return Response.status(406).entity("Client doesn't exist").build();
        return Response.ok(clientDTO).build();
    }

    @DELETE
    @RolesAllowed("Admin")
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
    @Retry(maxRetries = 1)
    @Timeout(250)
    @Fallback(fallbackMethod = "notAvailable")
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

    // Fallback methods
    public Response fallbackClient(@PathParam("id") Long id){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.id = 0L;
        clientDTO.username = "client";

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.email = "client@mail.com";
        contactDTO.phone = "012345678";
        clientDTO.contact = contactDTO;

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.id = 0L;
        reviewDTO.review = "Great client";
        reviewDTO.stars = 5;
        reviewDTO.reviewed_client_id = 0L;
        reviewDTO.creator_id = 1L;
        clientDTO.reviews.add(reviewDTO);

        return Response.status(408).entity(clientDTO).build();
    }
    
    public Response notAvailable(@PathParam("id") Long id){
        return Response.status(408).build();
    }
}
