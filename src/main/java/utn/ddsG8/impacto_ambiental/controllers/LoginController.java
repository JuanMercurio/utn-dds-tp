package utn.ddsG8.impacto_ambiental.controllers;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.List;

public class LoginController {
    private static Repositorio<User> repo = FactoryRepositorio.get(User.class);


    // TODO: metodo que se ocupe de ver si ya existe el usuario. De paso se puede arreglar la repeticion
    // de codigo en los controllers cuando creamos usuario

    public static Response login(Request request, Response response) {

        String query = "from "
                + User.class.getName()
                + " WHERE username = '"
                + request.queryParams("username")
                + "' AND password = '"
                + PassHasher.SHA_256(request.queryParams("password"))
                + "'";

        List<User> usuarios = repo.query(query);

        // si es 1 puso bien los datos
        if (usuarios.size() == 1) {
            request.session(true);
            User user = usuarios.get(0);
            request.session().attribute("id", user.getId());
            String nombreRol = user.getRole().getName();
            response.redirect("/" + nombreRol + "/" + user.getId());
        } else { // si entra aca es porque puso mal los datos
            response.redirect("/login");
        }

        return response;
    }

    public static ModelAndView logInView(Request request, Response response)  {
        return new ModelAndView(null, "login.hbs");
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/login");

        return response;
    }
}
