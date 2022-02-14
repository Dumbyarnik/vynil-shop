package control.vinyl;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.VinylDTO;

@Model
@Dependent
public interface VinylIdBoundary {

    // vinyl/{id}
    public VinylDTO getVinyl(Long id);
    public boolean updateVinyl(String username, Long id, VinylDTO vinylDTO);
    public boolean deleteVinyl(String username, Long id);
    
}
