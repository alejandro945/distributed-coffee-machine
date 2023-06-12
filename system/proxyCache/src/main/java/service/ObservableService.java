package service;

import com.zeroc.Ice.Current;

import servicios.Observable;

public class ObservableService implements Observable {

    private CacheService cacheService;
    private ObserverService observerService;

   public ObservableService(CacheService cacheService, ObserverService observerService) {
        this.cacheService = cacheService;
        this.observerService = observerService;
    }

    @Override
    public void update(String[] data, Current current) {
        System.out.println("ObservableService.update - Server · Data: " + data);
        cacheService.putElementInCache("product", data);
        observerService._notifyAll(current);
    }
}
