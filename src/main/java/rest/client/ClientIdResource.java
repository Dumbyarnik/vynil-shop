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


import control.DAO.ClientDAO;
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
    @Path("/{username}")
    @PermitAll
    public Response getKunde(@PathParam("username") String username) {
        ClientDAO clientDAO = clientController.getClient(username);
        if (clientDAO == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(clientDAO).build();
    }

    @DELETE
    @Path("/{id}")
    // should me admin function
    public Response deleteClient(@PathParam("id") Long id) {
        if (clientController.deleteClient(id))
            return Response.ok().build();
        return Response.status(Status.NOT_FOUND).build();
    } 
    
}
