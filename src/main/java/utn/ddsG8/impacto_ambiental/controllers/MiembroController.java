package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.helpers.OrganizacionHelper;
import utn.ddsG8.impacto_ambiental.domain.helpers.SectorHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MiembroController {

    private static Repositorio<Miembro> miembros = FactoryRepositorio.get(Miembro.class);
    private static Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);

    public static ModelAndView createView(Request request, Response respose) {
        return new ModelAndView(null, "/miembro/newMiembro.hbs");
    }

    public static Response save(Request request, Response response) {

        User user = UserController.crearUsuario(request, response);
        if (user == null) return response;

        String nombre = request.queryParams("nombre");
        String apellido = request.queryParams("apellido");
        String documento = request.queryParams("documento");
        TipoDoc tipodoc = TipoDoc.valueOf(request.queryParams("docTipo"));

        Miembro nuevoMiembro = new Miembro(nombre, apellido, tipodoc, documento);
        nuevoMiembro.setId(user.getId());
        miembros.agregar(nuevoMiembro);

        return response;
    }

    public static ModelAndView show(Request request, Response response) {
        Miembro miembro = miembros.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>() {{
            put("miembro", miembro);
        }}, "/miembro/miembro.hbs");
    }

    public static ModelAndView organizacionesParaUnirse(Request request, Response response) {
        //TODO falta poner los sectores
        List<Organizacion> orgs = repoOrganizacion.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>() {{
            put("organizaciones", orgs);
        }}, "/miembro/solicitarUnirseAOrg.hbs");
    }

    public static Response unirseAOrg(Request request, Response response) {
        Organizacion org = OrganizacionHelper.getOrg(new Integer(request.queryParams("org")));
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        Sector sector = SectorHelper.getSector(new Integer(request.queryParams("sector")));
        miembro.unirseAOrg(org, sector);
        repoOrganizacion.modificar(org);
        // TODO falta mandar un alert o algo como que ya lo solicito
        response.redirect("actualizar");
        return response;
    }

    public static Response actualizandome(Request request, Response response) {
        repoOrganizacion.buscarTodos();
        response.redirect("unirseAOrg");
        return response;
    }

    public static ModelAndView reportes(Request request, Response response) {
        Miembro miembro = MiembroHelper.getCurrentMiembro(request);
        Map<String, Object> parametros = new HashMap<>();
        Set<Organizacion> misOrgs = miembro.getSectores().stream().map(s -> s.getOrganizacion()).collect(Collectors.toSet());
        parametros.put("miembro", miembro);
        parametros.put("orgs", misOrgs);
        return new ModelAndView(parametros, "/miembro/reportes.hbs");
    }
}
