/*
    @author: Dennis Dreier
*/
package control.client.favourites;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.VinylDTO;
import entities.FavouritesGateway;
import gateway.FavouritesRepository;

@Model
@Dependent
public class FavouritesController implements FavouritesBoundary {

    @Inject
    FavouritesGateway favouritesRepository = new FavouritesRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public Collection<VinylDTO> getFavourites(String username) {
        return entityConverter
            .vinylCollectionToVinylDTCollection(favouritesRepository
            .getFavourites(username));
    }

    @Override
    public boolean createFavourite(String username, Long vinyl_id) {
        return favouritesRepository.createFavourite(username, vinyl_id);
    }

    @Override
    public boolean deleteFavourite(String username, Long vinyl_id) {
        return favouritesRepository.deleteFavourite(username, vinyl_id);
    }
    
}
