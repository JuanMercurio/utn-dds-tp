package utn.ddsG8.impacto_ambiental.domain.Notificaciones;

public class Whatsapp extends Notificacion{
    private String telefono;

    public Whatsapp(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void Enviar() {
        // TODO
    }

    @Override
    public void run() {
        System.out.println("Enviando whatsapp a"+this.telefono);
        System.out.println("Wpp:Ingrese a para saber mas. www.huelladecarbono.com.ar");
    }
}
