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
        if (clientRepository.createClient(createClientDTO.username,
            createClientDTO.password))
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
        return clientRepository.createContact(username, 
            contactDTO.email, contactDTO.phone);

    }

    @Override
    public boolean updateContact(String username, ContactDTO contactDTO) {
        return clientRepository.createContact(username, 
            contactDTO.email, contactDTO.phone);
    }

    @Override
    public boolean deleteContact(String username) {
        if (clientRepository.deleteContact(username))
            return true;
        return false;
    }
    
}
