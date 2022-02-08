package control.vinyl;

import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DAO.VinylDTO;
import entities.ClientGateway;
import entities.VinylGateway;
import entities.basic.Client;
import entities.basic.Genre;
import entities.basic.Vinyl;
import gateway.ClientRepository;
import gateway.VinylRepository;

@Model
@Dependent
public class VinylController implements VinylBoundary {

    @Inject
    VinylGateway vinylRepository = new VinylRepository();

    @Inject
    ClientGateway clientRepository = new ClientRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public Collection<VinylDTO> getVinyls() {
        Collection<VinylDTO> vinylDTOs = new ArrayList<>();

        for (Vinyl vinyl : vinylRepository.getVinyls()){
            VinylDTO vinylDTO = entityConverter.vinylToVinylDTO(vinyl);
            vinylDTOs.add(vinylDTO);
        }

        return vinylDTOs;
            
    }

    @Override
    public void createVinyl(String username, VinylDTO vinylDTO) {
        // Converting VinylDTO to Vinyl
        Client client = clientRepository.getClient(username);
        Vinyl vinyl = new Vinyl();
        vinyl.setTitle(vinylDTO.title);
        vinyl.setArtist(vinylDTO.artist);
        vinyl.setDescription(vinylDTO.description);
        vinyl.setPrice(vinylDTO.price);
        vinyl.setGenre(Genre.valueOf(vinylDTO.genre.toUpperCase()));
        vinyl.setClient(client);

        vinylRepository.createVinyl(vinyl);
        
    }

    @Override
    public void updateVinyl(Long id, VinylDTO vinylDTO) {
        vinylRepository.updateVinyl(id, vinylDTO);
    }

    @Override
    public VinylDTO getVinyl(Long id) {
        Vinyl vinyl = vinylRepository.getVinyl(id);
        return entityConverter.vinylToVinylDTO(vinyl);
    }

    @Override
    public boolean deleteVinyl(Long id) {
        return vinylRepository.deleteVinyl(id);
    }
    
}
