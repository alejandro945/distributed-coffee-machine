import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

public class ProxyCache {
    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "proxy.cfg", params)) {

            ObjectAdapter adapter = communicator.createObjectAdapter("Proxy");

            ProxyServices proxyServices = new ProxyServices();
            proxyServices.setCommunicator(communicator);

            adapter.add(proxyServices, Util.stringToIdentity("server"));

            adapter.activate();
            communicator.waitForShutdown();

        }

    }
}