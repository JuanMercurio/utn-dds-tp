package utn.ddsG8.impacto_ambiental.arq;

import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

public class UserTests {
    @Test
    public void changePassword() {
        Repositorio<User> users = FactoryRepositorio.get(User.class);
        User juan  = users.buscar(1);
        juan.setPassword(PassHasher.SHA_256("juan"));
        users.modificar(juan);
    }
}
