/*
    @author: Daniil Vorobyev
*/
package rest.html;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylController;
import control.vinyl.VinylGenreBoundary;
import io.quarkus.qute.*;

// http://localhost:8080/genrepage/{id}
@ApplicationScoped
@Path("/template/genre")

public class GenrePage {

    // Genre HTMLs
    @Inject
    Template genre;

    @Inject
    Template genres;

    // Controllers
    @Inject
    VinylGenreBoundary vinylGenreController = new VinylController();

    @Inject
    ClientBoundry clientController = new ClientController();

    // Gets the all genres page - main page
    @GET
    public TemplateInstance getGenresHTML() {
        return this.genres.instance();
    }

    // Gets page of one genre
    @GET
    @Path("/{genre}")
    public TemplateInstance getGenreHTML(@PathParam("genre") String genre) {
        genre = genre.toUpperCase();
        
        return this.genre.data("vinyls", 
            vinylGenreController.getVinylGenre(genre),
            "genre", genre);
    }
}
