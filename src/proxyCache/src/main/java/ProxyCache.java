import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

public class ProxyCache {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Test");
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "proxy.cfg", params)) {

            ObjectAdapter adapter = communicator.createObjectAdapter("Proxy");

            ProxyServices proxyServices = new ProxyServices();

            adapter.add(proxyServices, Util.stringToIdentity("server"));
            String hostName = InetAddress.getLocalHost().getHostName();
            System.out.println("Running on machine: " + hostName);

            adapter.activate();

            communicator.waitForShutdown();

        }

    }
}