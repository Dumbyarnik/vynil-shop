package rest.html;

import java.util.ArrayList;
import java.util.Collection;

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
@Path("/template/vinyl")

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
    @Path("/{id}")
    public TemplateInstance getVinylHTML(@PathParam("id") Long id) {
        VinylDTO vinylDTO = vinylController.getVinyl(id);
        Collection<VinylDTO> recommendations = vinylController.getVinylReccomedations(id);
       ArrayList<VinylDTO> vinyls= new ArrayList<>();

        for (VinylDTO vinylDTO2 : recommendations) {
          vinyls.add(vinylDTO2);
        }
    
        if (vinylDTO != null){
            ClientDTO clientDTO = clientController.getClient(vinylDTO.creator_id);
            return vinyl.data("vinyl", vinylDTO).data("user", clientDTO).data("recommendation",vinyls);
        }
            
        return error.instance();
    }

}
