package utn.ddsG8.impacto_ambiental.domain.movilidad;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.converters.DistanciaConverter;
import utn.ddsG8.impacto_ambiental.db.converters.LocalTimeAttributeConverter;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Transporte;
import utn.ddsG8.impacto_ambiental.db.Persistable;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tramo")
@DiscriminatorColumn(name = "tipo_tramo")
public abstract class Tramo extends Persistable {

    @Column(name = "fecha")
    @Convert(converter = LocalTimeAttributeConverter.class)
    protected LocalDate fecha;

    @Getter
    @Column(name = "distancia")
    @Convert(converter = DistanciaConverter.class)
    protected Distancia distancia;

    @Getter
    @ManyToOne
    @JoinColumn(name = "transporte", referencedColumnName = "id")
    protected Transporte transporte;

    public Double calcularHC() {
        System.out.println("AAAAAAAAAAAA;LKJASDF;LKAJSDF;LKJASDF:w");
        return transporte.calcularHC(this.distancia.valor);
    }

    public Double calcularHCAnual(int anio) {
        if (this.fecha.getYear() == anio) {
            return this.transporte.calcularHC(this.distancia.valor);
        }
        return 0.0;
    }

    public Double calcularHCMensual(int anio, int mes) {
        if (this.fecha.getMonth().getValue() == mes && this.fecha.getYear() == anio) {
            return this.transporte.calcularHC(this.distancia.valor);
        }
        return 0.0;
    }
}