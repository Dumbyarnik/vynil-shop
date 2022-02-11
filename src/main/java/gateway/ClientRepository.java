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

    private DatabaseService databaseService;

    @Override
    public Collection<Client> getClients() {
        Collection <Client> clients = em.createQuery("SELECT c FROM Client c",
            Client.class).getResultList();
        
        return clients;
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
        // creating contact
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setClient(client);
        
        if (client != null){
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
        // creating contact
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setClient(client);

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
        Client client = databaseService.getClientByName(username);

        if (client != null){
            client.deleteContact();
            em.merge(client);
            return true;
        }
        return false;
    }

    private UserLogin createSecurityIdentityClient(String username, String password){
        UserLogin user = new UserLogin();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = "Client";

        return user;
    }
}
