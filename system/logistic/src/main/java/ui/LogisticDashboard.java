package ui;

import com.zeroc.Ice.Communicator;

import controller.LogisticController;
import service.PlanificadorRuta;
import servicios.OrdenLogistica;

public class LogisticDashboard implements Runnable {

    LogisticController controller;

    public LogisticDashboard(Communicator com) {
        controller = new LogisticController(com);
    }

    @Override
    public void run() {
        System.out.println("Logistic Dashboard");
        
    }
}
