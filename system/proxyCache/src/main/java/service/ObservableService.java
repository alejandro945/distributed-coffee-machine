package service;

import com.zeroc.Ice.Current;

import servicios.Observable;

public class ObservableService implements Observable {

    private ObserverService observerService;

    public ObservableService(ObserverService observerService) {
        this.observerService = observerService;
    }

    @Override
    public boolean noti(Current current) {
        boolean out = true;
        for (int i = 0; i < observerService.getObservables().size() && out; i++) {
            out = observerService.getObservables().get(i).noti();
        }
        return out;
    }
}
