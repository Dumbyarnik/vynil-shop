package control.DTO;

import java.io.Serializable;
import java.util.Collection;

import io.quarkus.resteasy.reactive.jackson.SecureField;

public class ClientDTO {

    @SecureField(rolesAllowed = "Admin")
    public Long id;
    public String username;
    public String description;
    public ContactDTO contact;
    public Collection<ReviewDTO> reviews;

    public ClientDTO(){}
    
}
