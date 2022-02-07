package control;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import entities.basic.Client;

@Model
@Dependent
public interface ClientBoundry {
    
    public boolean createClient(CreateClientDAO createClientDAO);
    public ClientDAO getClient(String username);
    public Collection<ClientDAO> getClients();
    public boolean deleteClient(Long id);
    public boolean createContact(String username, ContactDAO contactDAO);
    public boolean updateContact(String username,  ContactDAO contactDAO);
    public boolean deleteContact(String username);
    
}
