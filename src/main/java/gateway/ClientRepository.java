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

@Model
@Dependent
public class ClientRepository implements ClientGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Override
    public Collection<Client> getClients() {
        Collection <Client> clients = em.createQuery("SELECT c FROM Client c",
            Client.class).getResultList();
        
        return clients;
    }

    @Override
    @Transactional
    public boolean createClient(Client client, UserLogin userLogin) {
        // persisting UserLogin & client
        try{
            UserLogin.add(userLogin.username, userLogin.password, "Client");
            em.persist(client);
        } catch (Exception e){
            return false;
        }
        
        return true; 
    }

    @Override
    public Client getClient(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    @Transactional
    public boolean deleteClient(Long id) {
        Client client = em.find(Client.class, id);
        if (client != null){
            em.remove(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean createContact(String username, Contact contact) {
        Client client = this.getClientByName(username);
        
        if (client != null){
            client.setContact(contact);
            em.merge(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateContact(String username, Contact contact) {
        Client client = this.getClientByName(username);

        if (client != null){
            client.setContact(contact);
            em.merge(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteContact(String username) {
        Client client = this.getClientByName(username);

        if (client != null){
            client.deleteContact();
            em.merge(client);
            return true;
        }
        return false;
    }
    
    @Override
    public Client getClientByName(String username) {
        Client client = em.createQuery("Select c FROM Client c where " + 
            "c.username LIKE :username",
            Client.class)
            .setParameter("username", username)
            .getSingleResult();
        
        return client;
    }
}
