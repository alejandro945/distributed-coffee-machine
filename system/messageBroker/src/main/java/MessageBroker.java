import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.*;

import services.AlarmaServiceBroker;
import services.VentaServiceBroker;
import servicios.*;

public class MessageBroker {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "broker.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Broker");

            // Ice Services
            AlarmaServiceBroker alarma = new AlarmaServiceBroker(communicator);
            VentaServiceBroker ventas = new VentaServiceBroker(communicator);

            // Services
            // ObserverService observerService = new ObserverService(recetas);
            // AlarmaServiceImp alarma = new AlarmaServiceImp(new
            // AlarmasManager(communicator));

            // Controllers

            // Add services to adapter
            adapter.add(alarma, Util.stringToIdentity("Alarmas"));
            adapter.add(ventas, Util.stringToIdentity("Ventas"));

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();

        }
    }
}