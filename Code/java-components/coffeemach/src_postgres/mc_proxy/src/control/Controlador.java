package control;

import java.util.Observer;
import java.util.Scanner;

import org.osoa.sca.annotations.Reference;

import servicios.ServicioProxyCoffeMachine;
import servicios.ServicioProxyServer;

public class Controlador extends Sujeto implements Runnable,
		ServicioProxyCoffeMachine, Observador {

	Scanner lector = new Scanner(System.in);
	String[] recetas;

	@Reference
	private static ServicioProxyServer servicioProxyServer;

	// Nuevo
	public static void setServicioProxyServer(
			ServicioProxyServer servicioProxyServer) {
		Controlador.servicioProxyServer = servicioProxyServer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		recetas = null;

		while (true) {

			System.out.println("Digite 1 para actualizar");
			int opcion = lector.nextInt();
			if (opcion == 1) {
				recetas = servicioProxyServer.update();

				for (int i = 0; i < recetas.length; i++) {
					System.out.println(recetas[i]);
				}

			}

		}

	}

	// El controlador es sujeto de las máquinas de café
	@Override
	public void attach(Observer o) {
		// TODO Auto-generated method stub
		super.attach(o);
	}

	@Override
	public void detach(Observer o) {
		// TODO Auto-generated method stub
		super.detach(o);
	}

	@Override
	public String[] notificar() {
		// TODO Auto-generated method stub

		recetas = servicioProxyServer.update();

		if (!recetas.equals(null)) {

			return recetas;
		}

		return null;
	}

}
