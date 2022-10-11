package utn.ddsG8.impacto_ambiental.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.HashMap;
import java.util.List;

public class OrganizacionController {
    final Repositorio<Organizacion> repo = FactoryRepositorio.get(Organizacion.class);

    // retorna una vista en la cual figuran todas las organizaciones
    public ModelAndView showAll(Request request, Response response) {
        List<Organizacion> orgs = repo.buscarTodos();
        return null;
    }

    // retorna una vista en la cual figura la organizacion con el id elegido
    public ModelAndView show(Request request, Response response) {
        Organizacion org = repo.buscar(new Integer(request.params("id")));
        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizacion", org);
        }}, "organizacion/org.hbs");
    }

    // retorna una vista en la cual podemos crear una nueva org
    public ModelAndView create(Request request, Response response) {
        return new ModelAndView(null, "/organizacion/crear.hbs");
    }

    // instancia una nueva org y lo guarda en la db
    public Response save(Request request, Response response) {
        Organizacion org = new Organizacion();

        //todo setear las cosas de la org

        this.repo.agregar(org);

        response.redirect("/organizacion");
        return response;
    }

    // carga la nueva version del objeto a la base de datos
    public Response update(Request request, Response response) {
        return null;
    }

    // retorna la vista para editar una organizacion segun un id
    public ModelAndView edit(Request request, Response response) {
        return null;
    }

    public Response remove(Request request, Response response) {
        return null;
    }

}
