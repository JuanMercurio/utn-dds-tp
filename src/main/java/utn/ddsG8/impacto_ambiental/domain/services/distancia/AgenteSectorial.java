package utn.ddsG8.impacto_ambiental.domain.services.distancia;


import javax.persistence.*;

@Entity(name = "agenteSectorial")
public class AgenteSectorial {

    @Id
    private int id;

    @Column
    private String nombre;

//    @ManyToOne
//    @JoinColumn(name = "sectorTerritorial", referencedColumnName = "id")
    @Transient
    SectorTerritorial sectorTerritorial;


    public AgenteSectorial(String nombre) {
        this.nombre = nombre;
    }
}
