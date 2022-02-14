/*
    @author: Daniil Vorobyev
*/
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
        // clients
        UserLogin.add("frank", "frank", "Client");
        UserLogin.add("peter", "peter", "Client");
        UserLogin.add("ringo", "ringo", "Client");
        // admin
        UserLogin.add("admin", "admin", "Admin");
    }
}
