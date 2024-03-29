/*
    @author: Daniil Vorobyev
*/
package entities.security;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@UserDefinition
public class UserLogin extends PanacheEntity {
    @Username
    @Column(unique = true)
    public String username;
    @Password
    public String password;
    @Roles
    public String role;

    public static void add(String username, String password, String role){
        UserLogin user = new UserLogin();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        user.persist();
    }
}
