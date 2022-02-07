package control.client;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import entities.ClientGateway;
import entities.basic.Client;
import entities.basic.Contact;
import entities.security.UserLogin;
import gateway.ClientRepository;

@Model
@Dependent
public class ClientController implements ClientBoundry {

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public boolean createClient(CreateClientDAO createClientDAO) {
        Client client = new Client();
        client.setUsername(createClientDAO.username);

        UserLogin userLogin = new UserLogin();
        userLogin.username = createClientDAO.username;
        userLogin.password = createClientDAO.password;
        
        
        if (clientRepository.createClient(client, userLogin))
            return true;
        return false;
    }

    @Override
    public ClientDAO getClient(String username) {
        ClientDAO clientDAO = entityConverter
            .clientToClientDAO(clientRepository.getClient(username));
        return clientDAO;
    }

    @Override
    public Collection<ClientDAO> getClients() {
        Collection<ClientDAO> clients = new ArrayList<ClientDAO>();
        for (Client client : clientRepository.getClients()){
            ClientDAO clientDAO = entityConverter
                .clientToClientDAO(client);
            clients.add(clientDAO);
        }
        return clients;
    }

    @Override
    public boolean deleteClient(Long id) {
        return clientRepository.deleteClient(id);
    }

    @Override
    public boolean createContact(String username, ContactDAO contactDAO) {
        Contact contact = new Contact();
        contact.setEmail(contactDAO.email);
        contact.setPhone(contactDAO.phone);

        return clientRepository.createContact(username, contact);

    }

    @Override
    public boolean updateContact(String username, ContactDAO contactDAO) {
        Contact contact = new Contact();
        contact.setEmail(contactDAO.email);
        contact.setPhone(contactDAO.phone);

        return clientRepository.createContact(username, contact);
    }

    @Override
    public boolean deleteContact(String username) {
        if (clientRepository.deleteContact(username))
            return true;
        return false;
    }
    
}
