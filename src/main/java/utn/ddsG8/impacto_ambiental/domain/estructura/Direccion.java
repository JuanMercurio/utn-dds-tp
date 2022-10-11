package utn.ddsG8.impacto_ambiental.domain.estructura;

import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;

import javax.persistence.*;

@Entity
@Table
public class Direccion extends Persistable {
    @Column
    private String nombre;               // esta de mas?
    @Column
    private String calle;
    @Column
    private Integer altura;
    @ManyToOne
    @JoinColumn(name = "localidad", referencedColumnName = "id")
    private Localidad localidad;

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
}
