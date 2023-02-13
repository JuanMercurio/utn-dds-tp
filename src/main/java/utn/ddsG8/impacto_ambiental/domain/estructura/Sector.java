package utn.ddsG8.impacto_ambiental.domain.estructura;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sector")
public class Sector extends Persistable {

    @Getter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sector_miembro",
            joinColumns = @JoinColumn(name = "sector_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizacion", referencedColumnName = "id")
    private Organizacion organizacion;

    @Setter
    @Getter
    @Column(name = "huella")
    private Double huella;

    @Getter
    @Column(name = "cantidadMiembros")
    private Double cantidadMiembros;

    public Sector() {}
    public Sector(String nombre, Organizacion org) {
        this.nombre = nombre;
        this.organizacion = org;
        this.miembros = new ArrayList<Miembro>();
        this.cantidadMiembros = 0.0;
        this.organizacion.getSectores().add(this);
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
        cantidadMiembros++;
    }

    public double calcularHC() {
        huella = CalcularHC.getInstancia().obtenerHCSector(this);
        return huella;
    }

    public Integer cantidadMiembros() {
        return this.miembros.size();
    }

}
