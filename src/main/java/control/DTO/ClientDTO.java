package control.DTO;

import java.io.Serializable;
import java.util.Collection;

public class ClientDTO implements Serializable {

    public Long id;
    public String username;
    public String description;
    public ContactDTO contact;
    public Collection<ReviewDTO> reviews;

    public ClientDTO(){}
    
}
