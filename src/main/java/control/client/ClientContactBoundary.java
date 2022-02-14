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
public interface ClientContactBoundary {
    // client/contact
    public boolean createContact(String username, ContactDTO contactDTO);
    public boolean updateContact(String username,  ContactDTO contactDTO);
    public boolean deleteContact(String username);    
}
