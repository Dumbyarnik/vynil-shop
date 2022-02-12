package control.vinyl;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;

@Model
@Dependent
public interface VinylBoundary {
    // vinyl
    public Collection<VinylDTO> getVinyls();
    public boolean createVinyl(String username, CreateVinylDTO createVinylDTO);

    // vinyl/{id}
    public VinylDTO getVinyl(Long id);
    public boolean updateVinyl(String username, Long id, VinylDTO vinylDTO);
    public boolean deleteVinyl(String username, Long id);

    // vinyl/{id}/reccomendations
    public Collection<VinylDTO> getVinylReccomedations(Long id);

    // vinyl/{genre}/search
    public Collection<VinylDTO> getVinylGenre(String genre);
}
