package utn.ddsG8.impacto_ambiental.controllers;

import spark.Request;
import spark.Response;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassValidator;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassValidatorList;
import utn.ddsG8.impacto_ambiental.domain.helpers.RoleHelper;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

public class UserController {

    private static String archivoContrasenias = "src/main/resources/badPasswords.txt";
    private static Repositorio<User> usuarios = FactoryRepositorio.get(User.class);


    public static User create(Request request, Response response) {

        PassValidator passValidator = new PassValidatorList( archivoContrasenias, 8, 1, 1,1);
        if (!passValidator.validarPass(request.queryParams("password")) || usuarios.query("from User where username = '" + request.queryParams("username") + "'").size() > 0) {
            response.status(500); // se puede usar para alerts
            response.redirect(request.url());
            System.out.println("jejejej piifiado");
            return null;
        } else {
            User user = new User(request.queryParams("username"), request.queryParams("password"));
            usuarios.agregar(user);
            return user;
        }
    }

    public static User crearUsuario(Request request, Response response) {
        User user = UserController.create(request, response);
        if (user == null) return null;

        String role = request.url().substring(request.url().lastIndexOf("/") + 1).toLowerCase().replaceAll("create", "");
        user.setRole(RoleHelper.getRole(role));
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        users.modificar(user);
        response.redirect("/"+ user.getRole().getName() +"/" + user.getId());

        return user;
    }
}
