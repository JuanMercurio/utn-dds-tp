package utn.ddsG8.impacto_ambiental.domain.estructura;

import lombok.Getter;
import lombok.Setter;
import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Contacto;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Medicion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organizacion")
@Setter
@Getter
public class Organizacion {

    @Id
    private int id;

    @Column(name = "razon_social")
    private String razonSocial;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo")
    private OrgTipo tipo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "clasificacion")
    private Clasificacion clasificacion;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // esta cascada es medio dudosa
    @JoinColumn(name = "direccion", referencedColumnName = "id")
    private Direccion direccion;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sector> sectores;

    // esta es la forma fea del one-to-many unidireccional
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion", referencedColumnName = "id")
    private List<SolicitudMiembro> potencialesMiembros;

    @Column(name = "archivo_datos_actividades")
    private String archivoDatosActividades;

    @ManyToMany(mappedBy = "organizaciones", fetch =FetchType.LAZY, cascade = CascadeType.PERSIST) // tiene sentido que los trayectos sigan vivos sin la organizacion?
    private Set<Trayecto> trayectos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion", referencedColumnName = "id")
    private List<Medicion> mediciones;

    // esta es la forma fea de one-to-many unidireccional
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion", referencedColumnName = "id")
    private List<Contacto> contactos;

    public Organizacion() {}
    public Organizacion(String razonSocial, OrgTipo tipo, Clasificacion clasificacion,
                        Direccion direccion) {
        this.razonSocial         = razonSocial;
        this.tipo                = tipo;
        this.clasificacion       = clasificacion;
        this.direccion           = direccion;
        this.sectores            = new ArrayList<Sector>();
        this.potencialesMiembros = new ArrayList<SolicitudMiembro>();
        this.mediciones          = new ArrayList<Medicion>();
        this.contactos = new ArrayList<>();
        this.trayectos = new HashSet<>();
    }


    public int cantidadMiembros(){
        // se puede buscar una forma mas elegante como ya tener un set de miembros, pero eso seria informacion redundante...
        Set<Miembro> miembros = new HashSet<Miembro>();
        //esto esta como el orto addall no hace eso jajaja
        sectores.forEach(sector -> miembros.addAll(sector.getMiembros()));
        return miembros.size();
    }

    public void setArchivoDatosActividades(String archivoDatosActividades) {
        this.archivoDatosActividades = archivoDatosActividades;
    }

    public void aceptarMiembro(SolicitudMiembro m) {
        m.getSector().agregarMiembro(m.getSolicitante());
    }

    public void aceptarTodosLosMiembros(){
        for(SolicitudMiembro solicitudMiembro : potencialesMiembros ){
            aceptarMiembro(solicitudMiembro);
        }
        potencialesMiembros = null;
    }

    public void solicitudNuevoMiembro(Miembro miembro, Sector sector) {
        SolicitudMiembro solicitante = new SolicitudMiembro(miembro, sector);
        potencialesMiembros.add(solicitante);
    }

    public void CrearContacto (String nombre, String email,String telefono){
        Contacto c = new Contacto(email,telefono,nombre);
        contactos.add(c);
    }

    public boolean perteneceASector(SectorTerritorial sectorTerritorial) {
        return this.direccion.perteneceASectorTerritorial(sectorTerritorial);
    }

    public double calcularHC() {
        return CalcularHC.getInstancia().obtenerHCOrganizacion(this);
    }
}
