package control;

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import control.DAO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Vinyl;

public class EntityConverter {

    public ClientDAO clientToClientDAO(Client client){
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.username = client.getUsername();

        if (client.getContact() != null){
            clientDAO.contact = this.contactToContactDAO(client.getContact());
        }

        return clientDAO;
    }

    public VinylDTO vinylToVinylDTO(Vinyl vinyl){
        VinylDTO vinylDTO = new VinylDTO();
        vinylDTO.title = vinyl.getTitle();
        vinylDTO.artist = vinyl.getArtist();
        vinylDTO.description = vinyl.getDescription();
        vinylDTO.price = vinyl.getPrice();
        vinylDTO.genre = vinyl.getGenre().name();

        return vinylDTO;
    }

    private ContactDAO contactToContactDAO(Contact contact){
        ContactDAO contactDAO = new ContactDAO();
        contactDAO.email = contact.getEmail();
        contactDAO.phone = contact.getPhone();

        return contactDAO;
    }
    
}
