package gateway;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.ClientGateway;
import entities.basic.Client;
import entities.basic.Contact;
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
    
}
