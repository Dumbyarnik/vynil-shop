package control.client.favourites;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.VinylDTO;

@Model
@Dependent
public interface FavouritesBoundary extends Serializable {

    // client/favourites
    public Collection<VinylDTO> getFavourites(String username); 
    // client/favourites/{vinyl_id}
    public boolean createFavourite(String username, Long vinyl_id); 
    public boolean deleteFavourite(String username, Long vinyl_id);
    
}
