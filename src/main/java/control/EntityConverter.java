package control;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Genre;
import entities.basic.Vinyl;

public class EntityConverter {

    public ClientDTO clientToClientDAO(Client client){
        ClientDTO clientDAO = new ClientDTO();
        clientDAO.username = client.getUsername();

        if (client.getContact() != null){
            clientDAO.contact = this.contactToContactDAO(client.getContact());
        }

        return clientDAO;
    }

    public VinylDTO vinylToVinylDTO(Vinyl vinyl){
        VinylDTO vinylDTO = new VinylDTO();
        vinylDTO.id = vinyl.getId();
        vinylDTO.title = vinyl.getTitle();
        vinylDTO.artist = vinyl.getArtist();
        vinylDTO.description = vinyl.getDescription();
        vinylDTO.price = vinyl.getPrice();
        vinylDTO.genre = vinyl.getGenre().name();

        return vinylDTO;
    }

    public Vinyl vinylDTOToVinyl(VinylDTO vinylDTO){
        // putting attributes of the vinyl
        Vinyl vinyl = new Vinyl();
        vinyl.setTitle(vinylDTO.title);
        vinyl.setArtist(vinylDTO.artist);
        vinyl.setDescription(vinylDTO.description);
        vinyl.setPrice(vinylDTO.price);
        vinyl.setGenre(Genre.valueOf(vinylDTO.genre.toUpperCase()));

        return vinyl;
    }

    public Vinyl vinylDTOToVinyl(VinylDTO vinylDTO, Client client){
        // putting attributes of the vinyl
        Vinyl vinyl = new Vinyl();
        vinyl.setTitle(vinylDTO.title);
        vinyl.setArtist(vinylDTO.artist);
        vinyl.setDescription(vinylDTO.description);
        vinyl.setPrice(vinylDTO.price);
        vinyl.setGenre(Genre.valueOf(vinylDTO.genre.toUpperCase()));
        // setting relationship with client
        vinyl.setClient(client);

        return vinyl;
    }

    private ContactDTO contactToContactDAO(Contact contact){
        ContactDTO contactDAO = new ContactDTO();
        contactDAO.email = contact.getEmail();
        contactDAO.phone = contact.getPhone();

        return contactDAO;
    }
    
}
