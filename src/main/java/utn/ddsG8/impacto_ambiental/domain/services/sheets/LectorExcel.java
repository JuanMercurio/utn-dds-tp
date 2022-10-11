package utn.ddsG8.impacto_ambiental.domain.services.sheets;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utn.ddsG8.impacto_ambiental.domain.calculos.Medicion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LectorExcel {

    public List<Medicion> obtenerDatosActividades(String path) throws IOException {

        List<Medicion> mediciones = new ArrayList<Medicion>();
        FileInputStream file = new FileInputStream(new File(path));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        rowIterator.next();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            Medicion medicion = new Medicion();
            medicion.setActividad(cellIterator.next().getStringCellValue());
            //TODO: HACER LA MEDICION DE LOGISTICA.
            medicion.setTipoConsumo(cellIterator.next().getStringCellValue());
            medicion.setValor(getValor(cellIterator.next()));
            medicion.setPeriocidad(cellIterator.next().getStringCellValue());
            medicion.setPeriodoDeImputacion(getValor(cellIterator.next()));

            mediciones.add(medicion);
        }
            file.close();

        return mediciones;
    }

    // solo verifica si es numerico, de ser asi lo transforma en un string.
    // verificar si tiene que leer otro tipo de celdas
    private String getValor(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC ? Double.toString(cell.getNumericCellValue()) : cell.getStringCellValue();
    }


}
