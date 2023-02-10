package persistence;

import org.junit.jupiter.api.Test;

public class Persistir {

    @Test
    public void persistirDatos() {
        PersistirFactoresEmision.persistirFactores();
        PersistirDireccionesTest.persistirDirecciones();
//        PersistirUsuarios.;
    }
}
