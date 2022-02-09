package control.DTO;

import java.io.Serializable;

public class ClientDTO implements Serializable {

    public Long id;
    public String username;
    public String description;
    public ContactDTO contact;

    public ClientDTO(){}
    
}
