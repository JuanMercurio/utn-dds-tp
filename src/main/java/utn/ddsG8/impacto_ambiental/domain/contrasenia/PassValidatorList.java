package utn.ddsG8.impacto_ambiental.domain.contrasenia;

import java.io.FileNotFoundException;
import java.util.*;

public class PassValidatorList extends PassValidator {

    public PassValidatorList(String archivoContrasenias, Integer cantMinCaracteres, Integer cantMinLower, Integer cantMinNum, Integer cantMinUpper)   {
        super(archivoContrasenias, cantMinCaracteres, cantMinLower, cantMinNum, cantMinUpper);
    }

    @Override
    public boolean cumpleRestricciones(String pass){
        List<String> passList = Arrays.asList((pass.split("")));
        int uppers  = (int) passList.stream().filter((String s) -> Character.isUpperCase(s.charAt(0))).count();
        int lowers  = (int) passList.stream().filter((String s) -> Character.isLowerCase(s.charAt(0))).count();
        int numeros = (int) passList.stream().filter((String s) -> Character.isDigit(s.charAt(0))).count();

        boolean cumpleLength    =  pass.length() >= this.cantMinCaracteres ;
        boolean cumpleCantUpper = uppers >= this.cantMinUpper;
        boolean cumpleCantLower = lowers >= this.cantMinLower;
        boolean cumpleCantNum   = numeros >= this.cantMinNum;
        return cumpleLength && cumpleCantLower && cumpleCantNum && cumpleCantUpper;
    }

}