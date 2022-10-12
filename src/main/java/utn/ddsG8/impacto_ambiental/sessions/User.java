package utn.ddsG8.impacto_ambiental.sessions;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
public class User extends Persistable {

    @Column(name = "username")
    private String username;

    @Column(name = "hashed_pass")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
