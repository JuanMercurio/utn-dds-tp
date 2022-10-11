package utn.ddsG8.impacto_ambiental.movilidad;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.TramoTransportePrivado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.TramoTransportePublico;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Moto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Pie;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Combustible;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.combustibles.Nafta;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Auto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Tren;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;

class TramoTest {
    private final String token = "Bearer E8iN6xBPXQsUI+M72MfPdVhM/o3axkzywqKZjjOyhe0=";
    private final Localidad localidad = new Localidad(3908, "ALDEA BRASILERA", 3101, null);
    private final Direccion inicioDireccion = new Direccion("Grupo8", 100, localidad);
    private final Direccion finDireccion = new Direccion("No llegamos con la entrega", 100, localidad);

    @Test
    public void autoDistancia() {
        Combustible combustible = new Nafta();
        Auto auto = new Auto(combustible);
        Tramo tramo = new TramoTransportePrivado(auto, inicioDireccion, finDireccion, token);
        Distancia distanciaTramo= tramo.getDistancia();
//        System.out.println(distanciaTramo.valor + distanciaTramo.unidad);
        Assertions.assertTrue(distanciaTramo.valor > 0);
    }
    @Test
    public void autoHuellaCarbono(){
        Combustible combustible = new Nafta();
        Auto auto = new Auto(combustible);
        Tramo tramo = new TramoTransportePrivado(auto, inicioDireccion, finDireccion, token);
        FE fe;

        fe = new FE("Camion","","",3);
        CalcularHC.getInstancia().cargarFactorEmision(fe);
        fe = new FE("Auto","","",3);
        CalcularHC.getInstancia().cargarFactorEmision(fe);
        fe = new FE("Tren","","",3);
        CalcularHC.getInstancia().cargarFactorEmision(fe);
        fe = new FE("Colectivo","","",3);
        CalcularHC.getInstancia().cargarFactorEmision(fe);

        System.out.println(tramo.calcularHC());
        double hc = auto.calcularHC(tramo.getDistancia().valor);
        System.out.println(hc);
        Assertions.assertTrue(hc> 0);


    }

    @Test
    public void motoDistancia() {
        Combustible combustible = new Nafta();
        Moto moto = new Moto(combustible);
        Tramo tramo = new TramoTransportePrivado(moto, inicioDireccion, finDireccion, token);
        Distancia distanciaTramo= tramo.getDistancia();
        Assertions.assertTrue(distanciaTramo.valor > 0);
    }

    @Test
    public void pieDistancia() {
        Pie pie = new Pie();
        Tramo tramo = new TramoTransportePrivado(pie, inicioDireccion, finDireccion, token);
        Distancia distanciaTramo= tramo.getDistancia();
        Assertions.assertTrue(distanciaTramo.valor > 0);
    }

    @Test
    public void TranportePublicoDistancia() {
        TransportePublico tren = crearTren();
        Tramo tramo = new TramoTransportePublico(tren, tren.getParadas().get(0), tren.getParadas().get(5));
        Distancia distanciaTramo= tramo.getDistancia();
        Assertions.assertTrue(distanciaTramo.valor > 0);
    }

    @Test
    public void TrenHuellaCarbono()
    {
        TransportePublico tren = crearTren();
        Tramo tramo = new TramoTransportePublico(tren, tren.getParadas().get(0), tren.getParadas().get(5));

        FE fe = new FE("Tren","","",3.99);
        CalcularHC.getInstancia().cargarFactorEmision(fe);

        System.out.println(tramo.calcularHC());

        double hc = tren.calcularHC(tramo.getDistancia().valor);
        System.out.println(hc);
        Assertions.assertTrue(hc> 0);
    }

    private TransportePublico crearTren() {
        TransportePublico tren = new Tren("Subte 123");
        for (int i = 0; i <10; i++) {
            Parada parada = new Parada("parada "+i, null);
            tren.agregarParada(parada,1,2,i);
        }
        return tren;
    }


}