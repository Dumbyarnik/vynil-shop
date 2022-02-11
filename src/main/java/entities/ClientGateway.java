package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
    
import entities.basic.Client;

@Model
@Dependent
public interface ClientGateway extends Serializable{

    // client
    public Collection<Client> getClients();
    public boolean createClient(String username, String password);

    // client/{id}
    public Client getClient(Long id);
    public boolean deleteClient(Long id);

    // client/contact
    public boolean createContact(String username, String email, String phone);
    public boolean updateContact(String username,  String email, String phone);
    public boolean deleteContact(String username);    
}
