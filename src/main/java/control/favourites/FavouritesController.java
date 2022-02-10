package control.favourites;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import entities.ClientGateway;
import entities.FavouritesGateway;
import entities.VinylGateway;
import entities.basic.Client;
import entities.basic.Genre;
import entities.basic.Vinyl;
import gateway.ClientRepository;
import gateway.FavouritesRepository;
import gateway.VinylRepository;

@Model
@Dependent
public class FavouritesController implements FavouritesBoundary {

    @Inject
    VinylGateway vinylRepository = new VinylRepository();

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    @Inject
    FavouritesGateway favouritesRepository = new FavouritesRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public Collection<VinylDTO> getFavourites(String username) {
        Client client = clientRepository.getClientByName(username);

        Collection<VinylDTO> favourites = new ArrayList<>();

        for (Vinyl vinyl : favouritesRepository.getFavourites(client)){
            VinylDTO vinylDTO = entityConverter.vinylToVinylDTO(vinyl);
            favourites.add(vinylDTO);
        }

        return favourites;
    }

    @Override
    public boolean createFavourite(String username, Long vinyl_id) {
        Client client = clientRepository.getClientByName(username);
        Vinyl vinyl = vinylRepository.getVinyl(vinyl_id);

        if (client == null || vinyl == null)
            return false;
        
        client.getFavourites().add(vinyl);

        favouritesRepository.createFavourite(client);

        return true;
    }

    @Override
    public boolean deleteFavourite(String username, Long vinyl_id) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
