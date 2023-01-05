package utn.ddsG8.impacto_ambiental.domain.movilidad;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.converters.DistanciaConverter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "trayecto")
public class Trayecto extends Persistable {

    @Getter
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "trayecto_miembro",
            joinColumns = @JoinColumn(name = "trayecto_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "trayecto_org",
            joinColumns = @JoinColumn(name = "trayecto_id"),
            inverseJoinColumns = @JoinColumn(name = "org_id")
    )

    private List<Organizacion> organizaciones;

    // es la forma fea de hacer u one-to-many unidireccional
    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto", referencedColumnName = "id")
    private List<Tramo> tramos;

    @Column(name = "distancia")
    @Convert(converter = DistanciaConverter.class)
    private Distancia distancia;

    public Trayecto() {
        this.miembros = new ArrayList<Miembro>() ;
        this.organizaciones = new ArrayList<Organizacion>() ;
        this.tramos = new ArrayList<Tramo>() ;
    }

    public Distancia getDistancia() {
        if (distancia == null) this.setDistancia();
        return distancia;
    }

    public Distancia getDistanciaDeTramo(int index) {
        return tramos.get(index).getDistancia();
    }

    private void setDistancia() {
        double valor = (double) tramos.stream().mapToDouble(t -> t.getDistancia().valor) .sum();
        final String unidad = tramos.get(0).getDistancia().unidad;
        this.distancia = new Distancia(valor, unidad);
    }

    public void agregarTramo(Tramo tramo) {
        tramos.add(tramo);
    }

    public void agregarTramos(Tramo ... tramo) {
        Stream.of(tramo).forEach(t -> tramos.add(t));
    }

    public  void agregarOrganizacion(Organizacion unaOrg) {
        this.organizaciones.add(unaOrg);
    }

    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public double CalcularHCTrayecto(){
        double hc = 0;

        for ( Tramo tramo: tramos) {
            hc += tramo.calcularHC();
        }
        return hc;

    }
    public double CalcularHCTrayectoMensual(CalcularHC calculador, int mes, int anio){
        double hc = 0;

        for ( Tramo tramo: tramos) {
            hc += tramo.calcularHCMensual(mes,anio);
        }
        return hc;

    }
    public double CalcularHCTrayectoAnual(CalcularHC calculador, int anio){
        double hc = 0;

        for ( Tramo tramo: tramos) {
            hc += tramo.calcularHCAnual(anio);
        }
        return hc;

    }

    public boolean formaParte(Organizacion organizacion) {
        return organizaciones.contains(organizacion);
    }
}
