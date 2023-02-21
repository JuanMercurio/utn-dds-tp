package utn.ddsG8.impacto_ambiental.spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcularHCOrg implements Helper<Organizacion> {
    @Override
    public Object apply(Organizacion org, Options options) throws IOException {
        return new BigDecimal(CalcularHC.getInstancia().obtenerHCOrganizacion(org)).setScale(2, RoundingMode.CEILING);
    }
}
