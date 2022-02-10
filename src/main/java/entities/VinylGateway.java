package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.VinylDTO;
import entities.basic.Vinyl;

@Model
@Dependent
public interface VinylGateway extends Serializable {

    // vinyl
    public Collection<Vinyl> getVinyls();
    public void createVinyl(Vinyl vinyl);

    // vinyl/{id}
    public Vinyl getVinyl(Long id);
    public boolean updateVinyl(Long id, VinylDTO vinylDTO);
    public boolean deleteVinyl(Long id);

    // vinyl/{id}/reccomendations
    public Collection<Vinyl> getVinylReccomendations(Long id);

    // vinyl/{genre}/search
    public Collection<Vinyl> getVinylGenre(String genre); 

}
