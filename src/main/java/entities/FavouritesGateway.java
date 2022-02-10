package entities;

import java.io.Serializable;
import java.util.Collection;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Vinyl;
import entities.security.UserLogin;

@Model
@Dependent
public interface FavouritesGateway extends Serializable {

    // client/favourites
    public Collection<Vinyl> getFavourites(Client client);
    // client/favourites/{vinyl_id}
    public void createFavourite(Client client);
    public void deleteFavourite(Client client);
    
}
