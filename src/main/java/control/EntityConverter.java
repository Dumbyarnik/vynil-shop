package control;

import control.DTO.ClientDTO;
import control.DTO.ContactDTO;
import control.DTO.CreateReviewDTO;
import control.DTO.CreateVinylDTO;
import control.DTO.ReviewDTO;
import control.DTO.VinylDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Genre;
import entities.basic.Review;
import entities.basic.Vinyl;

public class EntityConverter {

    public ClientDTO clientToClientDTO(Client client){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.id = client.getId();
        clientDTO.username = client.getUsername();
        
        if (client.getContact() != null){
            clientDTO.contact = this.contactToContactDTO(client.getContact());
        }

        return clientDTO;
    }

    private ContactDTO contactToContactDTO(Contact contact){
        ContactDTO clientDTO = new ContactDTO();
        clientDTO.email = contact.getEmail();
        clientDTO.phone = contact.getPhone();

        return clientDTO;
    }
    
    public VinylDTO vinylToVinylDTO(Vinyl vinyl){
        VinylDTO vinylDTO = new VinylDTO();
        vinylDTO.id = vinyl.getId();
        vinylDTO.title = vinyl.getTitle();
        vinylDTO.artist = vinyl.getArtist();
        vinylDTO.description = vinyl.getDescription();
        vinylDTO.price = vinyl.getPrice();
        vinylDTO.genre = vinyl.getGenre().name();
        vinylDTO.creator_id = vinyl.getClient().getId();

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

    public Vinyl vinylDTOToVinyl(CreateVinylDTO vinylDTO, Client client){
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

    public Review createReviewDTOtoReview(CreateReviewDTO createReviewDTO,
        Client review_from_client, Client reviewed_client){
            Review review = new Review();
            review.setReview(createReviewDTO.review);
            review.setStars(createReviewDTO.stars);
            review.setCreator(review_from_client);
            review.setReviewed_client(reviewed_client);

            return review;
        }
    
}
