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


// http://localhost:8080/genrepage/{id}
@ApplicationScoped
@Path("/genrepage/{id}")



public class GenrePage {

    @Inject
    Template genre;

    
    @Inject
    VinylBoundary vinylController = new VinylController();

    @Inject
    ClientBoundry clientController = new ClientController();



 /*
    @GET
    public TemplateInstance getVinylHTML(@PathParam("id") Long id){
     VinylDTO vinylDTO = vinylController.getVinyls();
       ClientDTO clientDTO = clientController.getClient(id);
     if (vinylDTO != null)  
      return vinyl.data("vinyl",vinylDTO).data("user",clientDTO);
      
      return null;
    }
 */

}
