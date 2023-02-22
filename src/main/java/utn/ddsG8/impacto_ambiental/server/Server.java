package utn.ddsG8.impacto_ambiental.server;

import spark.Spark;
import spark.debug.DebugScreen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Server {
	public static void main(String[] args) {
		Spark.port(9078);
		Router.init();
		DebugScreen.enableDebugScreen();
	}
}
