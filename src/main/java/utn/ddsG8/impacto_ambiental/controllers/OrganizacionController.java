package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.Clasificacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.OrgTipo;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.Role;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.HashMap;
import java.util.List;

public class OrganizacionController {
    private final static Repositorio<Organizacion> organizaciones = FactoryRepositorio.get(Organizacion.class);

    // retorna una vista en la cual figuran todas las organizaciones
    public static ModelAndView showAll(Request request, Response response) {
        List<Organizacion> orgs = organizaciones.buscarTodos();
        return null;
    }

    // retorna una vista en la cual figura la organizacion con el id elegido
    public static ModelAndView show(Request request, Response response) {
        Organizacion org = organizaciones.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", org);
        }}, "organizacion/org.hbs");
    }

    public static ModelAndView createView(Request request, Response response) {
        return new ModelAndView(null, "newOrg.hbs");
    }

    // instancia una nueva org y lo guarda en la db
    public static Response save(Request request, Response response) {

        User user = UserController.create(request, response);
        if (user == null) {
            return response;
        }

        Role rol = (Role) EntityManagerHelper.getEntityManager().createQuery("from Role where name = 'organizacion'").getSingleResult();
        if (rol == null) {
            System.out.println("esta funcionando mal perro");
        }
        user.setRole(rol);
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);

        Organizacion org = new Organizacion();

        String razonSocial = request.queryParams("razonSocial");
        OrgTipo orgTipo = OrgTipo.valueOf(request.queryParams("orgTipo"));
        Clasificacion clasificacion = Clasificacion.valueOf(request.queryParams("clasificacion"));

        Organizacion newOrg = new Organizacion(razonSocial, orgTipo, clasificacion, null);
        newOrg.setId(user.getId());

        organizaciones.agregar(org);

        response.redirect("/prohibido");

        // Termine de instanciar la nueva organizacion
        return response;
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

}
