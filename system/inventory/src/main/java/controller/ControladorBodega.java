package controller;

import com.zeroc.Ice.Communicator;

import guiInventario.Interfaz;
import servicios.OperadorLogisticaPrx;
import servicios.OrdenLogisticaPrx;

public class ControladorBodega {

    OperadorLogisticaPrx operador;
    OrdenLogisticaPrx orden;
    Communicator communicator;
    private Interfaz interfaz;

    public ControladorBodega (OperadorLogisticaPrx o, OrdenLogisticaPrx ord, Communicator communicator) {
        this.operador = o;
        this.orden = ord;
        this.communicator = communicator;
    }

    public void run() {
        interfaz = new Interfaz(this);
        Thread t = new Thread(interfaz);
        t.start();
    }

    public void confirmarOrden(int codOrden) {
        // TODO Auto-generated method stub
        orden.confirmarOrden();
    }

    public void atenderOperador(int codOp) {
        // TODO Auto-generated method stub
        operador.atenderOperador();
    }

    public void comprarSuministros() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comprarSuministros'");
    }

    public void consultarSuministros() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarSuministros'");
    }
    
    
}
