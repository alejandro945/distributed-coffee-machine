package service;

import java.util.ArrayList;

import com.zeroc.Ice.Current;

import repository.ProductoReceta;
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
        System.out.println("ObserverService.attach - Proxy: " + proxy);
        System.out.println("Size of proxy observables" + observables.size());
        observables.add(proxy);
        return true;
    }

    private String[] getUpdate(String key, Current current) {
        if (key == "product") {
            return productoReceta.consultarProductos(current); // All
        } else if (key == "receta") {
            return productoReceta.consultarRecetas(current);
        } else {
            return productoReceta.consultarIngredientes(current);
        }
    }

    @Override
    public void _notifyAll(Current current) {
        System.out.println("ObserverService._notifyAll - Notifying all proxy observers");
        for (int i = 0; i < observables.size(); i++) {
            observables.get(i).update(getUpdate("product", current));
        }
    }

}
