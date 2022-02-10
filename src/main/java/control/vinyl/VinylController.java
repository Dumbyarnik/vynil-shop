package control.vinyl;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import entities.ClientGateway;
import entities.VinylGateway;
import entities.basic.Client;
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
        return entityConverter.vinylCollectionToVinylDTCollection(
            vinylRepository.getVinyls()
        );
    }

    @Override
    public boolean createVinyl(String username, CreateVinylDTO createVinylDTO) {
        // Converting VinylDTO to Vinyl
        Client client = clientRepository.getClientByName(username);
        if (client == null)
            return false;

        Vinyl vinyl = entityConverter.vinylDTOToVinyl(createVinylDTO, client);
        vinylRepository.createVinyl(vinyl);

        return true;
    }

    @Override
    public VinylDTO getVinyl(Long id) {
        Vinyl vinyl = vinylRepository.getVinyl(id);
        if (vinyl == null)
            return null;
        return entityConverter.vinylToVinylDTO(vinyl);
    }

    @Override
    public boolean updateVinyl(Long id, VinylDTO vinylDTO) {
        return vinylRepository.updateVinyl(id, vinylDTO);
    }

    @Override
    public boolean deleteVinyl(Long id) {
        return vinylRepository.deleteVinyl(id);
    }

    @Override
    public Collection<VinylDTO> getVinylReccomedations(Long id) {
        Collection<VinylDTO> reccomedations = 
            entityConverter.vinylCollectionToVinylDTCollection(
                vinylRepository.getVinylReccomendations(id));

        return reccomedations;

    }

    @Override
    public Collection<VinylDTO> getVinylGenre(String genre) {
        return entityConverter.vinylCollectionToVinylDTCollection(
            vinylRepository.getVinylGenre(genre)
        );
    }
    
}
