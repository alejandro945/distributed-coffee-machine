package ServerControl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.zeroc.Ice.Communicator;

import modelo.*;

public class ServerControl {

	ArrayList<String> listaAsociada = new ArrayList<String>();
	private Communicator comunicator;

	public ServerControl(Communicator com) {
		this.comunicator = com;
		// ConsolaAdministracion cAdmin=new ConsolaAdministracion(this);
		// Thread th=new Thread(cAdmin);
		// th.start();

	}

	public boolean asignarOperador(int idMaquina, int idOperador) {
		if (idMaquina == 0 || idOperador == 0) {
			return false;
		} else {
			ConexionBD cbd = new ConexionBD(comunicator);
			cbd.conectarBaseDatos();
			ManejadorDatos md = new ManejadorDatos();
			md.setConexion(cbd.getConnection());
			md.asignarOperador(idOperador, idMaquina);
			cbd.cerrarConexion();
			return true;
		}
	}

	public List<String> listaAsignaciones(int codigooperador) {
		List<String> lista = new ArrayList<String>();

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		List<AsignacionMaquina> asmL = md.listaAsignaciones(codigooperador);

		for (AsignacionMaquina asm : asmL) {
			int idMaq = asm.getMaquina().peticioncodigo();
			String ubicacion = asm.getMaquina().getUbicacion();

			String dato = "" + idMaq + "-" + ubicacion;
			lista.add(dato);
		}
		cbd.cerrarConexion();
		return lista;
	}

	public List<String> listaAsignacionesMDanada(int codigoOperador) {
		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		List<String> listaAsign = md
				.listaAsignacionMaquinasDanadas(codigoOperador);
		cbd.cerrarConexion();
		return listaAsign;
	}

	public String darCorreoOperador(int codigoOperador) {
		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());
		String correo = md.darCorreoOperador(codigoOperador);
		cbd.cerrarConexion();
		return correo;
	}

	public boolean existeOperador(int codigoOperador, String contrasena) {

		if (codigoOperador != 0 && contrasena != null) {
			ConexionBD cbd = new ConexionBD(comunicator);
			cbd.conectarBaseDatos();
			ManejadorDatos md = new ManejadorDatos();
			md.setConexion(cbd.getConnection());
			boolean resultado = md.existeOperador(codigoOperador, contrasena);
			cbd.cerrarConexion();
			return resultado;
		}
		return false;

	}

	public String alarmaMaquina(int idAlarma, int idMaquina, Date fechainicial) {
		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String alarma = md.darNombreAlarma(idAlarma);
		String operador = md.darOperador(idMaquina);

		if (alarma != null && operador != null) {
			AlarmaMaquina aM = new AlarmaMaquina(idAlarma, idMaquina,
					fechainicial);
			md.registrarAlarma(aM);
			cbd.cerrarConexion();
			return "Fallo de máquina: " + alarma + " - Atención por:"
					+ operador;
		}
		cbd.cerrarConexion();
		return null;
	}

	public void desactivarAlarma(int idAlarma, int idMaquina, Date fechaFinal) {
		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());
		md.desactivarAlarma(idMaquina, idAlarma, fechaFinal);
		cbd.cerrarConexion();
	}

	public void reporteVentas(int idMaquina, Date fechaInicial,
			Date fechaFinal, String[] detalle) {
		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		VentasMaquina vM = new VentasMaquina();
		vM.setFechaInicial(fechaInicial);
		vM.setFechaFinal(fechaFinal);
		vM.setIdMaquina(idMaquina);

		List<VentasReceta> vR = new ArrayList<VentasReceta>();

		for (String dato : detalle) {

			String[] div = dato.split("#");
			VentasReceta vRTemp = new VentasReceta();
			vRTemp.setIdReceta(Integer.parseInt(div[0]));

			vRTemp.setValorreceta(Integer.parseInt(div[1]));

			vR.add(vRTemp);
		}

		vM.setDetalle(vR);
		vM.setValor(0);

		md.registrarReporteVentas(vM);
		cbd.cerrarConexion();
	}

	public String[] update() {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		listaAsociada = md.consultaRecetasCompleta();

		cbd.cerrarConexion();

		if (!listaAsociada.equals(null)) {

			String[] retorno = new String[listaAsociada.size()];

			for (int i = 0; i < listaAsociada.size(); i++) {

				retorno[i] = listaAsociada.get(i);
			}

			return retorno;
		}

		return null;

	}

	public String[] consultarRecetas() {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String[] ret = md.consultarRecetas();

		cbd.cerrarConexion();

		return ret;
	}

	public String[] consultarIngredientes() {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String[] ret = md.consultarIngredientes();

		cbd.cerrarConexion();

		return ret;

	}

	public String registrarIngrediente(String nombre) {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String ret = md.registrarIngrediente(nombre);

		cbd.cerrarConexion();

		return ret;

	}

	public String registrarReceta(String nombre, int precio) {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String ret = md.registrarReceta(nombre, precio);

		cbd.cerrarConexion();

		return ret;

	}

	public void borrarReceta(int cod) {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		md.borrarReceta(cod);

		cbd.cerrarConexion();

	}

	public void registrarRecetaIngrediente(int idReceta, int idIngrediente,
			int valor) {

		ConexionBD cbd = new ConexionBD(comunicator);
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		md.registrarRecetaIngrediente(idReceta, idIngrediente, valor);

		cbd.cerrarConexion();

	}

	// ArrayList<String> listaIng = new ArrayList<String>();

	// ArrayList<String> listaReceta = new ArrayList<String>();

