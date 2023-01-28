package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import javax.persistence.*;

@Entity
@Table
public class Pais {

    @Id
    public int id;

    @Column
    public String nombre;

    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Pais() {}
}
