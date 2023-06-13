import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.*;

import repository.AlarmaRepository;
import services.AlarmaServiceBroker;
import services.ThreadMessage;
import servicios.*;

public class MessageBroker {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "broker.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Broker");



            // Ice Services
            AlarmaServicePrx alarmaServicePrx = AlarmaServicePrx.checkedCast(communicator.propertyToProxy("alarmas")).ice_twoway();
           
            // Services
            AlarmaServiceBroker alarma = new AlarmaServiceBroker(AlarmaRepository.getInstance(), alarmaServicePrx);
            ThreadMessage threadMessage = new ThreadMessage(alarma, AlarmaRepository.getInstance());
            threadMessage.start();
            MessageBrokerPrx messageBroker = MessageBrokerPrx.uncheckedCast(adapter.add(alarma, Util.stringToIdentity("broker")));
            alarma.setMessageBroker(messageBroker);

            // Add services to adapter
            adapter.add(alarma, Util.stringToIdentity("Alarmas")); //Server Binding

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}