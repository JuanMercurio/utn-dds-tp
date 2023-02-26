package utn.ddsG8.impacto_ambiental.domain.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

public class UserHelper {

    private static Repositorio<User> users = FactoryRepositorio.get(User.class);

    public static User loggedUser(Request request) {
        return EntityManagerHelper
                .getEntityManager()
                .find(User.class, request.session().attribute("id"));
    }

    public static boolean usernameExists(String username) {
        return users.query("from User where username = '" + username +  "'").size() > 0;
    }

    public static boolean isAdmin(Request request){
        return loggedUser(request).isAdmin();
    }
}
