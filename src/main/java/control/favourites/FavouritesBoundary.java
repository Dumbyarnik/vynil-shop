package control.favourites;

import java.io.Serializable;
import java.util.Collection;

import control.DTO.VinylDTO;

public interface FavouritesBoundary extends Serializable {

    // client/favourites
    public Collection<VinylDTO> getFavourites(String username); 
    // client/favourites/{vinyl_id}
    public boolean createFavourite(String username, Long vinyl_id); 
    public boolean deleteFavourite(String username, Long vinyl_id);
    
}
