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
    public Client getClient(String username);
    Collection<Client> getClients();
    
}
