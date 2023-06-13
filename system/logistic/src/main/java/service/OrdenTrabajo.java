package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import servicios.OrdenLogistica;

public class OrdenTrabajo implements OrdenLogistica {

    private Communicator communicator;

    public OrdenTrabajo(Communicator com) {
        communicator = com;
    }

    @Override
    public void confirmarOrden(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmarOrden'");
    }
    
}
