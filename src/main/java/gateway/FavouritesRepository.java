package gateway;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.FavouritesGateway;
import entities.basic.Client;
import entities.basic.Vinyl;

@Model
@Dependent
public class FavouritesRepository implements FavouritesGateway {

    @Inject
    protected EntityManager em;

    @Inject
    private DatabaseService databaseService;

    @Override
    public Collection<Vinyl> getFavourites(String username) {
        return databaseService.getClientByName(username).getFavourites();
    }

    @Override
    @Transactional
    public boolean createFavourite(String username, Long vinyl_id) {
        Client client = databaseService.getClientByName(username);
        Vinyl vinyl = em.find(Vinyl.class, vinyl_id);

        if (client == null || vinyl == null)
            return false;
        
        // if client has this favourite
        for (Vinyl tmp : client.getFavourites())
            if (tmp.getId().equals(vinyl_id))
                return false;
        
        // if client put his vinyl into favourites
        for (Vinyl tmp : client.getVinyls())
            if (tmp.getId().equals(vinyl_id))
                return false;
        
        client.getFavourites().add(vinyl);

        em.merge(client);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteFavourite(String username, Long vinyl_id) {
        Client client = databaseService.getClientByName(username);
        Vinyl vinyl = em.find(Vinyl.class, vinyl_id);

        if (client == null || vinyl == null)
            return false;

        if (!client.getFavourites().contains(vinyl))
            return false;

        client.getFavourites().remove(vinyl);

        em.merge(client);

        return true;
    }
    
}
