package receta;

import java.util.ArrayList;

import com.zeroc.Ice.Current;

import servicios.ObservablePrx;
import servicios.Observer;

public class ObserverService implements Observer {

    /* Attributes */
    private ProductoReceta productoReceta;
    private ArrayList<ObservablePrx> observables = new ArrayList<>();

    /* Constructor */
    public ObserverService(ProductoReceta productoReceta) {
        this.productoReceta = productoReceta;
    }

    /* Methods */
    @Override
    public boolean attach(ObservablePrx proxy, Current current) {
        if (proxy == null) return false;
        observables.add(proxy);
        return true;
    }

    @Override
    public String[] getUpdate(String key, Current current) {
        return productoReceta.consultarIngredientes(current);
    }
    
}
