package rest.html;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import control.DTO.ClientDTO;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.client.ClientIdBoundary;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import control.vinyl.VinylIdBoundary;
import control.vinyl.VinylReccomendationsBoundary;
import io.quarkus.qute.*;

// http://localhost:8080/vinylpage/{id}
@ApplicationScoped
@Path("/template/vinyl")

public class VinylPage {

    // Vinyl HTMLs
    @Inject
    Template vinyl;

    @Inject
    Template createVinyl;

    @Inject
    Template user;

    // Error HTMLs
    @Inject
    Template error;

    @Inject
    Template noAccess;

    @Inject
    Template notAllowed;

    // Controllers
    @Inject
    VinylIdBoundary vinylIdController = new VinylController();

    @Inject
    VinylReccomendationsBoundary vinylReccomendationsController = new VinylController();

    @Inject
    VinylBoundary vinylController = new VinylController();

    @Inject
    ClientIdBoundary clientIdController = new ClientController();

    @Inject
    ClientBoundry clientController = new ClientController();

    // Gets vinyl HTML page
    @GET
    @Path("/{id}")
    public TemplateInstance getVinylHTML(@PathParam("id") Long id) {
        VinylDTO vinylDTO = vinylIdController.getVinyl(id);
        Collection<VinylDTO> recommendations = vinylReccomendationsController.getVinylReccomedations(id);

        if (vinylDTO != null) {
            ClientDTO clientDTO = clientIdController.getClient(vinylDTO.creator_id);
            return vinyl.data("vinyl", vinylDTO).data("user", clientDTO).data("recommendation", recommendations);
        }

        return error.instance();
    }

    // Gets the create vinyl form
    @GET
    @RolesAllowed("Client")
    @Path("/create")
    public TemplateInstance getCreateVinylHTML(@Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if (user == null)
            return this.noAccess.instance();

        return createVinyl.instance();
    }

    // Creates the new vinyl
    @POST
    @RolesAllowed("Client")
    @Path("/create")
    public TemplateInstance createVinylHTML(@Context SecurityContext sec,
        @FormParam("title") String title,
        @FormParam("artist") String artist,
        @FormParam("description") String description,
        @FormParam("genre") String genre,
        @FormParam("price") Long price) {

        Principal userTMP = sec.getUserPrincipal();
        if (userTMP == null)
            return this.noAccess.instance();
        String username = userTMP.getName();

        CreateVinylDTO createVinylDTO = new CreateVinylDTO();
        createVinylDTO.title = title;
        createVinylDTO.artist = artist;
        createVinylDTO.description = description;
        createVinylDTO.price = price;
        createVinylDTO.genre = genre;

        if (vinylController.createVinyl(username, createVinylDTO))
            return user.data("user", 
                clientController.getClientByUsername(username));

        return notAllowed.instance();
    }

}
