/*
    @author: Daniil Vorobyev
*/
package control.client;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.CreateClientDTO;

@Model
@Dependent
public interface ClientBoundry {
    // client
    public Collection<ClientDTO> getClients();
    public boolean createClient(CreateClientDTO createClientDTO);

    // additional for frontend
    public ClientDTO getClientByUsername(String username);
    
}
