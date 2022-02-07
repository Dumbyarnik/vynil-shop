package control;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import entities.ClientGateway;
import entities.security.UserLogin;
import gateway.ClientRepository;
import io.quarkus.runtime.StartupEvent;



@Singleton
public class CreateSecInitials {
    @Inject
    ClientGateway clientGateway = new ClientRepository();

    @Transactional
    public void loadUsers(@Observes StartupEvent event){
        UserLogin.deleteAll();
        UserLogin.add("user", "password", "Client");
    }
}
