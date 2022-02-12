package control.DTO;

import java.io.Serializable;
import java.util.Collection;

public class ClientDTO {

    public Long id;
    public String username;
    public String description;
    public ContactDTO contact;
    public Collection<ReviewDTO> reviews;

    public ClientDTO(){}
    
}
