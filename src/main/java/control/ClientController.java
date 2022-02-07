package control;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.DAO.ClientDAO;
import entities.ClientGateway;
import entities.basic.Client;
import gateway.ClientRepository;

@Model
@Dependent
public class ClientController implements ClientBoundry {

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public void createClient(String username, String password) {
        clientRepository.createClient(username, password);
    }

    @Override
    public ClientDAO getClient(String username) {
        ClientDAO clientDAO = entityConverter
            .clientToClientDAO(clientRepository.getClient(username));
        return clientDAO;
    }
    
}
