package gateway;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import control.DAO.VinylDTO;
import entities.ClientGateway;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Genre;
import entities.security.UserLogin;
import io.quarkus.security.User;

import java.util.Collection;

import entities.VinylGateway;
import entities.basic.Vinyl;

@Model
@Dependent
public class VinylRepository implements VinylGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Override
    public Collection<Vinyl> getVinyls() {
        Collection <Vinyl> vinyls = em.createQuery("SELECT v FROM Vinyl v",
            Vinyl.class).getResultList();
        
        return vinyls;
    }

    @Override
    @Transactional
    public void createVinyl(Vinyl vinyl) {
        em.persist(vinyl);
    }

    @Override
    public Vinyl getVinyl(Long id) {
        return em.find(Vinyl.class, id);
    }

    @Override
    @Transactional
    public void updateVinyl(Long id, VinylDTO vinylDTO) {
        Vinyl vinyl = em.find(Vinyl.class, id);
        Client client = em.find(Client.class, vinyl.getClient().getId());

        // going through all the vinyls and find the one we
        // need to update
        for (Vinyl tmp : client.getVinyls())
            if (tmp.getId().equals(vinyl.getId())){
                tmp.setTitle(vinylDTO.title);
                tmp.setArtist(vinylDTO.artist);
                tmp.setDescription(vinylDTO.description);
                tmp.setPrice(vinylDTO.price);
                tmp.setGenre(Genre.valueOf(vinylDTO.genre.toUpperCase()));
                
                em.merge(client);        
            }
        }

    @Override
    @Transactional
    public boolean deleteVinyl(Long id) {
        Vinyl vinyl = this.getVinyl(id);
        Client client = vinyl.getClient();

        if (vinyl == null)
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
    
}
