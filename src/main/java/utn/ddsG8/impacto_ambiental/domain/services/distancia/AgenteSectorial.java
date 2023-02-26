package utn.ddsG8.impacto_ambiental.domain.services.distancia;


import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "agentesectorial")
public class AgenteSectorial {

    @Id
    private int id;

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "sectorTerritorial", referencedColumnName = "id_DB")
    SectorTerritorial sectorTerritorial;

    public AgenteSectorial() {}
    public AgenteSectorial(String nombre) {
        this.nombre = nombre;
    }

    public double calcularHC() {
        return CalcularHC.getInstancia().obtenerHCSectorTerritorial(this.sectorTerritorial);
    }
}
