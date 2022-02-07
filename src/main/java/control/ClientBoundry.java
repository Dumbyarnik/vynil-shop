package control;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import entities.basic.Client;

@Model
@Dependent
public interface ClientBoundry {
    
    public void createClient(String username, String password);
    public Client getClient(String username);
    
}
