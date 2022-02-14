/*
    @author: Dennis Dreier
*/
package entities;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import entities.basic.Vinyl;

@Model
@Dependent
public interface FavouritesGateway {
    // client/favourites
    public Collection<Vinyl> getFavourites(String username); 
    // client/favourites/{vinyl_id}
    public boolean createFavourite(String username, Long vinyl_id); 
    public boolean deleteFavourite(String username, Long vinyl_id);
}
