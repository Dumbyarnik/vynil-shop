package control.vinyl;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;

@Model
@Dependent
public interface VinylReccomendationsBoundary {

    // vinyl/{id}/reccomendations
    public Collection<VinylDTO> getVinylReccomedations(Long id);
    
}
