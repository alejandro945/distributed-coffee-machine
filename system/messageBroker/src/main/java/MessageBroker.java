import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.*;
import servicios.*;

public class MessageBroker {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "broker.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Broker");

            // Ice Services

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();

        }
    }
}