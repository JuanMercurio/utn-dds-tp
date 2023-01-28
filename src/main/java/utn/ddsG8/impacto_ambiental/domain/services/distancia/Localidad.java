package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DiscriminatorValue("localidad")
public class Localidad extends SectorTerritorial {

    @Column
    public int codPostal;

    @ManyToOne
    @JoinColumn(name = "municipio", referencedColumnName = "id_db")
    public Municipio municipio;

    public Localidad(int id, String nombre, int codPostal, Municipio municipio) {
        this.id = id;
        this.codPostal = codPostal;
        this.municipio = municipio;
        this.nombre = nombre;
    }


    public int getId() {
        return this.id;
    }

    @Override
    public SectorTerritorial getPadre() {
        return this.municipio;
    }
}
