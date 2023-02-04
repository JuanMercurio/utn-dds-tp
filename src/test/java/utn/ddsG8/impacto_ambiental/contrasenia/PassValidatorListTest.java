package utn.ddsG8.impacto_ambiental.contrasenia;

import org.apache.poi.xssf.usermodel.helpers.XSSFPasswordHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.db.EntityManagerHelper;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassHasher;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassValidatorList;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;
import utn.ddsG8.impacto_ambiental.sessions.User;

import java.io.FileNotFoundException;

class PassValidatorListTest {

    @Test
    public void listTest() throws FileNotFoundException {
        String contrasenias_path = "src/main/resources/badPasswords.txt";
        PassValidatorList list = new PassValidatorList(contrasenias_path,
                8, 1, 1, 1);

        Assertions.assertTrue(list.validarPass("Bien1234"));

        Assertions.assertFalse(list.validarPass("Password1"));
        Assertions.assertFalse(list.validarPass("malacontrasenia1234"));

        Assertions.assertFalse(list.validarPass("11111111"));
        Assertions.assertFalse(list.validarPass("aaaaaaaa"));
        Assertions.assertFalse(list.validarPass("AAAAAAAA"));
    }

    @Test
    public void obtenersha() {
        System.out.println(PassHasher.SHA_256("miembro"));
    }


}
