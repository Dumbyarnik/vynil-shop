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

import entities.basic.Client;
import gateway.ClientRepository;

import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientRepository clientRepository;

    @PostConstruct
    public void init() {  
        clientRepository.createClient("user");
    }

    // http://localhost:8080/client
    @GET
    public Response getKunde() {
        Client client = clientRepository.getClient("user");
        if (client == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.ok(client).build();
    }
    
}
