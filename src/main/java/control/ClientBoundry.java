package control;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import entities.basic.Client;

@Model
@Dependent
public interface ClientBoundry {
    
    public boolean createClient(ClientDAO clientDAO);
    public ClientDAO getClient(String username);
    
}
