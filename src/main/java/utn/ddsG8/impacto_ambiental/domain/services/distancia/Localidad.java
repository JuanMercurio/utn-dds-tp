package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import javax.persistence.*;

@Entity
@Table
public class Localidad {

    @Id
    public int id;
    @Column
    public String nombre;
    @Column
    public int codPostal;

    @ManyToOne
    @JoinColumn(name = "municipio", referencedColumnName = "id")
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
}
