package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class OrganizacionHelper {
    private static Repositorio<Organizacion> organizacionRepositorio = FactoryRepositorio.get(Organizacion.class);
    public static Organizacion getOrg(Request request) {
        return organizacionRepositorio.buscar(UserHelper.loggedUser(request).getId());
    }
}
