package control;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

@Model
@Dependent
public class ClientController implements ClientBoundry {

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    @Override
    public void createClient(String username, String password) {
        clientRepository.createClient(username, password);
    }

    @Override
    public Client getClient(String username) {
        return clientRepository.getClient(username);
    }
    
}
