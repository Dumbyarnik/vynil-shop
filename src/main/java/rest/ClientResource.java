package rest;

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
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;

import control.ClientBoundry;
import control.ClientController;
import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.MediaType;

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
    public Response getKunden() {
        return Response.ok(clientController.getClients()).build();
    }

    // http://localhost:8080/client
    @POST
    public Response createClient(ClientDAO clientDAO) {
        if (clientController.createClient(clientDAO))
            return Response.ok().build();
        return Response.ok("Dieser Username existiert bereits").build();
    }

    // http://localhost:8080/client/{username}
    @GET
    @Path("/{username}")
    public Response getKunde(@PathParam("username") String username) {
        ClientDAO clientDAO = clientController.getClient(username);
        if (clientDAO == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(clientDAO).build();
    }

    // http://localhost:8080/client/{id}
    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") Long id) {
        if (clientController.deleteClient(id))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    } 
    
    // http://localhost:8080/client/{username}/contact
    @POST
    @Path("/{username}/contact")
    public Response createContact(@PathParam("username") String username,
        ContactDAO contactDAO) {
        if (clientController.createContact(username, contactDAO))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    } 

    // http://localhost:8080/{username}/contact
    @PUT
    @Path("/{username}/contact")
    public Response updateAdresse(@PathParam("username") String username,
        ContactDAO contactDAO) {
        if (clientController.updateContact(username, contactDAO))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    }

    // http://localhost:8080/{username}/contact
    @DELETE
    @Path("/{username}/contact")
    public Response deleteAdresse(@PathParam("username") String username) {
        if (clientController.deleteContact(username))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    }
}
