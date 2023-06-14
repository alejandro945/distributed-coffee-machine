package controller;

import java.util.Scanner;

import com.zeroc.Ice.Communicator;

import guiInventario.Interfaz;
import servicios.OperadorLogisticaPrx;
import servicios.OrdenLogisticaPrx;

public class ControladorBodega {

    OperadorLogisticaPrx operador;
    OrdenLogisticaPrx orden;
    Communicator communicator;

    public ControladorBodega (OperadorLogisticaPrx o, OrdenLogisticaPrx ord, Communicator communicator) {
        this.operador = o;
        this.orden = ord;
        this.communicator = communicator;
    }

    public void confirmarOrden(int codOrden) {
        // TODO Auto-generated method stub
        orden.confirmarOrden(codOrden);
    }

    public void atenderOperador(int codOp, int codOrden) {
        operador.atenderOperador(codOp, codOrden);
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
