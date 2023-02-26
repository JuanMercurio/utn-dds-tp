package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.calculadores.CalculadorDistanciaCalles;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.CombustibleE;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.DistanciaServicio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import javax.persistence.*;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("vehiculo_particular")
public class VehiculoParticular extends Transporte {

    @Enumerated(EnumType.STRING)
    protected CombustibleE combustible;

    @ManyToOne()
    @JoinColumn(name = "duenio", referencedColumnName = "id")
    protected Miembro duenio;

    @Column
    private double cantidadCombustiblePorKm;

    public VehiculoParticular() {}
    public VehiculoParticular(String nombre, String tipoVehiculo, String fe, CombustibleE combustible, double cantidadCombustiblePorKm) {
        this.nombre = nombre;
        this.tipoDeTransporte = tipoVehiculo;
        this.nombreFE = fe;
        this.combustible = combustible;
        this.cantidadCombustiblePorKm = cantidadCombustiblePorKm;
    }

    @Override
    public double FEvalor() {
        return FactoryRepositorio.get(FE.class).query("FROM factor_emision where nombre = '" + this.nombreFE + "'").get(0).getValor();
    }

    @Override
    public double getConsumo(Tramo t) {
        return t.getDistancia().valor * this.cantidadCombustiblePorKm;
    }

    @Override
    public Boolean esPublico() {
        return false;
    }

    @Override
    public void calcularDistancia(Tramo tramo) {
        new CalculadorDistanciaCalles().calcularDistanciaTramo(tramo);
    }

}
