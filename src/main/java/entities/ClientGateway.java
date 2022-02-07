package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.security.UserLogin;

@Model
@Dependent
public interface ClientGateway extends Serializable{

    public boolean createClient(Client client, UserLogin userLogin);
    public Collection<Client> getClients();
    public Client getClient(String username);
    public boolean deleteClient(Long id);
    public boolean createContact(String username, Contact contact);
    public boolean updateContact(String username,  Contact contact);
    public boolean deleteContact(String username);
    
}
