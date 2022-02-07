package control.DAO;

import java.io.Serializable;

public class ClientDAO implements Serializable {

    public String username;
    public String password;
    public ContactDAO contactDAO;

    public ClientDAO(){}
    
}
