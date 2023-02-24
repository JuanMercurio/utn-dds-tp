package utn.ddsG8.impacto_ambiental.domain.Notificaciones;

import lombok.Getter;
import utn.ddsG8.impacto_ambiental.db.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "contacto")
public class Contacto extends Persistable implements Notificable {

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "nombre")
    private String nombre;

    public Contacto() {
    }

    public Contacto(String nombre, String email, String telefono) {
        this.telefono = telefono;
        this.email = email;
        this.nombre = nombre;
    }

    @Override
    public void notificar() {
        // si esto se implementase tendriamos que tener una lista de formas de contactarse con el contact
        // y hacer un for each en el cual llama polimorficamente a todos y los notifica de la manera que
        // cada clase implementa
        System.out.println("Notificando a contacto: " + this.nombre);
    }
}
