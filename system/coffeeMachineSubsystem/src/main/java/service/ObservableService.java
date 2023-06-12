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
    public boolean noti(Current current) {
        return controladorMQ.cargarRecetaMaquinas();
    }

}
