package rest.html;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import control.DTO.ClientDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;

// http://localhost:8080/genrepage/{id}
@ApplicationScoped
@Path("/template/genre")

public class GenrePage {

    @Inject
    Template genre;

    @Inject
    Template genres;

    @Inject
    VinylBoundary vinylController = new VinylController();

    @Inject
    ClientBoundry clientController = new ClientController();

    @GET
    public TemplateInstance getGenresHTML(){      
        return this.genres.instance();
    }

    @GET
    @Path("/{genre}")
    public TemplateInstance getGenreHTML(@PathParam("genre") String genre){  
        genre = genre.toUpperCase();    
        return this.genre.data("vinyls", vinylController.getVinylGenre(genre),
            "genre", genre);
    }
}
