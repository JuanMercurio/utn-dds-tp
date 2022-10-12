package utn.ddsG8.impacto_ambiental.middleware;


import spark.Request;
import spark.Response;

public class AuthMiddleware {
    public static Response authenticateSession(Request request, Response response) {
        if (request.session().isNew() || request.session().attribute("id") == null) {
            response.redirect("/login");
        }
        return response;
    }
}
