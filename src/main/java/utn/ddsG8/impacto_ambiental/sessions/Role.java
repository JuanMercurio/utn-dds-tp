package utn.ddsG8.impacto_ambiental.sessions;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "role")
public class Role extends Persistable {
    @Column(name = "name")
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "role_permiso", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Permiso> permisos;

    public Boolean tieneTodosLosPermisos(Permiso ... permisos) {
        return Arrays.stream(permisos).allMatch(p -> this.permisos.contains(p));
    }
}
