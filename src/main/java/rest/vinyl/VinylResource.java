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

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import control.DAO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/vinyl
@ApplicationScoped
@Path("/vinyl")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VinylResource {
    
    @Inject
    VinylBoundary vinylController = new VinylController();

    @PostConstruct
    public void init() {  
    }

    @GET
    @PermitAll
    public Response getVinyls() {
        return Response.ok(vinylController.getVinyls()).build();
    }

    @POST
    @RolesAllowed("Client")
    public Response createVinyl(@Context SecurityContext sec,
        VinylDTO vinylDTO) {
        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        vinylController.createVinyl(username, vinylDTO);
            
        return Response.ok().build();
    }
    
}
