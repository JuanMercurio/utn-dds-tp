package utn.ddsG8.impacto_ambiental.spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class IndicadorHCSector implements Helper<Sector> {
    @Override
    public Object apply(Sector sec, Options options) throws IOException {
        return new BigDecimal(sec.calcularHC()/sec.cantidadMiembros()).setScale(2, RoundingMode.CEILING);
    }
}
