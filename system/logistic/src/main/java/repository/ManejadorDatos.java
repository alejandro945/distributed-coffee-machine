package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public boolean atenderOperador (int codOperador, int codOrden) {
		boolean retorno = false;
		try {
			String update = "UPDATE OPERADOR SET IDOPERADOR = ? WHERE CODIGO = ?";
			PreparedStatement pst = conexion.prepareStatement(update);
			pst.setInt(1, codOperador);
			pst.setInt(2, codOrden);
			pst.executeUpdate();
			retorno = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
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

}
