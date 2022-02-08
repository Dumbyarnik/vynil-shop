package control.client;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
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
    public Collection<ClientDTO> getClients() {
        Collection<ClientDTO> clients = new ArrayList<ClientDTO>();
        // converting all the clients into clientDTO
        for (Client client : clientRepository.getClients()){
            ClientDTO clientDTO = entityConverter
                .clientToClientDTO(client);
            clients.add(clientDTO);
        }
        return clients;
    }

    @Override
    public boolean createClient(CreateClientDTO createClientDTO) {
        // Converting CreateClientDTO into Client
        // And creating UserLogin object
        Client client = new Client();
        client.setUsername(createClientDTO.username);

        UserLogin userLogin = new UserLogin();
        userLogin.username = createClientDTO.username;
        userLogin.password = createClientDTO.password;
        
        if (clientRepository.createClient(client, userLogin))
            return true;
        return false;
    }

    @Override
    public ClientDTO getClient(Long id) {
        ClientDTO clientDTO = entityConverter
            .clientToClientDTO(clientRepository.getClient(id));
        return clientDTO;
    }

    @Override
    public boolean deleteClient(Long id) {
        return clientRepository.deleteClient(id);
    }

    @Override
    public boolean createContact(String username, ContactDTO contactDTO) {
        // Converting contactDTO to contact
        Contact contact = new Contact();
        contact.setEmail(contactDTO.email);
        contact.setPhone(contactDTO.phone);

        return clientRepository.createContact(username, contact);

    }

    @Override
    public boolean updateContact(String username, ContactDTO contactDAO) {
        // Converting contactDTO to contact
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
