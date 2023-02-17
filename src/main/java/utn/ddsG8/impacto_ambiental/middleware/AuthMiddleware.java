package utn.ddsG8.impacto_ambiental.middleware;


import spark.Request;
import spark.Response;
import spark.Spark;
import utn.ddsG8.impacto_ambiental.domain.helpers.MiembroHelper;
import utn.ddsG8.impacto_ambiental.domain.helpers.UserHelper;

public class AuthMiddleware {
    public static Response authenticateSession(Request request, Response response) {
        if (request.session().isNew() || request.session().attribute("id") == null) {
            response.redirect("/login");
        }
        return response;
    }

    public static Response authenticateId(Request request, Response response) {

        if (!UserHelper.isAdmin(request) && UserHelper.loggedUser(request).getId() != Integer.parseInt(request.params("id"))) {
            response.redirect("/prohibido");
            Spark.halt();
        }

        return response;
    }

    public static Response isAdmin(Request request, Response response) {
        if (request.session().attribute("id") == null) response.redirect("/login");
        if (!UserHelper.isAdmin(request)) {
            response.redirect("/prohibido");
        }
        return response;
    }

    public static Response isMiembro(Request request, Response response) {
        if (!UserHelper.loggedUser(request).getRole().getName().equals("miembro")) {
            response.redirect("/prohibido");
        }
        return response;
    }

    //todo
    public static Response trayectoEsDeMiembro(Request request, Response response) {
        return response;
    }
}
