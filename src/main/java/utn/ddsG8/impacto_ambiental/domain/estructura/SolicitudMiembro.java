package utn.ddsG8.impacto_ambiental.domain.estructura;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "solucitud_miembro_organizacion")
public class SolicitudMiembro extends Persistable {

    @ManyToOne()
    @JoinColumn(name = "solicitante", referencedColumnName = "id")
    private Miembro solicitante;

    @ManyToOne()
    @JoinColumn(name = "sector", referencedColumnName = "id")
    private Sector sector;

    public SolicitudMiembro() {}
    public SolicitudMiembro(Miembro solicitante, Sector sector) {
        this.solicitante = solicitante;
        this.sector = sector;
    }
}
