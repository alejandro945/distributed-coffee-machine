import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.*;

import repository.AlarmaRepository;
import services.AlarmaServiceBroker;
import services.ThreadMessage;
import services.VentaServiceBroker;
import servicios.*;

public class MessageBroker {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "broker.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Broker");

            AlarmaServicePrx alarmaServicePrx = AlarmaServicePrx.checkedCast(communicator.propertyToProxy("alarmas"))
                    .ice_twoway();

            // Ice Services
            AlarmaServiceBroker alarma = new AlarmaServiceBroker(AlarmaRepository.getInstance(), alarmaServicePrx);
            VentaServiceBroker ventas = new VentaServiceBroker();
            ThreadMessage threadMessage = new ThreadMessage(alarma, AlarmaRepository.getInstance());
            threadMessage.start();

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