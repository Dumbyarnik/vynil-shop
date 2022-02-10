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

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Override
    public Collection<Vinyl> getFavourites(Client client) {
        return client.getFavourites();
    }

    @Override
    @Transactional
    public void createFavourite(Client client) {
        em.merge(client);
        
    }

    @Override
    @Transactional
    public void deleteFavourite(Client client) {
        em.merge(client);
    }
    
}
