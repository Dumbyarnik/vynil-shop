package control;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import entities.basic.Client;

@Model
@Dependent
public interface ClientBoundry {
    
    public boolean createClient(ClientDAO clientDAO);
    public ClientDAO getClient(String username);
    public Collection<ClientDAO> getClients();
    public boolean deleteCLient(Long id);
    
}
