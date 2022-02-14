/*
    @author: Daniil Vorobyev
*/
package control.client;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;

@Model
@Dependent
public interface ClientIdBoundary {
    // client/{id}
    public ClientDTO getClient(Long id);
    public boolean deleteClient(Long id);
}
