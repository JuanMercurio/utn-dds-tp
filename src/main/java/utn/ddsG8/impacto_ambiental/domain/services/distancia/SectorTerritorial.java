package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.*;
import java.util.List;

@Entity(name = "sectorTerritorial")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class SectorTerritorial {

    @Id
    @GeneratedValue
    public int id_db;

    @Column
    public int id;

    @Getter
    @Column(name = "nombre")
    public String nombre;

    @OneToMany(mappedBy = "sectorTerritorial")
    public List<AgenteSectorial> agentes;

    public abstract SectorTerritorial getPadre();
}
