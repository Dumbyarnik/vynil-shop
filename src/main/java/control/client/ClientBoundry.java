package control.client;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import entities.basic.Client;

@Model
@Dependent
public interface ClientBoundry {
    // client
    public Collection<ClientDTO> getClients();
    public boolean createClient(CreateClientDTO createClientDTO);

    // client/{id}
    public ClientDTO getClient(Long id);
    public boolean deleteClient(Long id);

    // client/contact
    public boolean createContact(String username, ContactDTO contactDTO);
    public boolean updateContact(String username,  ContactDTO contactDTO);
    public boolean deleteContact(String username);
    
}
