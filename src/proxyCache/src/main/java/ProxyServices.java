import com.zeroc.Ice.Current;

import servicios.ProxyService;

public class ProxyServices implements ProxyService {

    @Override
    public String[] consultarIngredientesProxy(Current current) {
        System.out.println("Llego");
        return new String[] { "hola", "chau" };
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
