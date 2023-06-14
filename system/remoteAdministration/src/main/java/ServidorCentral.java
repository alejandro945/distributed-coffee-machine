import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

import repository.AlarmasManager;
import repository.OrdenManager;
import repository.ProductoReceta;
import repository.LogisticManager;
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
            LogisticManager control = new LogisticManager(communicator);
            ProductoReceta recetas = new ProductoReceta(communicator);
            VentasManager ventas = new VentasManager(communicator);

            // Ice Services
            OrdenLogisticaPrx ordenLogisticaPrx = OrdenLogisticaPrx.checkedCast(communicator.propertyToProxy("logistica"))
                    .ice_twoway(); // Logistica
            OrdenBodegaPrx ordenBodegaPrx = OrdenBodegaPrx.checkedCast(communicator.propertyToProxy("inventario"))
                    .ice_twoway(); // Inventario

            // Services
            ObserverService observerService = new ObserverService(recetas);
            AlarmaServiceImp alarma = new AlarmaServiceImp(new AlarmasManager(communicator),
                    new OrdenManager(communicator), ordenLogisticaPrx, ordenBodegaPrx);

            // Controllers
            ServicioComLogistica log = new ControlComLogistica(control);
            // ControladorRecetas controladorRecetas = new ControladorRecetas(recetas,
            // observerService);
            // controladorRecetas.run();

            // Add services to adapter
            adapter.add(alarma, Util.stringToIdentity("Alarmas")); // Message Broker on Alarm Topic
            adapter.add(ventas, Util.stringToIdentity("Ventas")); // Message Broker on Sales Topic
            adapter.add(log, Util.stringToIdentity("Logistica"));
            adapter.add(recetas, Util.stringToIdentity("Recetas"));// Proxy Cache on Recipes
            adapter.add(observerService, Util.stringToIdentity("Observer")); // Pub and Sub on Recipes

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
