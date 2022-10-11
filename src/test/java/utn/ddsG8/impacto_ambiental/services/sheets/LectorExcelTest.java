package utn.ddsG8.impacto_ambiental.services.sheets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.Medicion;
import utn.ddsG8.impacto_ambiental.domain.services.sheets.LectorExcel;

import java.io.IOException;
import java.util.List;

// TODO: eliminar prints de estos tests despues de mostarle a pablo
public class LectorExcelTest {

    @Test
    public void obtieneDatos() throws IOException {
        LectorExcel lector = new LectorExcel();
        List<Medicion> mediciones = lector.obtenerDatosActividades("src/main/resources/ejemploDA.xlsx");
        Assertions.assertTrue(mediciones.size() > 0);
    }

    @Test
    public void imprimeDatos() throws IOException {
        LectorExcel lector = new LectorExcel();
        System.out.println();
        System.out.println("Imprime archivo leido");
        System.out.println();
        System.out.format("%-45s%-35s%-15s%-15s%-10s\n", "ACTIVIDAD", "TIPO DE CONSUMO", "VALOR", "PERIOCIDAD", "PERIODO DE IMPUTACION");
        List<Medicion> mediciones = lector.obtenerDatosActividades("src/main/resources/ejemploDA.xlsx");
        mediciones.forEach(m -> imprimirRegistro(m));
        System.out.println();
    }

    public void imprimirRegistro(Medicion medicion) {
        System.out.format("%-45s%-35s%-15s%-15s%-10s\n",
                medicion.getActividad(),
                medicion.getTipoConsumo(),
                medicion.getValor(),
                medicion.getPeriocidad(),
                medicion.getPeriodoDeImputacion());
    }

}
