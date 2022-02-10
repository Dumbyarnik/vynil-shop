package control;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import control.client.ClientBoundry;
import control.client.ClientController;
import entities.security.UserLogin;
import io.quarkus.runtime.StartupEvent;

@Singleton
public class CreateSecInitials {
    @Inject
    ClientBoundry clientController = new ClientController();

    @Transactional
    public void loadUsers(@Observes StartupEvent event){
        UserLogin.deleteAll();

        UserLogin.add("ringo", "ringo", "Client");
        UserLogin.add("bingo", "bingo", "Client");
        UserLogin.add("shmingo", "shmingo", "Client");
    }
}
