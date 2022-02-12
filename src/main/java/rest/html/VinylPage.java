package rest.html;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import control.DTO.ClientDTO;
import control.DTO.VinylDTO;
import control.client.ClientBoundry;
import control.client.ClientController;
import control.vinyl.VinylBoundary;
import control.vinyl.VinylController;
import io.quarkus.qute.*;

// http://localhost:8080/vinylpage/{id}
@ApplicationScoped
@Path("/vinylpage/{id}")



public class VinylPage {

    @Inject
    Template vinyl;

    @Inject
    Template error;

    @Inject
    VinylBoundary vinylController = new VinylController();

    @Inject
    ClientBoundry clientController = new ClientController();




    @GET
    public TemplateInstance getVinylHTML(@PathParam("id") Long id){
     VinylDTO vinylDTO = vinylController.getVinyl(id);
     ClientDTO clientDTO = clientController.getClient(id);
     if (vinylDTO != null)  
      return vinyl.data("vinyl",vinylDTO).data("user",clientDTO);
      
      return error.data(null);
    }
 

}
