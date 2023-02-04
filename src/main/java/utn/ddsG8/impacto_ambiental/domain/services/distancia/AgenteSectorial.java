package utn.ddsG8.impacto_ambiental.domain.services.distancia;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "agentesectorial")
public class AgenteSectorial {

    @Id
    @Setter
    @Getter
    private int id;

    @Getter
    @Column
    private String nombre;

    @Setter
    @ManyToOne
    @JoinColumn(name = "sectorTerritorial", referencedColumnName = "id_DB")
    SectorTerritorial sectorTerritorial;


    public AgenteSectorial(String nombre) {
        this.nombre = nombre;
    }

    public AgenteSectorial() {}
}
