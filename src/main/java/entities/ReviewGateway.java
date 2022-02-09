package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Model;
import javax.sql.rowset.serial.SerialArray;

import control.DTO.ClientDTO;
import entities.basic.Client;
import entities.basic.Contact;
import entities.security.UserLogin;

@Model
@Dependent
public interface ReviewGateway extends Serializable {
    
}
