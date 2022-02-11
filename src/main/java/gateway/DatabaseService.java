package gateway;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entities.basic.Client;

@Model
@Dependent
public class DatabaseService {

    @Inject
    protected EntityManager em;

    public Client getClientByName(String username) {
        Client client = em.createQuery("Select c FROM Client c where " + 
            "c.username LIKE :username",
            Client.class)
            .setParameter("username", username)
            .getSingleResult();
        
        return client;
    }
    
}
