package control;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
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

    private ContactDTO contactToContactDAO(Contact contact){
        ContactDTO contactDAO = new ContactDTO();
        contactDAO.email = contact.getEmail();
        contactDAO.phone = contact.getPhone();

        return contactDAO;
    }
    
}
