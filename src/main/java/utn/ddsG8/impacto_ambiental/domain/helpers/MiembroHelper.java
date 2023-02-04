package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;


public class MiembroHelper {

    public static Repositorio<Miembro> repositoryMiembros = FactoryRepositorio.get(Miembro.class);

    public static Miembro getCurrentMiembroInURL(Request request) {
        return repositoryMiembros.buscar(UserHelper.loggedUser(request).getId());
    }
}
