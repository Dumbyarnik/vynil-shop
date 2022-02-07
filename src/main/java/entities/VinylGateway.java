package entities;

import java.io.Serializable;
import java.util.Collection;
import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;


import control.DAO.ClientDAO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.basic.Vinyl;
import entities.security.UserLogin;

@Model
@Dependent
public interface VinylGateway extends Serializable {
    public Collection<Vinyl> getVinyls();
    public void createVinyl(Vinyl vinyl);
}
