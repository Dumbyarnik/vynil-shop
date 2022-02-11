package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import entities.basic.Genre;
import entities.basic.Vinyl;

@Model
@Dependent
public interface VinylGateway extends Serializable {

    // vinyl
    public Collection<Vinyl> getVinyls();
    public boolean createVinyl(String username, String title, String artist,
        String description, Long price, Genre genre);

    // vinyl/{id}
    public Vinyl getVinyl(Long id);
    public boolean updateVinyl(Long id, String title, String artist,
        String description, Long price, Genre genre);
    public boolean deleteVinyl(Long id);

    // vinyl/{id}/reccomendations
    public Collection<Vinyl> getVinylReccomendations(Long id);

    // vinyl/{genre}/search
    public Collection<Vinyl> getVinylGenre(String genre); 

}
