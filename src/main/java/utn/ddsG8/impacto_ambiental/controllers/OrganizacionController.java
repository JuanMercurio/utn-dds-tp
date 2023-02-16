package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.Huella;
import utn.ddsG8.impacto_ambiental.domain.calculos.Medicion;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.services.sheets.LectorExcel;
import utn.ddsG8.impacto_ambiental.domain.helpers.OrganizacionHelper;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import spark.template.freemarker.FreeMarkerEngine;

public class OrganizacionController {
    private final static Repositorio<Organizacion> repoOrg = FactoryRepositorio.get(Organizacion.class);
    private final static Repositorio<SolicitudMiembro> solicitudesPendientes = FactoryRepositorio.get(SolicitudMiembro.class);

    // retorna una vista en la cual figuran todas las organizaciones
    public static ModelAndView showAll(Request request, Response response) {
        List<Organizacion> orgs = repoOrg.buscarTodos();
        return null;
    }

    // retorna una vista en la cual figura la organizacion con el id elegido
    public static ModelAndView show(Request request, Response response) {
        Organizacion org = repoOrg.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", org);
            put("huella", org.calcularHC());
        }}, "organizacion/org.hbs");
    }

    // retorna una vista en la cual se crea una nueva organizacion
    public static ModelAndView createView(Request request, Response response) {
        return new ModelAndView(null, "organizacion/newOrg.hbs");
    }

    // instancia una nueva org y lo guarda en la db
    public static Response save(Request request, Response response) {

        User user = UserController.create(request, response);
        if (user == null) return response;

        user.setRole(RoleHelper.getRole("organizacion"));
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);

        // todo falta la direccion. ver si lo hace despues el usuario o ahora
        String razonSocial = request.queryParams("razonSocial");
        OrgTipo orgTipo = OrgTipo.valueOf(request.queryParams("orgTipo"));
        Clasificacion clasificacion = Clasificacion.valueOf(request.queryParams("clasificacion"));

        Organizacion newOrg = new Organizacion(razonSocial, orgTipo, clasificacion, null);
        newOrg.setId(user.getId());

        repoOrg.agregar(newOrg);

        response.redirect("/organizacion/" + newOrg.getId());

        return response;
    }

    // retorna la vista con todas las solicitudes que tiene una org
    public static ModelAndView solicitudes(Request request, Response response) {
        String query = "from SolicitudMiembro where organizacion = '"+ request.params("id") + "'";
        List<SolicitudMiembro> solicitudes = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("solicitud", solicitudes);
        }}, "organizacion/solicitudes_miembros.hbs");
    }

    // carga la nueva version del objeto a la base de datos
    public static Response update(Request request, Response response) {
        return null;
    }

    // retorna la vista para editar una organizacion segun un id
    public static ModelAndView edit(Request request, Response response) {
        return null;
    }

    public Response remove(Request request, Response response) {
        return null;
    }

    public static Response administrarSolicitud(Request request, Response response) {
       int idSolicitud = Integer.parseInt(request.queryParams("id_solicitud"));
       SolicitudMiembro solicitud = solicitudesPendientes.buscar(idSolicitud);
       solicitudesPendientes.eliminar(solicitud);

       // todo... hay que notificar?

       if (request.queryParams("estado") == "aceptado") {
           solicitud.getSector().agregarMiembro(solicitud.getSolicitante());
           FactoryRepositorio.get(Sector.class).modificar(solicitud.getSector());

       }

       response.redirect("solicitudes");
       return response;
    }

    public static Response actualizandoSoli (Request request, Response response) {
        solicitudesPendientes.buscarTodos();
        response.redirect("solicitudes");
        return response;
    }

    public static ModelAndView subirActividadesView(Request request, Response response) {
        return new ModelAndView(null, "organizacion/subirActividades.hbs");
    }

    private static List<Medicion> administrarArchivoActividades(Request request, Response response) {

        // aca se podria crear el archivo y guardarlo en la base de datos, pero por ahora directamente carga las mediciones
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try (InputStream input = request.raw().getPart("archivo").getInputStream()) {
            return LectorExcel.getMedicionesFromInputStream(input);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String subirActividades(Request request, Response response) {
        List<Medicion>  mediciones = administrarArchivoActividades(request, response);
        Organizacion org = OrganizacionHelper.getOrg(request);
        org.getMediciones().addAll(mediciones);
        repoOrg.modificar(org);

        return "cargamos las nuevasmediciones perro";
    }

    public static ModelAndView vistaCalculadora(Request request, Response response) {

        List<Sector> sectores = OrganizacionHelper.getOrg(request).getSectores();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("sectores", sectores);
        }}, "organizacion/calculadora.hbs");
    }

    public static Response persistirHuella(Request request, Response response) {
        double valor = CalcularHC.getInstancia().obtenerHCOrganizacion(OrganizacionHelper.getOrg(request));

        if (valor >= 0) {
            response.status(200);
            Organizacion org = OrganizacionHelper.getOrg(request);
            org.getHuellas().add(new Huella(valor));
            repoOrg.modificar(org);
        }
        else response.status(500);

        return response;
    }
}
