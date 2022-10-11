package utn.ddsG8.impacto_ambiental.domain.contrasenia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class PassValidator{
    protected Integer cantMinCaracteres;
    protected Integer cantMinUpper;
    protected Integer cantMinLower;
    protected Integer cantMinNum;
    protected List<String> malasContrasenias;

    public PassValidator(String archivoContrasenias, Integer cantMinCaracteres,
                         Integer cantMinLower, Integer cantMinNum, Integer cantMinUpper)
                         throws FileNotFoundException {

        crearListaPasswords(archivoContrasenias);
        this.cantMinCaracteres = cantMinCaracteres;
        this.cantMinLower = cantMinLower;
        this.cantMinNum = cantMinNum;
        this.cantMinUpper = cantMinUpper;
    }

    private void crearListaPasswords(String path) throws FileNotFoundException {
        this.malasContrasenias = new ArrayList<>();

        File myFile = new File(path) ;
        Scanner myReader = new Scanner(myFile);

        while(myReader.hasNextLine()){
            String pass = myReader.nextLine();
            this.malasContrasenias.add(pass);
        }

        myReader.close();

        this.malasContrasenias.sort((s1, s2) -> s1.compareTo(s2));
    }

    public boolean validarPass(String pass){
        boolean esComun = Arrays.binarySearch(this.malasContrasenias.toArray(), pass) > -1;
        return cumpleRestricciones(pass) && !esComun;
    }

    public abstract boolean cumpleRestricciones(String s);
}
