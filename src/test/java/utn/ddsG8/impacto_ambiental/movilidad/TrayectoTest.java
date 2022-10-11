package utn.ddsG8.impacto_ambiental.movilidad;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utn.ddsG8.impacto_ambiental.domain.calculos.CalcularHC;
import utn.ddsG8.impacto_ambiental.domain.calculos.FE;
import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.TramoTransportePrivado;
import utn.ddsG8.impacto_ambiental.domain.movilidad.TramoTransportePublico;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Auto;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.Pie;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Parada;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.TransportePublico;
import utn.ddsG8.impacto_ambiental.domain.movilidad.transportes.publico.Tren;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Distancia;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.Localidad;


class TrayectoTest {
    private static final String token = "Bearer E8iN6xBPXQsUI+M72MfPdVhM/o3axkzywqKZjjOyhe0=";
    private static final Localidad localidad = new Localidad(3908, "ALDEA BRASILERA", 3101, null);
    private static final Direccion inicio = new Direccion("Grupo8", 100, localidad);
    private static final Direccion fin = new Direccion("No llegamos con la entrega", 100, localidad);
    private static Tramo tramoAuto;
    private static Tramo tramoPie;
    private static TransportePublico transportePublico;
    private static Tramo tramoTren1;
    private static Tramo tramoTren2;

    @BeforeAll
    public static void iniciarTramos() {
        Auto auto = new Auto(null);
        Pie pie = new Pie();
        transportePublico = crearTren();
        tramoAuto = new TramoTransportePrivado(auto, inicio, fin, token);
        tramoPie = new TramoTransportePrivado(pie, inicio, fin, token);
        tramoTren1 = new TramoTransportePublico(transportePublico, transportePublico.getParadas().get(2), transportePublico.getParadas().get(4));
        tramoTren2 = new TramoTransportePublico(transportePublico, transportePublico.getParadas().get(6), transportePublico.getParadas().get(7));



    }
    public static CalcularHC CrearFes(){
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
        fe = new FE("Pie","","",0);
        calcular.cargarFactorEmision(fe);
        return calcular;
    }

    @Test
    public void autoYPieTest() {
        Trayecto trayecto = new Trayecto();
        trayecto.agregarTramos(tramoAuto, tramoPie, tramoAuto, tramoPie);
        Distancia distancia = trayecto.getDistancia();
        Assertions.assertTrue(distancia.valor > 0);
    }

    @Test
    public void distanciaDeTramoSegunIndiceTest() {
        Trayecto trayecto = new Trayecto();
        trayecto.agregarTramos(tramoAuto, tramoPie, tramoAuto, tramoPie);
        Distancia distancia = trayecto.getDistanciaDeTramo(2);
        Assertions.assertTrue(distancia.valor > 0);
    }

    @Test
    public void distanciaDeTrayectoPrivadoYPublico() {
        Trayecto trayecto = new Trayecto();
        trayecto.agregarTramos(tramoTren1, tramoAuto, tramoTren2, tramoAuto);
        double hc = trayecto.CalcularHCTrayecto();
                Assertions.assertTrue(trayecto.getDistancia().valor > 0);
        Assertions.assertTrue(hc > 0);
        System.out.println(hc);
    }

    private static TransportePublico crearTren() {
        TransportePublico tren = new Tren("Aguante Pablo");
        for (int i = 0; i <10; i++) {
            Parada parada = new Parada("parada "+i, null);
            tren.agregarParada(parada,1,2,i);
        }
        return tren;
    }
}