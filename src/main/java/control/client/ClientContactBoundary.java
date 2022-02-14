/*
    @author: Daniil Vorobyev
*/
package control.client;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ContactDTO;

@Model
@Dependent
public interface ClientContactBoundary {
    // client/contact
    public boolean createContact(String username, ContactDTO contactDTO);
    public boolean updateContact(String username,  ContactDTO contactDTO);
    public boolean deleteContact(String username);    
}
