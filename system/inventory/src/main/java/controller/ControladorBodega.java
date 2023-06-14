package controller;

import com.zeroc.Ice.Communicator;

import dataAccess.ConexionBD;
import dataAccess.ManejadorDatos;

public class ControladorBodega {

    private ManejadorDatos manejadorDatos;

    public ControladorBodega ( Communicator communicator) {
        this.manejadorDatos = new ManejadorDatos(new ConexionBD(communicator).getConnection());
    }

    public void confirmarOrden(int codOrden) {
        manejadorDatos.obtenerOrdenEntrega(codOrden);
        // Actualizar cantidad de suministros asociados
    }


    public void updateOrden(int codOrden, int codOp) {
        manejadorDatos.updateOperario(codOp, codOp);
    }


    public void comprarSuministros() {
        throw new UnsupportedOperationException("Unimplemented method 'comprarSuministros'");
    }

    public void consultarSuministros() {
        throw new UnsupportedOperationException("Unimplemented method 'consultarSuministros'");
    }
    
    
}
