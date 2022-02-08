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

    // http://localhost:8080/client
    @GET
    @PermitAll
    public Response getClients() {
        return Response.ok(clientController.getClients()).build();
    }

    @POST
    @PermitAll
    public Response createClient(CreateClientDAO createClientDAO) {
        if (clientController.createClient(createClientDAO))
            return Response.ok().build();
        return Response.ok("This username already exists").build();
    }


    //TODO: add put and delet
}
