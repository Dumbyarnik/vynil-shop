/*
    @author: Daniil Vorobyev
*/
package gateway;

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
import io.quarkus.elytron.security.common.BcryptUtil;

@Model
@Dependent
public class ClientRepository implements ClientGateway {

    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    @Inject
    private DatabaseService databaseService;

    @Override
    public Collection<Client> getClients() {
        return em.createQuery("SELECT c FROM Client c",
            Client.class).getResultList();
    }

    @Override
    @Transactional
    public boolean createClient(String username, String password) {
        // persisting UserLogin & client
        try{
            // creating client
            Client client = new Client();
            client.setUsername(username);
            // creating UserLogin
            UserLogin userLogin = this.createSecurityIdentityClient(username, password);

            em.persist(client);
            em.persist(userLogin);
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
    public boolean createContact(String username, String email, String phone) {
        // getting client
        Client client = databaseService.getClientByName(username);

        if (client.getContact() != null)
            return this.updateContact(username, email, phone);

        // creating contact
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        
        if (client != null){
            contact.setClient(client);
            client.setContact(contact);
            em.merge(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateContact(String username, String email, String phone) {
        // getting client
        Client client = databaseService.getClientByName(username);
        
        if (client.getContact() == null)
            return false;

        if (client != null){
            client.getContact().setEmail(email);
            client.getContact().setPhone(phone);
            em.merge(client);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteContact(String username) {
        Client client = databaseService.getClientByName(username);

        if (client.getContact() == null)
            return false;

        if (client != null){
            client.getContact().deleteClient();
            em.remove(client.getContact());

            client.deleteContact();
            em.merge(client);
            
            return true;
        }
        return false;
    }

    // returns encrypted password
    private UserLogin createSecurityIdentityClient(String username, String password){
        UserLogin user = new UserLogin();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = "Client";

        return user;
    }

    // For frontend
    @Override
    public Client getClientByUsername(String username) {
       return this.databaseService.getClientByName(username);
    }
}
