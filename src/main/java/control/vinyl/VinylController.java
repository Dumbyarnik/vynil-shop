package control.vinyl;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import control.EntityConverter;
import control.DTO.CreateVinylDTO;
import control.DTO.VinylDTO;
import entities.VinylGateway;
import entities.basic.Genre;
import entities.basic.Vinyl;
import gateway.VinylRepository;

@Model
@Dependent
public class VinylController implements VinylBoundary {

    @Inject
    VinylGateway vinylRepository = new VinylRepository();

    EntityConverter entityConverter = new EntityConverter();

    @Override
    public Collection<VinylDTO> getVinyls() {
        return entityConverter.vinylCollectionToVinylDTCollection(
            vinylRepository.getVinyls()
        );
    }

    @Override
    public boolean createVinyl(String username, CreateVinylDTO createVinylDTO) {
        return vinylRepository.createVinyl(username, createVinylDTO.title,
            createVinylDTO.artist, createVinylDTO.description, 
            createVinylDTO.price, Genre.valueOf(createVinylDTO.genre));
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
        return vinylRepository.updateVinyl(id, vinylDTO.title,
            vinylDTO.artist, vinylDTO.description, 
            vinylDTO.price, Genre.valueOf(vinylDTO.genre));
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
