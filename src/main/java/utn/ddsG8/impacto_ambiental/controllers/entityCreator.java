package utn.ddsG8.impacto_ambiental.controllers;

import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.estructura.Clasificacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.OrgTipo;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class entityCreator {

    private static Repositorio<Organizacion> organizaciones = FactoryRepositorio.get(Organizacion.class);

    // TODO falta agregar la direccion de la empresa
    public static Response createOrg(Request request, Response response) {

        String razonSocial = request.queryParams("razonSocial");
        OrgTipo orgTipo = Enum.valueOf(OrgTipo.class, request.queryParams("orgTipo"));
        Clasificacion clasificacion = Enum.valueOf(Clasificacion.class, request.queryParams("clasificacion"));

        Organizacion newOrg = new Organizacion(razonSocial, orgTipo, clasificacion, null);
        ///agrear id (mismo que el usuario)

        organizaciones.agregar(newOrg);

        return response;
    }
}
