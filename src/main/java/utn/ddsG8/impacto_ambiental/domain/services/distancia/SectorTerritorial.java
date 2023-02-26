package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Huella;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sec", referencedColumnName = "id_db")
    public List<Huella> huellas;

    public  double calcularHC() {
        return CalcularHC.getInstancia().obtenerHCSectorTerritorial(this);
    }

    public void persistirHC() {
        Huella huella = new Huella(calcularHC());
        this.huellas.add(huella);
        FactoryRepositorio.get(SectorTerritorial.class).modificar(this);
    }

    public abstract SectorTerritorial getPadre();
    public abstract boolean tieneDireccion(Direccion direccion);

    public List<Organizacion> getOrganizaciones() {
       return  FactoryRepositorio.get(Organizacion.class).buscarTodos().stream().filter(o -> o.getDireccion().perteneceASectorTerritorial(this)).collect(Collectors.toList());
    }
}
