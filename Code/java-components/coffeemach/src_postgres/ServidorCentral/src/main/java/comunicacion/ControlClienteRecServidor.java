package comunicacion;

import java.util.Date;
import java.util.List;

import ServerControl.*;
import servicios.*;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

public class ControlClienteRecServidor implements ServicioClienteRecServidor {

	private ServerControl control;

	public ControlClienteRecServidor(ServerControl con) {
		control = con;
	}

	/*
	 * ------------------------------------------------------------ Servicios
	 * llamados por el nodo de logística
	 */
	public boolean asignarOperador(int idOperador, int idMaquina, Current current) {

		return control.asignarOperador(idMaquina, idOperador);
	}

	// Funciona correctamente
	
	/*
	 * --------------------------------------------------------------------------
	 * ---------- Servicios llamados por la maquina de Café
	 */

	// Guarda correctamente la alarma en la BD y genera el mensaje que será
	// enviado a logística
	

	@Override
	public String[] consultarIngredientes(Current current) {

		return control.consultarIngredientes();
	}

	@Override
	public String[] consultarRecetas(Current current) {

		return control.consultarRecetas();
	}

	@Override
	public void borrarReceta(int cod, Current current) {

		control.borrarReceta(cod);
	}

	@Override
	public void registrarRecetaIngrediente(int idReceta, int idIngrediente,
			int valor, Current current) {
		control.registrarRecetaIngrediente(idReceta, idIngrediente, valor);
	}

	@Override
	public String registrarReceta(String nombre, int precio, Current current) {

		return control.registrarReceta(nombre, precio);
	}

	@Override
	public String registrarIngrediente(String nombre, Current current) {

		return control.registrarIngrediente(nombre);
	}

	//
	// public void setServicioComMQCafeSalida(ServicioComMQCafeSalida
	// servicioComMQCafeSalida) {
	// this.servicioComMQCafeSalida = servicioComMQCafeSalida;
	// }

}
