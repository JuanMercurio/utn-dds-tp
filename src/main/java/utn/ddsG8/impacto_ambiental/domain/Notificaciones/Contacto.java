package utn.ddsG8.impacto_ambiental.domain.Notificaciones;

import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacto")
public class Contacto extends Persistable {

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "nombre")
    private String nombre;

    // TODO: jana esto es necesario persistir?
    @Transient
    private List<Notificacion> notificaciones;
    //private Timer t;
    //private long tiempoNotificacion;
    //private List<LocalDate> fechas;

    public Contacto(String emaill, String telefonoo, String nombree) {
        this.email = emaill;
        this.telefono = telefonoo;
        this.nombre = nombree;
        this.notificaciones = new ArrayList<>();
        Whatsapp wpp = new Whatsapp(this.telefono);
        Mail mail = new Mail(this.email);
        notificaciones.add(wpp);
        notificaciones.add(mail);
       // tiempoNotificacion = 1000;


        /* t = new Timer();

         t.scheduleAtFixedRate(wpp,5,this.tiempoNotificacion);
         t.schedule(wpp,5);
        t.schedule(wpp,new Date(2022,7,13));*/
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }
}
