package comunicacion;

import java.util.Date;
import java.util.List;

import control.*;
import servicios.*;

public class Comunicacion implements ServicioCom_MQCafe, ServicioCom_Logistica,
		ServicioProxyServer, ServicioClienteRecServidor {

	private Control control;

	public Comunicacion() {

		control = new Control();

	}

	/*
	 * ------------------------------------------------------------ Servicios
	 * llamados por el nodo de logística
	 */

	public boolean asignarOperador(int idOperador, int idMaquina) {

		return control.asignarOperador(idMaquina, idOperador);
	}

	// Funciona correctamente
	@Override
	public List<String> asignacionMaquina(int codigoOperador) {

		return control.listaAsignaciones(codigoOperador);
	}

	// Funciona correctamente
	@Override
	public List<String> asignacionMaquinasDesabastecidas(int codigoOperador) {

		return control.listaAsignacionesMDanada(codigoOperador);
	}

	@Override
	public boolean inicioSesion(int codigoOperador, String password) {

		return control.existeOperador(codigoOperador, password);
	}

	/*
	 * --------------------------------------------------------------------------
	 * ---------- Servicios llamados por la maquina de Café
	 */

	// Guarda correctamente la alarma en la BD y genera el mensaje que será
	// enviado a logística
	@Override
	public void alarma_maquina(int idAlarma, int idMaquina, Date fecha_inicial) {

		//
		// System.out.println(idAlarma+" - "+idMaquina+" - "+fecha_inicial);

		String dato = control.alarmaMaquina(idAlarma, idMaquina, fecha_inicial);
		if (dato != null) {

			// String[] opSub = dato.split("#");
			// System.out.println(opSub[1]);
			// No es capaz de encontrar la clase
			// Comunicacion_ViaEmail cVE = new Comunicacion_ViaEmail();
			// cVE.prueba();
			// cVE.enviarEmail(opSub[0], opSub[1]);

		}

	}

	@Override
	public void reporte_ventas(int idMaquina, Date fechaInicial,
			Date fechaFinal, String[] detalle) {
		control.reporteVentas(idMaquina, fechaInicial, fechaFinal, detalle);

	}

	@Override
	public void desactivarAlarma(int idMaquina, int idAlarma, Date fecha_final) {

		control.desactivarAlarma(idAlarma, idMaquina, fecha_final);

	}

	@Override
	public String[] update() {

		String[] temp = control.update();

		return temp;
	}

	@Override
	public void asociar(int idReceta, int idIngrediente, int valor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] consultarIngredientes() {

		return control.consultarIngredientes();
	}

	@Override
	public String[] consultarRecetas() {

		return control.consultarRecetas();
	}

	@Override
	public void borrarReceta(int cod) {

		control.borrarReceta(cod);
	}

	@Override
	public void registrarReceta_Ingrediente(int idReceta, int idIngrediente,
			int valor) {
		control.registrarReceta_Ingrediente(idReceta, idIngrediente, valor);
	}

	@Override
	public String registrarReceta(String nombre, int precio) {

		return control.registrarReceta(nombre, precio);
	}

	@Override
	public String registrarIngrediente(String nombre) {

		return control.registrarIngrediente(nombre);
	}

	//
	// public void setServicioCom_MQCafeSalida(ServicioCom_MQCafeSalida
	// servicioCom_MQCafeSalida) {
	// this.servicioCom_MQCafeSalida = servicioCom_MQCafeSalida;
	// }

}
