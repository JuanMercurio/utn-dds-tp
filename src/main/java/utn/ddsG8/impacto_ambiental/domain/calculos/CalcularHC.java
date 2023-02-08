package utn.ddsG8.impacto_ambiental.domain.calculos;


import utn.ddsG8.impacto_ambiental.domain.estructura.Direccion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Miembro;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.domain.estructura.Sector;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// SINGLETON

public class CalcularHC {
    private static CalcularHC instancia = null;
    private double K;
    private List<FE> factoresDeEmision;

    private Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);

    public CalcularHC() {
    factoresDeEmision = new ArrayList<>();
        this.K = 2;
    }

    public static CalcularHC getInstancia() {
        if (instancia == null) {
            instancia = new CalcularHC();
        }
        return instancia;
    }


    // TODO: CALCULAR HUELAL DE CARBONO DEL EXCEL
    // TODO: CALCULAR HUELLAD E CARBONO DE LOGISTICA.
    // DE DONDE SALE EL fe?
    public void cargarFactorEmision (FE fe){
        this.factoresDeEmision.add(fe);

    }

    public void modificarFE (String actividad, String tipoConsumo, double valorEmision){
        for (FE fe: factoresDeEmision) {
            if(fe.getNombre() == actividad && fe.getTipo() == tipoConsumo){
                fe.setValor(valorEmision);
            }
        }

    }
    // si lo encuentra lo devuelve. si no devuelve -1.
    public double buscarFactorEmision ( String actividad, String tipoConsumo){
        for (FE fe: factoresDeEmision) {
            if(fe.getNombre().contains(actividad) && fe.getTipo().contains(tipoConsumo)){
                return fe.getValor();
            }
        }
        return -1;
    }


    public double CalcularFEActividades (List<Medicion> mediciones){
        double medicionAcum = 0;
            double fe;
        Boolean comenzoLogistica = true;
        Logistica logistica = new Logistica();
        int cont = 0;
        for (Medicion med: mediciones) {
            if( med.getActividad().contains("Logística")){
                //System.out.println("ENTRO 1");
                if(!comenzoLogistica ){
                    comenzoLogistica = true;
                    logistica = new Logistica();
                }

                if (med.getTipoConsumo().contains("Distancia")){
                    //System.out.println("ENTRO 2");
                    //System.out.println("Distancia: "+med.getValorD());
                    logistica.setDistancia(med.getValorD());
                    cont++;
                }
                else if(med.getTipoConsumo().contains("Peso")){
                    //System.out.println("ENTRO 3");
                    //System.out.println("Peso: "+med.getValorD());
                    logistica.setPesoTotal(med.getValorD());
                    cont++;
                }
                else if(med.getTipoConsumo().contains("Medio Transporte")){
                    //System.out.println("ENTRO 4");
                    logistica.setMedioTransporte(med.getValor());
                    cont++;
                }
                else if(med.getTipoConsumo().contains("Producto Transportado")){
                    //System.out.println("ENTRO 5");
                    logistica.setProductoTransportado(med.getValor());
                    cont++;
                }
                if( cont == 4){
                    comenzoLogistica = false;
                    cont = 0;

                    fe = buscarFactorEmisionTransporte(logistica.getMedioTransporte());
                    if( fe != -1 ) {
                       // System.out.println("ENTRO");
                        //System.out.println(fe*logistica.getDistancia()*logistica.getPesoTotal()*K);
                        medicionAcum += fe*logistica.getDistancia()*logistica.getPesoTotal()*K;
                    }
                }
            }
            else{
                //System.out.println(calcularFeMedicion(med));
                medicionAcum += calcularFeMedicion(med);
            }

        }
        return medicionAcum;
    }
    //public void ExisteLogistica()
    public double calcularFeMedicion (Medicion med){
        Double fe;
        fe = buscarFactorEmision(med.getActividad(),med.getTipoConsumo());
        if( fe != -1 ) {
            return fe * med.getValorD();
        }
        return 0;
    }
    public double CalcularFEActividadesMensual (List<Medicion> mediciones, int mes, int anio){
       //todo HACER HOY MENSUAL Y ANUAL
        double medicionAcum = 0;
        double fe;
        Boolean comenzoLogistica = true;
        Logistica logistica = new Logistica();
        int cont = 0;
        for (Medicion med: mediciones) {
            if( med.getMes() == mes && med.getAnio() == anio){
                if( med.getActividad().contains("Logística")){
                    //System.out.println("ENTRO 1");
                    if(!comenzoLogistica ){
                        comenzoLogistica = true;
                        logistica = new Logistica();
                    }

                    if (med.getTipoConsumo().contains("Distancia")){
                        //System.out.println("ENTRO 2");
                        //System.out.println("Distancia: "+med.getValorD());
                        logistica.setDistancia(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Peso")){
                        //System.out.println("ENTRO 3");
                        //System.out.println("Peso: "+med.getValorD());
                        logistica.setPesoTotal(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Medio Transporte")){
                        //System.out.println("ENTRO 4");
                        logistica.setMedioTransporte(med.getValor());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Producto Transportado")){
                        //System.out.println("ENTRO 5");
                        logistica.setProductoTransportado(med.getValor());
                        cont++;
                    }
                    if( cont == 4){
                        comenzoLogistica = false;
                        cont = 0;

                        fe = buscarFactorEmisionTransporte(logistica.getMedioTransporte());
                        if( fe != -1 ) {
                            // System.out.println("ENTRO");
                            //System.out.println(fe*logistica.getDistancia()*logistica.getPesoTotal()*K);
                            medicionAcum += fe*logistica.getDistancia()*logistica.getPesoTotal()*K;
                        }
                    }
                }
                else{
                    //System.out.println(calcularFeMedicion(med));
                    medicionAcum += calcularFeMedicion(med);
                }
            }

        }
        return medicionAcum;


    }
    public double CalcularFEActividadesAnual(List<Medicion> mediciones,  int anio){

        double medicionAcum = 0;
        double fe;
        Boolean comenzoLogistica = true;
        Logistica logistica = new Logistica();
        int cont = 0;
        for (Medicion med: mediciones) {
            if(med.getAnio() == anio){
                if( med.getActividad().contains("Logística")){
                    //System.out.println("ENTRO 1");
                    if(!comenzoLogistica ){
                        comenzoLogistica = true;
                        logistica = new Logistica();
                    }

                    if (med.getTipoConsumo().contains("Distancia")){
                        //System.out.println("ENTRO 2");
                        //System.out.println("Distancia: "+med.getValorD());
                        logistica.setDistancia(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Peso")){
                        //System.out.println("ENTRO 3");
                        //System.out.println("Peso: "+med.getValorD());
                        logistica.setPesoTotal(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Medio Transporte")){
                        //System.out.println("ENTRO 4");
                        logistica.setMedioTransporte(med.getValor());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Producto Transportado")){
                        //System.out.println("ENTRO 5");
                        logistica.setProductoTransportado(med.getValor());
                        cont++;
                    }
                    if( cont == 4){
                        comenzoLogistica = false;
                        cont = 0;

                        fe = buscarFactorEmisionTransporte(logistica.getMedioTransporte());
                        if( fe != -1 ) {
                            // System.out.println("ENTRO");
                            //System.out.println(fe*logistica.getDistancia()*logistica.getPesoTotal()*K);
                            medicionAcum += fe*logistica.getDistancia()*logistica.getPesoTotal()*K;
                        }
                    }
                }
                else{
                    //System.out.println(calcularFeMedicion(med));
                    medicionAcum += calcularFeMedicion(med);
                }
            }

        }
        return medicionAcum;


    }
    public double CalcularFEActividadesTOTAL(List<Medicion> mediciones){

        double medicionAcum = 0;
        double fe;
        Boolean comenzoLogistica = true;
        Logistica logistica = new Logistica();
        int cont = 0;
        for (Medicion med: mediciones) {

                if( med.getActividad().contains("Logística")){
                    //System.out.println("ENTRO 1");
                    if(!comenzoLogistica ){
                        comenzoLogistica = true;
                        logistica = new Logistica();
                    }

                    if (med.getTipoConsumo().contains("Distancia")){
                        //System.out.println("ENTRO 2");
                        //System.out.println("Distancia: "+med.getValorD());
                        logistica.setDistancia(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Peso")){
                        //System.out.println("ENTRO 3");
                        //System.out.println("Peso: "+med.getValorD());
                        logistica.setPesoTotal(med.getValorD());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Medio Transporte")){
                        //System.out.println("ENTRO 4");
                        logistica.setMedioTransporte(med.getValor());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Producto Transportado")){
                        //System.out.println("ENTRO 5");
                        logistica.setProductoTransportado(med.getValor());
                        cont++;
                    }
                    if( cont == 4){
                        comenzoLogistica = false;
                        cont = 0;

                        fe = buscarFactorEmisionTransporte(logistica.getMedioTransporte());
                        if( fe != -1 ) {
                            // System.out.println("ENTRO");
                            //System.out.println(fe*logistica.getDistancia()*logistica.getPesoTotal()*K);
                            medicionAcum += fe*logistica.getDistancia()*logistica.getPesoTotal()*K;
                        }
                    }
                }
                else{
                    //System.out.println(calcularFeMedicion(med));
                    medicionAcum += calcularFeMedicion(med);
                }


        }
        return medicionAcum;


    }
    public double buscarFactorEmisionTransporte(String nombre){
        for (FE act:factoresDeEmision){
            if(act.getNombre().contains(nombre)){
                return act.getValor();
            }
        }
        return 0.0;
    }

    public double obtenerHCOrganizacion(Organizacion org) {
        // todo ES LA SUMA DE LOS TRAYECTOS Y LOS DA
        return org.calcularHC();
    }

    public double obtenerHCSector(Sector sector) {
        return obtenerTrayectos(sector).stream().mapToDouble(t -> t.CalcularHCTrayecto()).sum();
    }

    public double obtenerHCTrayectosOrganizacion(Organizacion organizacion) {
        return  organizacion.getTrayectos().stream().mapToDouble(t -> t.CalcularHCTrayecto()).sum();
    }

    public List<Trayecto> obtenerTrayectos(Sector sector) {
        Set<Trayecto> trayectos = new HashSet<>();
        sector.getMiembros().forEach(m -> m.getTrayectos().forEach(t -> {
            if (t.getOrganizaciones().contains(sector.getOrganizacion())) trayectos.add(t);
        }));
        return trayectos.stream().collect(Collectors.toList());
    }



    public double obtenerHCMiembroDeOrg(Miembro miembro, Organizacion organizacion) {
        return miembro.getTrayectos().stream().filter(t -> t.formaParte(organizacion)).mapToDouble(t -> obtenerHCTrayecto(t)).sum();
    }

    public double obtenerHCMiembro(Miembro miembro) {
        return miembro.getTrayectos().stream().mapToDouble(t -> obtenerHCTrayecto(t)).sum();
    }

    public double obtenerHCTrayecto(Trayecto trayecto) {
        return trayecto.CalcularHCTrayecto();
    }


    public double obtenerHCTramo(Tramo tramo) {
        return tramo.calcularHC();
    }

    public double obtenerHCSectorTerritorial(SectorTerritorial sectorTerritorial) {
        List<Organizacion> listOrganizacion = repoOrganizacion.buscarTodos().stream().filter(o -> o.perteneceASector(sectorTerritorial)).collect(Collectors.toList());
        return listOrganizacion.stream().mapToDouble(o -> o.calcularHC()).sum();
    }

}
