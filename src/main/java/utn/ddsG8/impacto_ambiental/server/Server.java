package utn.ddsG8.impacto_ambiental.server;

import spark.Spark;
import spark.debug.DebugScreen;
import utn.ddsG8.impacto_ambiental.domain.Notificaciones.Notificador;

public class Server {
	public static void main(String[] args) {
		Spark.port(9078);
		Router.init();
		DebugScreen.enableDebugScreen();
		Notificador.iniciarNotificador();
	}
}
