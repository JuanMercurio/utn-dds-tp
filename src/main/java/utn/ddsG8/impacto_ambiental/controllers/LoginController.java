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

    public static Response login(Request request, Response response) {

        String query = "from "
                + User.class.getName()
                + " WHERE username = '"
                + request.queryParams("username")
                + "' AND password = '"
                + PassHasher.SHA_256(request.queryParams("password"))
                + "'";

        User user =  repo.query(query).get(0);

        if (user != null) {
            request.session(true);
            request.session().attribute("id", user.getId());
            String nombreRol = user.getRole().getName();
            response.redirect("/" + nombreRol);
            System.out.println("Vamos a /" + nombreRol);
        } else {
            response.redirect("/login");
        }

        return response;
    }

    public static ModelAndView logInResponse(Request request, Response response)  {
        return new ModelAndView(null, "login.hbs");
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/login");

        return response;
    }
}
