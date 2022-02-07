package control;

import control.DAO.ClientDAO;
import entities.basic.Client;

public class EntityConverter {

    public ClientDAO clientToClientDAO(Client client){
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.username = client.getUsername();
        clientDAO.password = client.getPassword();

        return clientDAO;
    }
    
}
