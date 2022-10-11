package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipio")
public class Municipio {

    @Id
    public int id;
    @Column(name = "nombre")
    public String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia", referencedColumnName = "id")
    public Provincia provincia;

    @Transient // TODO
    public List<AgenteSectorial> agentesSectoriales;

    public Municipio(int id, String nombre, Provincia provincia) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        agentesSectoriales = new ArrayList<>();
    }

    public void AgregarAgenteSectorial(AgenteSectorial ags){
        agentesSectoriales.add(ags);
    }
    public double CalcularHCMunicipio (List<Organizacion> organizaciones){
        double hc = 0;
        for (Organizacion org: organizaciones) {

            if(org.getDireccion().getLocalidad().municipio.nombre == this.nombre){
                hc+= org.CalcularHC();
            }
            //
        }
        return hc;
    }
}
