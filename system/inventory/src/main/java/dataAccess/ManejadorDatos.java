package dataAccess;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

public class ManejadorDatos {

    private Connection conexion;

    public void updateSuministro(int idSuministro, int cantidad, String type) {
        String updateSuministro = "UPDATE SUMINISTRO SET CANTIDAD = CANTIDAD " + type + " ? WHERE ID_SUMINISTRO = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(updateSuministro);
            ps.setInt(1, cantidad);
            ps.setInt(2, idSuministro);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKitReparacion(int idKit, int cantidad, String type) {
        String updateKitReparacion = "UPDATE KIT_REPARACION SET CANTIDAD = CANTIDAD " + type + " ? WHERE ID_KIT = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(updateKitReparacion);
            ps.setInt(1, cantidad);
            ps.setInt(2, idKit);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIngredientesInv(int idIngrediente, int cantidad, String type) {
        String updateIngrediente = "UPDATE INGREDIENTE_INV SET CANTIDAD = CANTIDAD " + type
                + " ? WHERE ID_INGREDIENTE = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(updateIngrediente);
            ps.setInt(1, cantidad);
            ps.setInt(2, idIngrediente);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMonedas(int idMoneda, int cantidad, String type) {
        String updateMoneda = "UPDATE MONEDA SET CANTIDAD = CANTIDAD " + type + " ? WHERE ID_MONEDA = ?";

        try {
            PreparedStatement ps = conexion.prepareStatement(updateMoneda);
            ps.setInt(1, cantidad);
            ps.setInt(2, idMoneda);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}