package utn.ddsG8.impacto_ambiental.domain.movilidad;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.converters.DistanciaConverter;
import utn.ddsG8.impacto_ambiental.db.converters.LocalTimeAttributeConverter;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaTramo;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tramo")
@DiscriminatorColumn(name = "tipo_tramo")
public class Tramo extends Persistable {

    @Column(name = "fecha")
    @Convert(converter = LocalTimeAttributeConverter.class)
    protected LocalDate fecha;

    @Column(name = "distancia")
    @Convert(converter = DistanciaConverter.class)
    protected Distancia distancia;

    @ManyToOne
    @JoinColumn(name = "transporte", referencedColumnName = "id")
    protected Transporte transporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_inicio", referencedColumnName = "id")
    private Direccion inicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_fin", referencedColumnName = "id")
    private Direccion fin;

    // consultarle a pablo (tener en cuenta tambien las otra soluciones como la suma con 0s, cargarlo 2 veces, etc)
    // no se como no repetir codigo. El dile es como hacer en este caso es como hacer para no repetir codigo en las
    // clases que hacen lo mismo (sin una subclase mas). Hay solo 1 subclase de transporte que no hace lo mismo
    // consultar la mejor solucion
    @Transient
    private CalculadorDistanciaTramo calculadorTramo;

    public Tramo() {}
    public Tramo(Transporte transporte, Direccion inicio, Direccion fin) {
        this.transporte = transporte;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = LocalDate.now();
        this.transporte.setCalculadorDistanciaTramo(this);
        this.calcularDistancia();;
    }

    public Double calcularHC() {
        return transporte.calcularHCTramo(this);
    }

    public void calcularDistancia() {
        calculadorTramo.calcularDistanciaTramo(this);
    }

}