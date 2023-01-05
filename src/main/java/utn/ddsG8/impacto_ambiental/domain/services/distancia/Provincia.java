package utn.ddsG8.impacto_ambiental.domain.services.distancia;

import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Provincia {

    @Id
    public int id;
    @Column(name = "nombre")
    public String nombre;

    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "pais", referencedColumnName = "id")
    public Pais pais;

    @Transient
    public List<AgenteSectorial> agenteSectorialesP;

    public Provincia(int id, String nombre, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        agenteSectorialesP = new ArrayList<>();
    }

    public Provincia(){}

    public void AgregarAgenteSectorial(AgenteSectorial ags){
        agenteSectorialesP.add(ags);
    }

    public double CalcularHCProvincia (List<Organizacion> organizaciones){
        double hc = 0;
        for (Organizacion org: organizaciones) {

            if(org.getDireccion().getLocalidad().municipio.provincia.nombre == this.nombre){
                hc+= org.CalcularHC();
            }
            //
        }
        return hc;
    }
}
