package services;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import servicios.VentaService;

public class VentaServiceBroker implements VentaService {

    private Communicator communicator;

    public VentaServiceBroker(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void registrarVenta(int codMaq, String[] ventas, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarVenta'");
    }

}
