package entities;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import entities.basic.Client;

@Model
@Dependent
public interface ClientGateway extends Serializable{

    public boolean createClient(String name, String password);
    public Client getClient(String username);
    
}
