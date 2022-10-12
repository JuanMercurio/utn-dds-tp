package utn.ddsG8.impacto_ambiental.controllers;


import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.util.List;

public class LoginController {
    private Repositorio<User> repo = FactoryRepositorio.get(User.class);

    public Response login(Request request, Response response) {

        String query = "from "
                + User.class.getName()
                + "WHERE username = '"
                + request.queryParams("username")
                + "' AND password = '"
                + PassHasher.SHA_256(request.queryParams("password"))
                + "'";

        List<User> users =  repo.query(query);

        if (users != null) {
            request.session(true);
            request.session().attribute("id", users.get(0).getId());
            response.redirect("/organizacion"); // todo ver donde combiene redireccionarlo
        } else {
            response.redirect("/login");
        }

        return response;
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/login");

        return response;
    }
}
