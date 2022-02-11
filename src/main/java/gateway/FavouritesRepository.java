package gateway;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import entities.FavouritesGateway;
import entities.basic.Client;
import entities.basic.Vinyl;

public class FavouritesRepository implements FavouritesGateway {

    @Inject
    protected EntityManager em;

    private DatabaseService databaseService;

    @Override
    public Collection<Vinyl> getFavourites(String username) {
        return databaseService.getClientByName(username).getFavourites();
    }

    @Override
    public boolean createFavourite(String username, Long vinyl_id) {
        Client client = databaseService.getClientByName(username);
        Vinyl vinyl = em.find(Vinyl.class, vinyl_id);

        if (client == null || vinyl == null)
            return false;
        
        client.getFavourites().add(vinyl);

        em.merge(client);

        return true;
    }

    @Override
    public boolean deleteFavourite(String username, Long vinyl_id) {
        Client client = databaseService.getClientByName(username);
        Vinyl vinyl = em.find(Vinyl.class, vinyl_id);

        if (client == null || vinyl == null)
            return false;

        client.getFavourites().remove(vinyl);

        em.merge(client);

        return true;
    }
    
}
