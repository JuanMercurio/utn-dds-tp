package utn.ddsG8.impacto_ambiental.domain.Notificaciones;

public class Mail extends Notificacion{
    private String mail;

    public Mail(String mail) {
        this.mail = mail;
    }

    @Override
    public void Enviar() {
        // TODO
        System.out.println("Enviando whatsapp a"+this.mail);
        System.out.println("ENVIANDOME MAIL");
    }
    /*public void EnviarNotificacion(){
        for (:
             ) {

        }
        t.scheduleAtFixedRate(,5,this.tiempoNotificacion);
        t.schedule(wpp,5);
        t.schedule(wpp,new Date(2022,7,13));
    }*/
    @Override
    public void run() {
        //Enviar();
        System.out.println("MAIL:Ingrese a para saber mas. www.huelladecarbono.com.ar"+this.mail);
    }
}
