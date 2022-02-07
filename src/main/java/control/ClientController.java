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
    public boolean createClient(ClientDAO clientDAO) {
        Client client = new Client();
        client.setUsername(clientDAO.username);
        client.setPassword(clientDAO.password);
        if (clientRepository.createClient(client))
            return true;
        return false;
    }

    @Override
    public ClientDAO getClient(String username) {
        ClientDAO clientDAO = entityConverter
            .clientToClientDAO(clientRepository.getClient(username));
        return clientDAO;
    }
    
}
