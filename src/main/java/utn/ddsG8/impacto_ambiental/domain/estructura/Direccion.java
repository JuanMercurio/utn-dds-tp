package utn.ddsG8.impacto_ambiental.domain.estructura;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "direccion")
@Table
public class Direccion extends Persistable {

    @Column
    private String calle;

    @Column
    private Integer altura;

    @ManyToOne
    @JoinColumn(name = "localidad", referencedColumnName = "id_db")
    private Localidad localidad;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parada", referencedColumnName = "id")
    private Parada parada;

    public Direccion() {}
    public Direccion(String calle, Integer altura, Localidad localidad) {
        this.calle = calle;
        this.altura = altura;
        this.localidad = localidad;
    }

    public Localidad getLocalidad() {
        return this.localidad;
    }

    public String getCalle() {
        return this.calle;
    }

    public String getAltura() {
        return this.altura.toString();
    }

    public boolean perteneceASectorTerritorial(SectorTerritorial sectorTerritorial) {
        return sectorTerritorial == localidad || sectorTerritorial == localidad.getMunicipio() || sectorTerritorial == localidad.getMunicipio().getProvincia();
    }
}
