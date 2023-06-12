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

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Proxy");

            // Ice Services
            ObserverPrx observerServicePrx = ObserverPrx.checkedCast(communicator.propertyToProxy("observer")).ice_twoway(); // Server
            RecetaServicePrx recetaServicePrx = RecetaServicePrx.checkedCast(communicator.propertyToProxy("recetas")).ice_twoway();

            // Services
            ObserverService observerService = new ObserverService(recetaServicePrx, CacheService.getInstance());
            ObservablePrx observablePrx = ObservablePrx.uncheckedCast(adapter.add(new ObservableService(CacheService.getInstance(), observerService), Util.stringToIdentity("observable"))); // Proxy observable same reference
            observerServicePrx.attach(observablePrx);
            ProxyServices proxyServices = new ProxyServices(recetaServicePrx);

            // Add services to adapter
            adapter.add(proxyServices, Util.stringToIdentity("Proxy")); // To server
            adapter.add(observerService, Util.stringToIdentity("Observer")); // To server

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();

        }

    }
}