package utn.ddsG8.impacto_ambiental.domain.calculos;


import lombok.Getter;
import utn.ddsG8.impacto_ambiental.domain.estructura.*;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Tramo;
import utn.ddsG8.impacto_ambiental.domain.movilidad.Trayecto;
import utn.ddsG8.impacto_ambiental.domain.services.distancia.SectorTerritorial;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

// singleton Class

public class CalcularHC {

    private static CalcularHC instancia = null;
    private double K;

    @Getter
    private Repositorio<Direccion> repoDireccion = FactoryRepositorio.get(Direccion.class);
    private Repositorio<Trayecto> repoTrayecto = FactoryRepositorio.get(Trayecto.class);
    private Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);
    private Repositorio<FE> factoresDeEmision = FactoryRepositorio.get(FE.class);

    public CalcularHC() {
        this.K = 2;
    }

    public static CalcularHC getInstancia() {
        if (instancia == null) {
            instancia = new CalcularHC();
        }
        return instancia;
    }

    public void cargarFactorEmision (FE fe){
        this.factoresDeEmision.agregar(fe);
    }

    public void modificarFE (String actividad, String tipoConsumo, double valorEmision){
        for (FE fe: factoresDeEmision.buscarTodos()) {
            if(fe.getNombre() == actividad && fe.getTipo() == tipoConsumo){
                fe.setValor(valorEmision);
            }
        }
    }

    // si lo encuentra lo devuelve. si no devuelve -1.
    public double buscarFactorEmision ( String actividad, String tipoConsumo){
        for (FE fe: factoresDeEmision.buscarTodos()) {
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
                        logistica.setMedioTransporte(med.getValor().toLowerCase());
                        cont++;
                    }
                    else if(med.getTipoConsumo().contains("Producto Transportado")){
                        //System.out.println("ENTRO 5");
                        logistica.setProductoTransportado(med.getValor().toLowerCase());
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
//        return factoresDeEmision.buscarTodos().stream().filter(f ->  f.getNombre().equals(nombre)).collect(Collectors.toList()).get(0).getValor();
        for (FE fe : factoresDeEmision.buscarTodos()) {
            if (fe.getNombre().equals(nombre)) {
                return fe.getValor();
            }
        }

        return -1;
    }


    public double obtenerHCOrganizacion(Organizacion org) {
        double a = obtenerHCTrayectosOrganizacion(org);
        double b = CalcularFEActividadesTOTAL(org.getMediciones());
        double c  = a + b;
        return c;
    }

    public double obtenerHCSector(Sector sector) {
        return sector.getTrayectos().stream().mapToDouble(t -> t.calcularHC()).sum();
    }

    public double obtenerHCTrayectosOrganizacion(Organizacion organizacion) {
        return  organizacion.getTrayectos().stream().mapToDouble(t -> t.calcularHC()).sum();
    }

    public double obtenerHCMiembroDeOrg(Miembro miembro, Organizacion organizacion) {
        return miembro.getTrayectos().stream().filter(t -> t.formaParte(organizacion)).mapToDouble(t -> obtenerHCTrayecto(t)).sum();
    }

    public double obtenerHCMiembro(Miembro miembro) {
        return miembro.getTrayectos().stream().mapToDouble(t -> obtenerHCTrayecto(t)).sum();
    }

    public double obtenerHCTrayecto(Trayecto trayecto) {
        return trayecto.calcularHC();
    }


    public double obtenerHCTramo(Tramo tramo) {
        return tramo.calcularHC();
    }

    public double obtenerHCSectorTerritorial(SectorTerritorial sectorTerritorial) {
        List<Organizacion> listOrganizacion = repoOrganizacion.buscarTodos().stream().filter(o -> o.perteneceASector(sectorTerritorial)).collect(Collectors.toList());
        return listOrganizacion.stream().mapToDouble(o -> o.calcularHC()).sum();
    }

    public double obtenerHCTipoDeOrg(Clasificacion clasificacion) {
        List<Organizacion> orgs = repoOrganizacion.buscarTodos().stream().filter(o -> o.getClasificacion().equals(clasificacion)).collect(Collectors.toList());
        return orgs.stream().mapToDouble(o -> o.calcularHC()).sum();
    }

    public double obtenerPorcentajeHCdeOrgEnSectorTerritorial(Organizacion org, SectorTerritorial sec) {
        return (obtenerHCOrganizacion(org) / obtenerHCSectorTerritorial(sec)) * 100;
    }

    public double porcentajeHCSectorTerritorialEnPais(SectorTerritorial sec) {
        return (obtenerHCSectorTerritorial(sec) / obtenerHCPais()) * 100;
    }

    public double obtenerHCPais() {
        return repoOrganizacion.buscarTodos().stream().mapToDouble(o -> o.calcularHC()).sum();
    }

    public double pocentajeHCSectorEnOrg(Sector sec) {
        return (obtenerHCSector(sec) / obtenerHCTrayectosOrganizacion(sec.getOrganizacion())) * 100;
    }

    public double obtenerHC(SectorTerritorial sectorTerritorial, Clasificacion clasificacion) {
        List<Organizacion> orgs = repoOrganizacion.buscarTodos().stream().filter(o -> o.perteneceASector(sectorTerritorial) && o.getClasificacion() == clasificacion).collect(Collectors.toList());
        return orgs.stream().mapToDouble(o -> o.calcularHC()).sum();
    }
}
