package control.vinyl;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateClientDTO;
import control.DTO.VinylDTO;

@Model
@Dependent
public interface VinylBoundary {
    // vinyl
    public Collection<VinylDTO> getVinyls();
    public void createVinyl(String username, VinylDTO vinylDTO);

    // vinyl/{id}
    public VinylDTO getVinyl(Long id);
    public void updateVinyl(Long id, VinylDTO vinylDTO);
    public boolean deleteVinyl(Long id);
}
