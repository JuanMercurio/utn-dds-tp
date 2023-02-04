package utn.ddsG8.impacto_ambiental.domain.estructura;

import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Contacto;
import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Notificacion;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


// TODO: Persistir esto?
public class Administrador {
    private String usuario;
    private String contrasenia;
    // TODO, explicar porque. XQ L ADMIN LAS TIENE QUE TENER.
    private List<Organizacion> organizaciones;
    private CalcularHC calculador;
    public Timer time;
    private long periodoEnvioNotificaciones;
    //private List<Notificacion> notificaciones;
    //private QuartzScheduler probando;


    public Administrador() {
        this.organizaciones = new ArrayList<>();
        this.time = new Timer();
        this.calculador = new CalcularHC();
        //this.notificaciones = new ArrayList<>();
        this.periodoEnvioNotificaciones = 1000;
        /*Whatsapp wpp = new Whatsap;
        notificaciones.add(wpp);
        Mail mail = new Mail();
        notificaciones.add(mail);*/
    }
    public void EnviarNotificaciones(){
        for (Organizacion org: organizaciones) {
            for (Contacto contacto: org.getContactos()) {
                for (Notificacion not: contacto.getNotificaciones()) {
                    this.time.scheduleAtFixedRate(not,5,this.periodoEnvioNotificaciones);
                    this.time.schedule(not,this.periodoEnvioNotificaciones);
                    //this.time.schedule(not,new Date(2022,7,13));
                    System.out.println();
                    time.notify();
                }
            }


        }
    }

    public void AgregarOrganizacion( Organizacion organizacion){
        this.organizaciones.add(organizacion);
    }

    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(List<Organizacion> organizaciones) {
        this.organizaciones = organizaciones;
    }

    public CalcularHC getCalculador() {
        return calculador;
    }

    public void setCalculador(CalcularHC calculador) {
        this.calculador = calculador;
    }

    //String nombre, TransportePublico transportePublico, Direccion direccion, double distanciaProxima, double distanciaAnterior, int i
    public void AgregarParadaTransportePublico(String nombre, TransportePublico transportePublico, Direccion direccion,
                                               double distanciaProxima, double distanciaAnterior, int i ){
    }
    public long getPeriodoEnvioNotificaciones() {
        return periodoEnvioNotificaciones;
    }

    public void setPeriodoEnvioNotificaciones(long periodoEnvioNotificaciones) {
        this.periodoEnvioNotificaciones = periodoEnvioNotificaciones;
    }

    // TODO: LEVANTAR ARCHIVO O PONER FIJO CADA FE DE LA TABLA DE LA ENTREGA 2

    public  void ModificarFactirEmision ( String actividad, String tipoConsumo, double factorEmision){
        //MODIFICA solo el FE de una actividad y tipo consumo existente.
        this.calculador.modificarFE(actividad,tipoConsumo,factorEmision);
    }

    public void CargarFE(String actividad, String tipoConsumo, double factorEmision, String unidad){
        FE fe = new FE(actividad,tipoConsumo,unidad,factorEmision);
        this.calculador.cargarFactorEmision(fe);
    }



}
