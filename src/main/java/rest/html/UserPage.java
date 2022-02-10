package rest.html;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import control.DTO.ClientDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;

// http://localhost:8080/vinylpage/{id}
@ApplicationScoped
@Path("/userpage/{id}")


public class UserPage {

    @Inject
    Template user;

    

    @Inject
    ClientBoundry clientController = new ClientController();




    @GET
    public TemplateInstance getUserlHTML(@PathParam("id") Long id){
     ClientDTO clientDTO = clientController.getClient(id);
     if (clientDTO != null)  
      return user.data("user",clientDTO);
      
      return null;
    }
 

}
