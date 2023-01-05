package utn.ddsG8.impacto_ambiental.domain.movilidad.transportes;

import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class VehiculoParticular extends TransportePrivado {
    @Transient
    protected Combustible combustible;
    @ManyToOne()
    @JoinColumn(name = "duenio", referencedColumnName = "id")
    protected Miembro duenio;
}
