package control;

import control.DAO.ClientDAO;
import control.DAO.ContactDAO;
import entities.basic.Client;
import entities.basic.Contact;

public class EntityConverter {

    public ClientDAO clientToClientDAO(Client client){
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.username = client.getUsername();

        if (client.getContact() != null){
            clientDAO.contactDAO = this.contactToContactDAO(client.getContact());
        }

        return clientDAO;
    }

    private ContactDAO contactToContactDAO(Contact contact){
        ContactDAO contactDAO = new ContactDAO();
        contactDAO.email = contact.getEmail();
        contactDAO.phone = contact.getPhone();

        return contactDAO;
    }
    
}
