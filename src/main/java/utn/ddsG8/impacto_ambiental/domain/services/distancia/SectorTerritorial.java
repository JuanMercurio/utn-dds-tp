package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Huella;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

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

    @OneToMany()
    @JoinColumn(name = "sector_territorial", referencedColumnName = "id")
    public List<Huella> huellas;

    public  double calcularHC() {
        return CalcularHC.getInstancia().obtenerHCSectorTerritorial(this);
    }

    public void persistirHC() {
        FactoryRepositorio.get(Huella.class).agregar(new Huella(this.calcularHC()));
    }

    public abstract SectorTerritorial getPadre();
    public abstract boolean tieneDireccion(Direccion direccion);
}
