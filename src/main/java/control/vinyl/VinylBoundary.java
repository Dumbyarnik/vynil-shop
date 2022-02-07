package control.vinyl;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.CreateClientDAO;
import control.DAO.VinylDTO;

@Model
@Dependent
public interface VinylBoundary {
    public Collection<VinylDTO> getVinyls();
    public void createVinyl(String username, VinylDTO vinylDTO);
}
