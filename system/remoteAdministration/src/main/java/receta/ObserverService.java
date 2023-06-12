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
        if (proxy == null)
            return false;
        observables.add(proxy);
        return true;
    }

    @Override
    public String[] getUpdate(String key, Current current) {
        if (key == "product") {
            return productoReceta.consultarProductos(current);
        } else if (key == "receta") {
            return productoReceta.consultarRecetas(current);
        } else {
            return productoReceta.consultarIngredientes(current);
        }
    }

    public void automatizationNoti() {
        for (int i = 0; i < observables.size(); i++) {
            observables.get(i).noti();
        }
    }

}
