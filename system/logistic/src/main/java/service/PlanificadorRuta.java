package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import model.Operario;
import repository.ConexionBD;
import repository.ManejadorDatos;
import servicios.OperadorLogistica;

public class PlanificadorRuta implements OperadorLogistica{

    private Communicator communicator;

    public PlanificadorRuta(Communicator com) {
        communicator = com;
    }

    @Override
    public boolean atenderOperador(int codOp, int codOrden, Current current) {
        boolean resultado = false;
        if (codOp != 0) {
            ConexionBD connection = new ConexionBD(communicator);
            connection.conectarBaseDatos();
            ManejadorDatos dataManager = new ManejadorDatos();
            dataManager.setConexion(connection.getConnection());
            resultado = dataManager.atenderOperador(codOp, codOrden);
            connection.cerrarConexion();
        }
        return resultado;
    }


    
}
