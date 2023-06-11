import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;


public class Observer {
    public static void main(String[] args) throws UnknownHostException {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "observer.cfg", params)) {
            /*
             *            ObjectAdapter adapter = communicator.createObjectAdapter("Observer");

            RecetaServicePrx recetaServicePrx = RecetaServicePrx.checkedCast(
                    communicator.propertyToProxy("recetas")).ice_twoway();

            ProxyServices proxyServices = new ProxyServices(recetaServicePrx);

            adapter.add(proxyServices, Util.stringToIdentity("Proxy"));
            adapter.activate();

            communicator.waitForShutdown();
             */



        }

    }
}