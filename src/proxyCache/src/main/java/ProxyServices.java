import com.zeroc.Ice.Current;

import servicios.ProxyService;
import com.zeroc.Ice.Communicator;

public class ProxyServices implements ProxyService {

    private Communicator communicator;

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public String[] consultarIngredientesProxy(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarIngredientesProxy'");
    }

    @Override
    public String[] consultarRecetasProxy(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarRecetasProxy'");
    }

    @Override
    public String[] consultarProductosProxy(Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarProductosProxy'");
    }

}
