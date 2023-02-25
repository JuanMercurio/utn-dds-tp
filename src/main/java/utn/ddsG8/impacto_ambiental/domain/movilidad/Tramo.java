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

    @Column(name = "huella")
    private double huella;

    public Tramo() {}
    public Tramo(Transporte transporte, Direccion inicio, Direccion fin) {
        this.transporte = transporte;
        this.inicio = inicio;
        this.fin = fin;
        this.transporte.calcularDistancia(this);
        this.setHuella();
    }

    public Double calcularHC() {
        this.huella = Math.round(transporte.calcularHCTramo(this)*100)/100;
        return this.huella;
    }

    public void setHuella() {
        this.huella = (double) Math.round(calcularHC()*100)/100;
    }

}