package utn.ddsG8.impacto_ambiental.helpers;

import spark.Request;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.sessions.User;

public class UserHelper {
    public static User loggedUser(Request request) {
        return EntityManagerHelper
                .getEntityManager()
                .find(User.class, request.session().attribute("id"));
    }
}
