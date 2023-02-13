package utn.ddsG8.impacto_ambiental.CalculoHC;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.calculos.Medicion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.services.sheets.LectorExcel;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.io.IOException;
import java.util.List;

public class CalcularHuellaCarbono {

    private final Repositorio<FE> repoFE = FactoryRepositorio.get(FE.class);
    private final Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);

    @Test
    public void crearFeYBuscar () throws IOException {
        CalcularHC calcular = new CalcularHC();
        FE fe = new FE("Combustion fija","Gas Natural","m3",2.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Diesel","lt",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Gasoil ","lt",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Kerosene","lt",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Fuel Oil","lt",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Nafta","lt",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Carbon","kg",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Carbon de leña","kg",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustion fija","Lena","kg",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustión móvil","Combustible consumido - Gasoil","lts",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustión móvil","Combustible consumido – GNC","lts",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Combustión móvil","Combustible consumido - Nafta","lts",3.44);
        calcular.cargarFactorEmision(fe);

        fe = new FE("Electricidad adquirida y consumida","Electricidad","kwh",3.44);
        calcular.cargarFactorEmision(fe);
        fe = new FE("Logística de productos y residuos","","",3.44);
        calcular.cargarFactorEmision(fe);



        fe = new FE("Camion","","",3.44);
        calcular.cargarFactorEmision(fe);

        fe = new FE("Auto","","",3.44);
        calcular.cargarFactorEmision(fe);

        fe = new FE("Moto","","",3.44);
        calcular.cargarFactorEmision(fe);

        fe = new FE("Colectivo","","",3.44);
        calcular.cargarFactorEmision(fe);

        Assertions.assertTrue( calcular.buscarFactorEmision("Combustion fija","Gas Natural") != -1);
        LectorExcel lector = new LectorExcel();
        List<Medicion> mediciones = lector.obtenerDatosActividades("src/main/resources/ejemploDA.xlsx");
        double hc = calcular.CalcularFEActividades(mediciones);
        Assertions.assertTrue(hc != 0);
        System.out.println(hc);
    }

    @Test
    // TODO
    public void probarHuellaOrg() {
        Organizacion org = repoOrganizacion.buscar(3);
        System.out.println(CalcularHC.getInstancia().CalcularFEActividadesTOTAL(org.getMediciones()));
        System.out.println(CalcularHC.getInstancia().obtenerHCOrganizacion(org));
    }
}
