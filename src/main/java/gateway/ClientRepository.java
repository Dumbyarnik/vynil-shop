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

@Model
@Dependent
public class ClientRepository implements ClientGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    // TODO: unserstand what to give back
    @Override
    @Transactional
    public boolean createClient(String username, String password) {
        Client new_client = new Client();
        new_client.setUsername(username);
        new_client.setPassword(password);
        
        em.persist(new_client);
        
        return true; 
    }

    @Override
    public Client getClient(String username) {
        Client client = em.createQuery("Select c FROM Client c where " + 
            "c.username LIKE :username",
            Client.class)
            .setParameter("username", username)
            .getSingleResult();
        
        return client;
    }
    
}
