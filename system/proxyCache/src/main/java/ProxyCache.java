import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;

import service.CacheService;
import service.ObservableService;
import service.ObserverService;
import service.ProxyServices;
import servicios.ObservablePrx;
import servicios.ObserverPrx;
import servicios.RecetaServicePrx;

public class ProxyCache {
    public static void main(String[] args) throws UnknownHostException {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "proxy.cfg", params)) {

            ObjectAdapter adapter = communicator.createObjectAdapter("Proxy");

            System.out.println(communicator.getProperties().getProperty("recetas"));

            ObserverPrx observerServicePrx = ObserverPrx.checkedCast(communicator.propertyToProxy("observer"))
                    .ice_twoway();

            ObservablePrx observablePrx = ObservablePrx.uncheckedCast(communicator.propertyToProxy("Proxy"));

            RecetaServicePrx recetaServicePrx = RecetaServicePrx.checkedCast(
                    communicator.propertyToProxy("recetas")).ice_twoway();

            ObserverService observerService = new ObserverService(recetaServicePrx, CacheService.getInstance());
            new ObservableService(observerService);

            observerServicePrx.attach(observablePrx);

            ProxyServices proxyServices = new ProxyServices(recetaServicePrx);

            adapter.add(proxyServices, Util.stringToIdentity("Proxy"));
            adapter.add(observerService, Util.stringToIdentity("Observer"));
            adapter.activate();

            communicator.waitForShutdown();

        }

    }
}