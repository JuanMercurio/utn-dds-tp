package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("municipio")
public class Municipio extends SectorTerritorial implements Serializable {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia", referencedColumnName = "id_db")
    public Provincia provincia;

    public Municipio() {}
    public Municipio(int id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    @Override
    public SectorTerritorial getPadre() {
        return this.provincia;
    }

    @Override
    public boolean tieneDireccion(Direccion direccion) {
        return false;
    }
}
