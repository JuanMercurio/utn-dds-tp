package utn.ddsG8.impacto_ambiental.contrasenia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.contrasenia.PassValidatorList;

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


}
