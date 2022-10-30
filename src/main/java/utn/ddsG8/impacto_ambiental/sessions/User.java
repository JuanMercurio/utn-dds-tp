package utn.ddsG8.impacto_ambiental.sessions;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Referencia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.AgenteSectorial;

import javax.persistence.*;

@Setter
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
        this.password = PassHasher.SHA_256(password);
    }

    public boolean isAdmin() {
        return this.username.equals("admin");
    }

}
