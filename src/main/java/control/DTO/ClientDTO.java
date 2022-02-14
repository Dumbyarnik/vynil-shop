/*
    @author: Daniil Vorobyev
*/
package control.DTO;

import java.util.Collection;

public class ClientDTO {

    public Long id;
    public String username;
    public String description;
    public ContactDTO contact;
    public Collection<ReviewDTO> reviews;
    public Collection<VinylDTO> vinyls;
    
}
