package utn.ddsG8.impacto_ambiental.TimerContacto;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Contacto;

public class TimerContacto {
    @Test
    public void probando() throws InterruptedException {
        Contacto c = new Contacto("rozenberg", "123456789","Jana");

        c.wait(1000000);
        wait(1000000);
    }
}
