package rest;

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

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import control.client.ClientBoundry;
import control.client.ClientController;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// TODO: do methods for all 4 http methods (405)
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

    // http://localhost:8080/client
    @GET
    @PermitAll
    public Response getKunden() {
        return Response.ok(clientController.getClients()).build();
    }

    // http://localhost:8080/client
    @POST
    @PermitAll
    public Response createClient(CreateClientDAO createClientDAO) {
        if (clientController.createClient(createClientDAO))
            return Response.ok().build();
        return Response.ok("Username existiert").build();
    }

    // http://localhost:8080/client/{username}
    @GET
    @Path("/{username}")
    @PermitAll
    public Response getKunde(@PathParam("username") String username) {
        ClientDAO clientDAO = clientController.getClient(username);
        if (clientDAO == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(clientDAO).build();
    }

    // http://localhost:8080/client/{id}
    @DELETE
    @Path("/{id}")
    // should me admin function
    public Response deleteClient(@PathParam("id") Long id) {
        if (clientController.deleteClient(id))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    } 
    
    // http://localhost:8080/client/{username}/contact
    @POST
    @Path("/{username}/contact")
    @RolesAllowed("Client")
    public Response createContact(@Context SecurityContext sec,
        ContactDAO contactDAO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();
        
        if (clientController.createContact(username, contactDAO))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    } 

    // http://localhost:8080/{username}/contact
    @PUT
    @Path("/{username}/contact")
    @RolesAllowed("Client")
    public Response updateAdresse(@Context SecurityContext sec,
        ContactDAO contactDAO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (clientController.updateContact(username, contactDAO))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    }

    // http://localhost:8080/{username}/contact
    @DELETE
    @Path("/{username}/contact")
    public Response deleteAdresse(@Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (clientController.deleteContact(username))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    }
}
