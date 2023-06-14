package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import repository.ConexionBD;
import repository.ManejadorDatos;
import servicios.OrdenLogistica;

public class OrdenTrabajo implements OrdenLogistica {

    private Communicator communicator;

    public OrdenTrabajo(Communicator com) {
        communicator = com;
    }

    @Override
    public boolean confirmarOrden(int codOrden, Current current) {
        boolean resultado = false;
        if (codOrden != 0) {
            ConexionBD connection = new ConexionBD(communicator);
            connection.conectarBaseDatos();
            ManejadorDatos dataManager = new ManejadorDatos();
            dataManager.setConexion(connection.getConnection());
            resultado = dataManager.confirmarOrden(codOrden);
            connection.cerrarConexion();
        }
        return resultado;
    }
    
}
