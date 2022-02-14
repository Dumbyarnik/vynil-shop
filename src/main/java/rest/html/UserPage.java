package rest.html;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import control.DTO.ClientDTO;
import control.DTO.CreateClientDTO;
import control.DTO.CreateReviewDTO;
import control.DTO.ReviewDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.client.review.ReviewController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;

// http://localhost:8080/template/client
@ApplicationScoped
@Path("template/client")

public class UserPage {

    @Inject
    Template user;

    @Inject
    Template createUser;

    @Inject
    Template notAllowed;

    @Inject
    Template genres;

    @Inject
    Template noAccess;

    @Inject
    Template error;

    @Inject
    ClientBoundry clientController = new ClientController();

    @GET
    @RolesAllowed("Client")
    public TemplateInstance getUserHimseldlHTML(@Context SecurityContext sec) {
        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return noAccess.instance();
        
        String username = userTMP.getName();
            
        ClientDTO clientDTO = clientController.getClientByUsername(username);
        
        if (clientDTO != null)
            return user.data("user", clientDTO);

        return error.instance();
    }

    @GET
    @Path("/{id}")
    public TemplateInstance getUserlHTML(@PathParam("id") Long id) {
        ClientDTO clientDTO = clientController.getClient(id);
        
        if (clientDTO != null)
            return user.data("user", clientDTO);

        return error.instance();
    }

    @GET
    @Path("/create")
    public TemplateInstance getCreateUserHTML() {
        return this.createUser.instance();
    }

    @POST
    @Path("/create")
    public TemplateInstance postCreateUserHTML(
            @FormParam("username") String username,
            @FormParam("password") String password) {

        CreateClientDTO createClientDTO = new CreateClientDTO();
        createClientDTO.username = username;
        createClientDTO.password = password;

        if (clientController.createClient(createClientDTO))
            return this.genres.instance();
        return this.notAllowed.instance();
    }

  
}
