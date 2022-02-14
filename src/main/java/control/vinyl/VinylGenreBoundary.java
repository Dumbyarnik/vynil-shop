/*
    @author: Daniil Vorobyev
*/
package control.vinyl;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DTO.VinylDTO;

@Model
@Dependent
public interface VinylGenreBoundary {
    // vinyl/{genre}/search
    public Collection<VinylDTO> getVinylGenre(String genre);
    
}
