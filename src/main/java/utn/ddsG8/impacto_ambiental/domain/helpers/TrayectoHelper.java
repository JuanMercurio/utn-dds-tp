package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class TrayectoHelper {
    private static Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);

    public static Trayecto getTrayecto(Request request) {
        return repoTrayecto.buscar(Integer.parseInt(request.params("idTrayecto")));
    }

    public static Trayecto getTrayecto(int i) {
        return repoTrayecto.buscar(i);
    }
}
