package utn.ddsG8.impacto_ambiental.spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImpactoMiembroSobreOrg implements Helper<Organizacion> {
    @Override
    public Object apply(Organizacion organizacion, Options options) throws IOException {
        Miembro miembro = options.param(0);
        double valor = CalcularHC.getInstancia().obtenerHCMiembroDeOrg(miembro, organizacion);
        return new BigDecimal(valor).setScale(2, RoundingMode.CEILING);
    }
}
