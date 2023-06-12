package service;

import java.util.ArrayList;

import com.zeroc.Ice.Current;

import servicios.ObservablePrx;
import servicios.Observer;
import servicios.RecetaServicePrx;

public class ObserverService implements Observer {

    private ArrayList<ObservablePrx> observables = new ArrayList<>();

    private RecetaServicePrx recetaServicePrx;
    private CacheService cacheService;

    public ObserverService(RecetaServicePrx recetaServicePrx, CacheService cacheService) {
        this.recetaServicePrx = recetaServicePrx;
        this.cacheService = cacheService;
    }

    @Override
    public boolean attach(ObservablePrx machine, Current current) {
        if (machine == null)
            return false;
        observables.add(machine);
        return true;
    }

    @Override
    public String[] getUpdate(String key, Current current) {
        if (cacheService.getElementFromCache(key) != null) {
            return cacheService.getCache().getIfPresent(key);
        }

        if (key == "product") {
            cacheService.putElementInCache(key, recetaServicePrx.consultarProductos());
            return recetaServicePrx.consultarProductos();
        } else if (key == "receta") {
            cacheService.putElementInCache(key, recetaServicePrx.consultarRecetas());
            return recetaServicePrx.consultarRecetas();
        } else {
            cacheService.putElementInCache(key, recetaServicePrx.consultarIngredientes());
            return recetaServicePrx.consultarIngredientes();
        }
    }

    public ArrayList<ObservablePrx> getObservables() {
        return observables;
    }
}
