/*
    @author: Daniil Vorobyev
*/
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
}
