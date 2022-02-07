package control;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import control.DAO.CreateClientDAO;
import control.client.ClientBoundry;
import control.client.ClientController;
import entities.ClientGateway;
import entities.security.UserLogin;
import gateway.ClientRepository;
import io.quarkus.runtime.StartupEvent;



@Singleton
public class CreateSecInitials {
    @Inject
    ClientBoundry clientController = new ClientController();

    @Transactional
    public void loadUsers(@Observes StartupEvent event){
        UserLogin.deleteAll();

        CreateClientDAO createClientDAO = new CreateClientDAO();
        createClientDAO.username = "user";
        createClientDAO.password = "password";
        clientController.createClient(createClientDAO);
    }
}
