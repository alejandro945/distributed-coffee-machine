package services;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import controller.ControladorBodega;
import servicios.OrdenBodega;
import servicios.OrdenLogisticaPrx;

public class OrdenEntregaImp implements OrdenBodega {

    private OrdenLogisticaPrx ordenLogistica;
    private ControladorBodega controladorBodega;

    public OrdenEntregaImp(OrdenLogisticaPrx ordenLogistica, Communicator communicator) {
        this.ordenLogistica = ordenLogistica;
        this.controladorBodega = new ControladorBodega(communicator);
    }

    @Override
    public boolean atenderOperador(int codOp, int codOrden, Current current) {
        // Se atiende al operador actualizar orden de entrega con el id del operador
        controladorBodega.updateOrden(codOrden, codOp);
        System.out.println("Se ha actualizado correctamente el operador de la orden de entrega");
        // Al final se hace un feedback a logistica Aknowledgement
        ordenLogistica.confirmarOrden(codOrden);
        return true;
    }

    @Override
    public void notificarOrdenEntrega(int code,Current current) {
        // Llega la alama desde el servidor central
        controladorBodega.confirmarOrden(code);
    }

    
    
}
