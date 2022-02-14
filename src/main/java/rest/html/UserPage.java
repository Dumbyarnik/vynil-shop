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
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.CreateReviewDTO;
import control.DTO.ReviewDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientContactBoundary;
import control.client.ClientController;
import control.client.ClientIdBoundary;
import control.client.review.ReviewController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;

// http://localhost:8080/template/client
@ApplicationScoped
@Path("template/client")

public class UserPage {

    // User HTMLs
    @Inject
    Template user;

    @Inject
    Template userAccount;

    @Inject
    Template createUser;

    @Inject
    Template createContact;

    // Error HTMLs
    @Inject
    Template notAllowed;

    @Inject
    Template genres;

    @Inject
    Template noAccess;

    @Inject
    Template error;

    // Controllers
    @Inject
    ClientIdBoundary clientIdController = new ClientController();

    @Inject
    ClientContactBoundary clientContactController = new ClientController();

    @Inject
    ClientBoundry clientController = new ClientController();

    // Returns HTML of the account
    @GET
    @RolesAllowed("Client")
    public TemplateInstance getUserHimselflHTML(@Context SecurityContext sec) {
        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return noAccess.instance();

        String username = userTMP.getName();
        ClientDTO clientDTO = clientController.getClientByUsername(username);

        if (clientDTO != null)
            return userAccount.data("user", clientDTO);
        return error.instance();
    }

    // Returns the account of a user
    @GET
    @Path("/{id}")
    public TemplateInstance getUserlHTML(@PathParam("id") Long id) {
        ClientDTO clientDTO = clientIdController.getClient(id);
        if (clientDTO != null)
            return user.data("user", clientDTO);
        return error.instance();
    }

    // Returns the HTML of create User
    @GET
    @Path("/create")
    public TemplateInstance getCreateUserHTML() {
        return this.createUser.instance();
    }

    // Creates a user and returns main page
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

        return this.notAllowed.data("error", "This username already exists");
    }

    // Returns HTML form to create a contact
    @GET
    @Path("/create/contact")
    public TemplateInstance getCreateContactHTML(@Context SecurityContext sec) {
        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return noAccess.instance();
        
        String username = userTMP.getName();
        ClientDTO clientDTO = clientController.getClientByUsername(username);

        if (clientDTO.contact != null)
            return notAllowed.data("error", "You already have a contact");
        
        return this.createContact.instance();
    }

    // Creates contact and returns User's page
    @POST
    @Path("/create/contact")
    public TemplateInstance createContactHTML(@Context SecurityContext sec,
        @FormParam("email") String email,
        @FormParam("phone") String phone) {

        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return noAccess.instance();
        
        String username = userTMP.getName();

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.email = email;
        contactDTO.phone = phone;

        if (clientContactController.createContact(username, contactDTO))
            return this.getUserHimselflHTML(sec);
        
        return notAllowed.data("error", "You can't create contact");
    }

    // Deletes the contact and returns User's page
    @POST
    @Path("/delete/contact")
    public TemplateInstance deleteContactHTML(@Context SecurityContext sec) {

        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return noAccess.instance();
        
        String username = userTMP.getName();

        if (clientContactController.deleteContact(username))
            return this.getUserHimselflHTML(sec);
        
        return notAllowed.data("error", "You don't have a contact");
    }
}
