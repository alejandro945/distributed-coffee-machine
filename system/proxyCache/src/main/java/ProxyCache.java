import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

import service.ProxyServices;
import servicios.RecetaServicePrx;

public class ProxyCache {
    public static void main(String[] args) throws UnknownHostException {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "proxy.cfg", params)) {

            ObjectAdapter adapter = communicator.createObjectAdapter("Proxy");

            RecetaServicePrx recetaServicePrx = RecetaServicePrx.checkedCast(
                    communicator.propertyToProxy("recetas")).ice_twoway();

            ProxyServices proxyServices = new ProxyServices(recetaServicePrx);

            adapter.add(proxyServices, Util.stringToIdentity("Proxy"));
            adapter.activate();

            communicator.waitForShutdown();

        }

    }
}