//	public void llenarListas() {
//
//		String[] recetas = consultarRecetas();
//
//		String[] ingredientes = consultarIngredientes();
//
//		String[] recetaIngrediente = consultarRecetaIngrediente();
//
//		for (int i = 0; i < recetas.length; i++) {
//
//			listaReceta.add(recetas[i]);
//
//		}
//
//		for (int i = 0; i < ingredientes.length; i++) {
//
//			listaIng.add(ingredientes[i]);
//
//		}
//
//		for (int i = 0; i < recetaIngrediente.length; i++) {
//
//			String[] split = recetaIngrediente[i].split("-");
//
//			asociar(Integer.parseInt(split[0].trim()),
//					Integer.parseInt(split[1].trim()),
//					Integer.parseInt(split[2].trim()));
//
//		}
//
//	}
//
//	private String[] consultarRecetaIngrediente() {
//
//		ConexionBD cbd = new ConexionBD(comunicator);
//		cbd.conectarBaseDatos();
//		ManejadorDatos md = new ManejadorDatos();
//		md.setConexion(cbd.getConnection());
//
//		String[] ret = md.consultarRecetaIngrediente();
//
//		cbd.cerrarConexion();
//
//		return ret;
//
//	}

//	public void asociar(int idReceta, int idIngrediente, int valor) {
//
//		String concat = "";
//
//		for (int i = 0; i < listaReceta.size(); i++) {
//
//			String[] splitReceta = listaReceta.get(i).split("-");
//
//			if (Integer.parseInt(splitReceta[0].trim()) == idReceta) {
//
//				concat += splitReceta[0] + "-" + splitReceta[1] + "-"
//						+ splitReceta[2];
//
//				for (int i2 = 0; i2 < listaIng.size(); i2++) {
//
//					String[] splitIngrediente = listaIng.get(i2).split("-");
//
//					if (Integer.parseInt(splitIngrediente[0].trim()) == idIngrediente) {
//
//						concat += "#";
//
//						String codAl = validarAlarma(Integer
//								.parseInt(splitIngrediente[0].trim()));
//
//						concat += splitIngrediente[0] + "-"
//								+ splitIngrediente[1] + "-" + codAl + "-"
//								+ valor;
//
//						listaAsociada.add(concat);
//					}
//
//				}
//
//			}
//
//		}
//
//	}

//	public String validarAlarma(int codIng) {
//
//		String retorno = "";
//
//		if (codIng == 1) {
//
//			retorno = "8" + "-" + "12";
//		} else if (codIng == 2) {
//			retorno = "9" + "-" + "13";
//		} else if (codIng == 3) {
//			retorno = "10" + "-" + "14";
//		} else if (codIng == 4) {
//			retorno = "11" + "-" + "15";
//		}
//
//		return retorno;
//
//	}
}
