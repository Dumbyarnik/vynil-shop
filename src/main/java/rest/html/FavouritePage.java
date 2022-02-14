package rest.html;

import java.security.Principal;
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
import javax.ws.rs.core.SecurityContext;

import control.DTO.ClientDTO;
import control.DTO.ReviewDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.client.favourites.FavouritesBoundary;
import control.client.favourites.FavouritesController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import control.vinyl.VinylGenreBoundary;
import control.vinyl.VinylIdBoundary;
import control.vinyl.VinylReccomendationsBoundary;
import entities.basic.Vinyl;
import gateway.DatabaseService;
import io.quarkus.qute.*;

// http://localhost:8080/favourite/{id}
@ApplicationScoped
@Path("/template/favourite")

public class FavouritePage {

    @Inject
    Template favourites;

    @Inject
    Template vinyl;

    @Inject
    Template error;

    @Inject
    Template notAllowed;

    @Inject 
    Template noAccess;

    @Inject
    FavouritesBoundary favouriteContoller = new FavouritesController();

    @Inject
    ClientBoundry clientController = new ClientController();

    @Inject
    VinylIdBoundary vinylIdContoller = new VinylController();

    @Inject
    VinylGenreBoundary vinylGenreContoller = new VinylController();

    @Inject
    VinylReccomendationsBoundary vinylReccomendationsContoller = new VinylController();

    @Inject
    DatabaseService databaseService = new DatabaseService();

    @GET
    @RolesAllowed("Client")
    public TemplateInstance getFavouritesofUser(@Context SecurityContext sec) {
        Principal user = sec.getUserPrincipal();
        if (user == null)
            return this.noAccess.instance();

        String username = user.getName();
        Collection<VinylDTO> favouriteDTO = favouriteContoller.getFavourites(username);

        if (favouriteDTO != null)
            return this.favourites.data("favourites", favouriteDTO);

        return this.notAllowed.instance();
    }

    @GET
    public TemplateInstance getGenresHTML() {
        ClientDTO clientDTO = clientController.getClient(1l);
        if (clientDTO != null)
            return this.favourites.data("user", clientDTO);

        return this.error.instance();
    }

    @POST
    @Path("{id}/edit")
    @RolesAllowed("Client")
    public TemplateInstance postFavourite(@Context SecurityContext sec, 
        @PathParam("id") Long id) {

        Principal userTMP = sec.getUserPrincipal();
        String username = userTMP.getName();

        VinylDTO vinylDTO = vinylIdContoller.getVinyl(id);
        ClientDTO clientDTO = clientController.getClientByUsername(username);
        Collection<VinylDTO> recommendations = vinylReccomendationsContoller.getVinylReccomedations(id);

        if (favouriteContoller.createFavourite(username, id)) {
            return vinyl.data("vinyl", vinylDTO).data("user", clientDTO)
                    .data("recommendation", recommendations);
        }

        return this.notAllowed.instance();
    }

    @POST
    @Path("{id}/delete")
    @RolesAllowed("Client")
    public TemplateInstance deleteFavourite(@Context SecurityContext sec, 
        @PathParam("id") Long id) {

        Principal user = sec.getUserPrincipal();
        String username = user.getName();

        if (favouriteContoller.deleteFavourite(username, id)) {
            return getFavouritesofUser(sec);
        }

        return this.notAllowed.instance();
    }

}
