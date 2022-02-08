package entities;

import java.io.Serializable;
import java.util.Collection;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Vinyl;
import entities.security.UserLogin;

@Model
@Dependent
public interface VinylGateway extends Serializable {

    // vinyl
    public Collection<Vinyl> getVinyls();
    public void createVinyl(Vinyl vinyl);

    // vinyl/{id}
    public Vinyl getVinyl(Long id);
    public void updateVinyl(Long id, VinylDTO vinylDTO);
    public boolean deleteVinyl(Long id);
}
