package control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.*;

public class Control {

	ArrayList<String> listaAsociada = new ArrayList<String>();

	public Control() {

		// ConsolaAdministracion cAdmin=new ConsolaAdministracion(this);
		// Thread th=new Thread(cAdmin);
		// th.start();

	}

	public boolean asignarOperador(int idMaquina, int idOperador) {
		if (idMaquina == 0 || idOperador == 0) {
			return false;
		} else {
			Conexion_BD cbd = new Conexion_BD();
			cbd.conectarBaseDatos();
			ManejadorDatos md = new ManejadorDatos();
			md.setConexion(cbd.getConnection());
			md.asignarOperador(idOperador, idMaquina);
			cbd.cerrarConexion();
			return true;
		}
	}

	public List<String> listaAsignaciones(int codigo_operador) {
		List<String> lista = new ArrayList<String>();

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		List<Asignacion_Maquina> asmL = md.listaAsignaciones(codigo_operador);

		for (Asignacion_Maquina asm : asmL) {
			int idMaq = asm.getMaquina().peticion_codigo();
			String ubicacion = asm.getMaquina().getUbicacion();

			String dato = "" + idMaq + "-" + ubicacion;
			lista.add(dato);
		}
		cbd.cerrarConexion();
		return lista;
	}

	public List<String> listaAsignacionesMDanada(int codigoOperador) {
		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		List<String> listaAsign = md
				.listaAsignacionMaquinasDanadas(codigoOperador);
		cbd.cerrarConexion();
		return listaAsign;
	}

	public String darCorreoOperador(int codigoOperador) {
		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());
		String correo = md.darCorreoOperador(codigoOperador);
		cbd.cerrarConexion();
		return correo;
	}

	public boolean existeOperador(int codigoOperador, String contrasena) {

		if (codigoOperador != 0 && contrasena != null) {
			Conexion_BD cbd = new Conexion_BD();
			cbd.conectarBaseDatos();
			ManejadorDatos md = new ManejadorDatos();
			md.setConexion(cbd.getConnection());
			boolean resultado = md.existeOperador(codigoOperador, contrasena);
			cbd.cerrarConexion();
			return resultado;
		}
		return false;

	}

	public String alarmaMaquina(int idAlarma, int idMaquina, Date fecha_inicial) {
		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String alarma = md.darNombreAlarma(idAlarma);
		String operador = md.darOperador(idMaquina);

		if (alarma != null && operador != null) {
			Alarma_Maquina aM = new Alarma_Maquina(idAlarma, idMaquina,
					fecha_inicial);
			md.registrarAlarma(aM);
			cbd.cerrarConexion();
			return "Fallo de máquina: " + alarma + " - Atención por:"
					+ operador;
		}
		cbd.cerrarConexion();
		return null;
	}

	public void desactivarAlarma(int idAlarma, int idMaquina, Date fecha_Final) {
		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());
		md.desactivarAlarma(idMaquina, idAlarma, fecha_Final);
		cbd.cerrarConexion();
	}

	public void reporteVentas(int idMaquina, Date fechaInicial,
			Date fechaFinal, String[] detalle) {
		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		Ventas_Maquina vM = new Ventas_Maquina();
		vM.setFechaInicial(fechaInicial);
		vM.setFechaFinal(fechaFinal);
		vM.setIdMaquina(idMaquina);

		List<Ventas_Receta> vR = new ArrayList<Ventas_Receta>();

		for (String dato : detalle) {

			String[] div = dato.split("#");
			Ventas_Receta vRTemp = new Ventas_Receta();
			vRTemp.setIdReceta(Integer.parseInt(div[0]));

			vRTemp.setValor_receta(Integer.parseInt(div[1]));

			vR.add(vRTemp);
		}

		vM.setDetalle(vR);
		vM.setValor(0);

		md.registrarReporteVentas(vM);
		cbd.cerrarConexion();
	}

	public String[] update() {

		Conexion_BD cbd = new Conexion_BD();
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

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String[] ret = md.consultarRecetas();

		cbd.cerrarConexion();

		return ret;
	}

	public String[] consultarIngredientes() {

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String[] ret = md.consultarIngredientes();

		cbd.cerrarConexion();

		return ret;

	}

	public String registrarIngrediente(String nombre) {

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String ret = md.registrarIngrediente(nombre);

		cbd.cerrarConexion();

		return ret;

	}

	public String registrarReceta(String nombre, int precio) {

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		String ret = md.registrarReceta(nombre, precio);

		cbd.cerrarConexion();

		return ret;

	}

	public void borrarReceta(int cod) {

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		md.borrarReceta(cod);

		cbd.cerrarConexion();

	}

	public void registrarReceta_Ingrediente(int idReceta, int idIngrediente,
			int valor) {

		Conexion_BD cbd = new Conexion_BD();
		cbd.conectarBaseDatos();
		ManejadorDatos md = new ManejadorDatos();
		md.setConexion(cbd.getConnection());

		md.registrarReceta_Ingrediente(idReceta, idIngrediente, valor);

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
//		Conexion_BD cbd = new Conexion_BD();
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
