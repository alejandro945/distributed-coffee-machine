package service;

import com.zeroc.Ice.Current;

import controller.ControladorMQ;
import servicios.Observable;

public class ObservableService implements Observable {

    private ControladorMQ controladorMQ;

    public ObservableService(ControladorMQ controladorMQ) {
        this.controladorMQ = controladorMQ;
    }

    @Override
    public void update(String[] data, Current current) {
        System.out.println("ObservableService.update  - Proxy · Data: " + data);
        // controladorMQ.cargarRecetaMaquinas();
        controladorMQ.updateRecipes(data);
    }

}
