package utn.ddsG8.impacto_ambiental.domain.calculos;

public class Logistica extends Medicion{

        private double distancia;
        private double pesoTotal;
        private String medioTransporte;
        private String productoTransportado;
        //private Transporte transporte;

       /* public CalcularHC (){

        }*/


        public double getDistancia() {
                return distancia;
        }

        public void setDistancia(double distancia) {
                this.distancia = distancia;
        }

        public double getPesoTotal() {
                return pesoTotal;
        }

        public void setPesoTotal(double pesoTotal) {
                this.pesoTotal = pesoTotal;
        }

        public String getMedioTransporte() {
                return medioTransporte;
        }

        public void setMedioTransporte(String medioTransporte) {
                this.medioTransporte = medioTransporte;
        }

        public String getProductoTransportado() {
                return productoTransportado;
        }

        public void setProductoTransportado(String productoTransportado) {
                this.productoTransportado = productoTransportado;
        }
}
