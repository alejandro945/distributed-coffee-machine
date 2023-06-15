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
        System.out.println("ObserverService.attach - Client: " + machine);
        if (machine == null)
            return false;
        System.out.println("Tamano of machines observables" + observables.size());
        observables.add(machine);
        return true;
    }

    public String[] getFromCache(String key) {
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

    @Override
    public void _notifyAll(Current current) {
        System.out.println("Notificando a todas las maquinas de cafe suscritas sobre cambio en recetas");
        for (int i = 0; i < observables.size(); i++) {
            observables.get(i).update(getFromCache("product"));;
        }
    }
}
