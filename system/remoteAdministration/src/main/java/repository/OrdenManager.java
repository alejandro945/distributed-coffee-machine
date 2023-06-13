package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zeroc.Ice.Communicator;

import model.ConexionBD;
import model.OrdenEntrega;
import model.OrdenTrabajo;

public class OrdenManager {
    private Communicator communicator;

    public OrdenManager(Communicator communicator) {
        this.communicator = communicator;
    }

    public int insertOrdenTrabajo(OrdenTrabajo ot) {
        ConexionBD cbd = new ConexionBD(communicator);
        cbd.conectarBaseDatos();
        Connection conexion = cbd.getConnection();
        int consecutivo = 0;
        try {
            Statement st = conexion.createStatement();
            st.execute("SELECT NEXTVAL('SEQ_ORDEN_TRABAJO')");
            ResultSet rs = st.getResultSet();
            if (rs.next()) { consecutivo = rs.getInt(1); }
            String insertnuevaOrden = "INSERT INTO ORDENES_TRABAJO (IDALARMA, IDOPERADOR, IDORDEN, IDMAQUINA, FECHA, IDORDEN_ENTREGA) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conexion.prepareStatement(insertnuevaOrden);
            pst.setInt(1, ot.getIdAlarma());
            pst.setInt(2, ot.getIdOperador());
            pst.setInt(3, consecutivo);
            pst.setInt(4, ot.getIdmaquina());
            pst.setDate(5, new Date(ot.getFecha().getTime()));
            pst.setInt(6, ot.getIdOrdenEntrega());
            pst.execute();
            pst.close();
            st.close();
            cbd.cerrarConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consecutivo;
    }

    public int insertOrdenEntrega(OrdenEntrega oe) {
        ConexionBD cbd = new ConexionBD(communicator);
        cbd.conectarBaseDatos();
        int consecutivo = 0;
        try {
            Statement st = cbd.getConnection().createStatement();
            st.execute("SELECT NEXTVAL('SEQ_ORDEN_ENTREGA')");
            ResultSet rs = st.getResultSet();
            if (rs.next()) { consecutivo = rs.getInt(1); }
            String insertnuevaOrden = "INSERT INTO ORDENES_ENTREGA (IDOPERADOR, IDORDEN, IDMAQUINA, IDKIT, FECHA, CANTIDAD, IDSUMINISTRO, IDINGREDIENTE, IDALARMA) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst =  cbd.getConnection().prepareStatement(insertnuevaOrden);
            pst.setInt(1, oe.getIdOperador());
            pst.setInt(2, consecutivo);
            pst.setInt(3, oe.getIdMaquina());
            pst.setInt(4, oe.getIdKit());
            pst.setDate(5, new Date(oe.getFecha().getTime()));
            pst.setInt(6, oe.getCantidad());
            pst.setInt(7, oe.getIdSuministro());
            pst.setInt(8, oe.getIdIngrediente());
            pst.setInt(9, oe.getIdAlarma());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consecutivo;
    }
}
