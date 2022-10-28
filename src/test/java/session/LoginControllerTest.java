package session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.sessions.User;

public class LoginControllerTest {
    @Test
    public void devuelveUnAdmin() {
        String query = "from "
                + User.class.getName()
                + " WHERE username = '"
                + "admin"
                + "' AND password = '"
                + PassHasher.SHA_256("admin")
                + "'";

        User user =  (User) EntityManagerHelper.getEntityManager().createQuery(query).getResultList().get(0);
        Assertions.assertTrue(user.isAdmin());
    }

    //@Test
    public void hasher() {
        for(int i=0; i<10; i++) {
            System.out.println(PassHasher.SHA_256("Juan12345"));
        }

    }

}
