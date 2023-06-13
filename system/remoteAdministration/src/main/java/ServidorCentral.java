import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

import repository.AlarmasManager;
import repository.OrdenManager;
import repository.ProductoReceta;
import repository.ServerControl;
import repository.VentasManager;
import service.AlarmaServiceImp;
import service.ObserverService;
import servicios.*;
import controller.ControlComLogistica;
import controller.ControladorRecetas;

public class ServidorCentral {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "server.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Server");

            // Repositories that implements services better decoupling
            ServerControl control = new ServerControl(communicator);
            ProductoReceta recetas = new ProductoReceta(communicator);
            VentasManager ventas = new VentasManager(communicator);

            // Services
            ObserverService observerService = new ObserverService(recetas);
            AlarmaServiceImp alarma = new AlarmaServiceImp(new AlarmasManager(communicator),
                    new OrdenManager(communicator));

            // Controllers
            ServicioComLogistica log = new ControlComLogistica(control);
            ControladorRecetas controladorRecetas = new ControladorRecetas(recetas, observerService);
            controladorRecetas.run();

            // Add services to adapter
            adapter.add(alarma, Util.stringToIdentity("Alarmas"));
            adapter.add(ventas, Util.stringToIdentity("Ventas"));
            adapter.add(log, Util.stringToIdentity("logistica"));
            adapter.add(recetas, Util.stringToIdentity("Recetas"));// Conection with proxy
            adapter.add(observerService, Util.stringToIdentity("Observer")); // Conection with pub-sub

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
