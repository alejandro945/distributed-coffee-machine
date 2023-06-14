package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManejadorDatos {

	private Connection conexion;

	/**
	 * <b>Descripción:</b>Modifica el Objeto Connection para realizar la
	 * conexion a la BD.
	 * 
	 * @param conexion
	 *                 Es el objeto Connection
	 */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public boolean confirmarOrden (int codOrden) {
		boolean retorno = false;
		try {
			String update = "UPDATE ORDEN_LOGISTICA SET ESTADO = 'CONFIRMADA' WHERE CODIGO = ?";
			PreparedStatement pst = conexion.prepareStatement(update);
			pst.setInt(1, codOrden);
			pst.executeUpdate();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public boolean asignarOperador(int idOperador, int idMaquina) {
		boolean retorno = false;
		try {

			String insert = "INSERT INTO ASIGNACION_MAQUINA (ID_MAQUINA,ID_OPERADOR) VALUES (?,?)";
			PreparedStatement pst = conexion.prepareStatement(insert);
			pst.setInt(1, idMaquina);
			pst.setInt(2, idOperador);
			pst.executeUpdate();
			retorno = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retorno;
	}
}
