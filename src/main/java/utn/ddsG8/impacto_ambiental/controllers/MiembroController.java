package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.Role;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.HashMap;

public class MiembroController {

    private static Repositorio<Miembro>  miembros = FactoryRepositorio.get(Miembro.class);

    public static ModelAndView createView(Request request, Response respose) {
        return new ModelAndView(null, "/miembro/newMiembro.hbs");
    }

    public static Response save(Request request, Response response) {

        // TODO: Se puede separar esta parte como esta en figma pero por ahora va todo en un request
        User user = UserController.create(request, response);
        if (user == null) {
            return response;
        }

        Role rol = (Role) EntityManagerHelper.getEntityManager().createQuery("from Role where name = 'miembro'").getSingleResult();
        user.setRole(rol);
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);

        String nombre = request.queryParams("nombre");
        String apellido = request.queryParams("apellido");
        String documento = request.queryParams("documento");
        TipoDoc tipodoc = TipoDoc.valueOf(request.queryParams("docTipo"));

        Miembro nuevoMiembro = new Miembro(nombre, apellido, tipodoc, documento);
        nuevoMiembro.setId(user.getId());

        miembros.agregar(nuevoMiembro);

        response.redirect("/miembro/" + nuevoMiembro.getId());

        return response;
    }

    public static ModelAndView show(Request request, Response response) {
        Miembro miembro = miembros.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("miembro", miembro);
        }}, "/miembro/miembro.hbs");
    }
}
