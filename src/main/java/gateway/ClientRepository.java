package gateway;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import entities.basic.Client;

@Model
@Dependent
public class ClientRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Transactional
    public void createClient(String username) {
        Client new_client = new Client(username);
        em.persist(new_client);      
    }

    public Client getClient(String username) {
        Client client = em.createQuery("Select c FROM Client c where " + 
            "c.username LIKE :username",
            Client.class)
            .setParameter("username", username)
            .getSingleResult();
        
        return client;
    }
    
}
