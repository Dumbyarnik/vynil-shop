package gateway;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.basic.Client;
import entities.basic.Genre;

import entities.VinylGateway;
import entities.basic.Vinyl;

@Model
@Dependent
public class VinylRepository implements VinylGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    private DatabaseService databaseService;

    @Override
    public Collection<Vinyl> getVinyls() {
        Collection <Vinyl> vinyls = em.createQuery("SELECT v FROM Vinyl v",
            Vinyl.class).getResultList();
        
        return vinyls;
    }

    @Override
    @Transactional
    public boolean createVinyl(String username, String title, String artist,
    String description, Long price, Genre genre) {
        Client client = databaseService.getClientByName(username);

        if (client == null)
            return false;

        Vinyl vinyl = new Vinyl();
        vinyl.setTitle(title);
        vinyl.setArtist(artist);
        vinyl.setDescription(description);
        vinyl.setPrice(price);
        vinyl.setGenre(genre);
        vinyl.setClient(client);

        em.persist(vinyl);

        return true;
    }

    @Override
    public Vinyl getVinyl(Long id) {
        return em.find(Vinyl.class, id);
    }

    @Override
    @Transactional
    public boolean updateVinyl(Long id, String title, String artist,
        String description, Long price, Genre genre) {
        Vinyl vinyl = em.find(Vinyl.class, id);
        Client client = em.find(Client.class, vinyl.getClient().getId());

        if (vinyl == null)
            return false;

        // going through all the vinyls and find the one we
        // need to update
        for (Vinyl tmp : client.getVinyls())
            if (tmp.getId().equals(vinyl.getId())){
                tmp.setTitle(title);
                tmp.setArtist(artist);
                tmp.setDescription(description);
                tmp.setPrice(price);
                tmp.setGenre(genre);
                
                em.merge(client);        
            }

        return true;
    }

    @Override
    @Transactional
    public boolean deleteVinyl(Long id) {
        Vinyl vinyl = this.getVinyl(id);
        Client client = vinyl.getClient();

        if (vinyl == null || client == null)
            return false;
        
        // going through all the vinyls and find the one we
        // need to delete
        for (Vinyl tmp : client.getVinyls())
            if (tmp.getId().equals(vinyl.getId())){
                tmp.deleteClient();
                client.getVinyls().remove(tmp);

                em.merge(client);
                em.remove(vinyl);
                return true;        
            }
        
        return false;
        
    }

    @Override
    public Collection<Vinyl> getVinylReccomendations(Long id) {
        Vinyl vinyl = this.getVinyl(id);
        
        Collection<Vinyl> tmp_list = em.createQuery("Select v FROM Vinyl v where " + 
            "v.genre LIKE :genre",
            Vinyl.class)
            .setParameter("genre", (vinyl.getGenre()))
            .getResultList();

        if (tmp_list.size() == 0)
            return null;

        // retrieve first 3 results
        Collection<Vinyl> reccomendations = new ArrayList<Vinyl>();
        int i = 0;
        for (Vinyl tmp : tmp_list){
            if (i == 3)
                break;
            if (tmp != vinyl){
                reccomendations.add(tmp);
            }
        }

        return reccomendations;
    }

    @Override
    public Collection<Vinyl> getVinylGenre(String genre) {
        return em.createQuery("Select v FROM Vinyl v where " + 
            "v.genre LIKE :genre",
            Vinyl.class)
            .setParameter("genre", Genre.valueOf(genre))
            .getResultList();
    }
    
}
