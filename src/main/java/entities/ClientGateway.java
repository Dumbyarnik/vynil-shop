package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import entities.basic.Client;

@Model
@Dependent
public interface ClientGateway extends Serializable{

    public boolean createClient(Client client);
    Collection<Client> getClients();
    public Client getClient(String username);
    public boolean deleteClient(Long id);
    
}
