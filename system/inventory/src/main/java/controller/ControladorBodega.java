package controller;

import servicios.OperadorLogisticaPrx;
import servicios.OrdenLogisticaPrx;

public class ControladorBodega {

    OperadorLogisticaPrx operador;
    OrdenLogisticaPrx orden;

    public ControladorBodega (OperadorLogisticaPrx o, OrdenLogisticaPrx ord) {
        this.operador = o;
        this.orden = ord;
    }

    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
