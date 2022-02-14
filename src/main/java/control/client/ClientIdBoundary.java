/*
    @author: Daniil Vorobyev
*/
package control.client;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;

@Model
@Dependent
public interface ClientIdBoundary {
    // client/{id}
    public ClientDTO getClient(Long id);
    public boolean deleteClient(Long id);
}
