import java.util.*;
import com.zeroc.Ice.*;

import service.OrdenTrabajo;
import servicios.OrdenBodegaPrx;

public class CmLogistics {
    public static void main(String[] args) {
        List<String> extArgs = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "logistica.cfg", extArgs)) {
            ObjectAdapter adapter = communicator.createObjectAdapter("CmLogistics");

            // Ice Services
            OrdenBodegaPrx bodega = OrdenBodegaPrx.checkedCast(communicator.propertyToProxy("inventario"))
                    .ice_twoway();

            OrdenTrabajo ordenTrabajo = new OrdenTrabajo(communicator, bodega);

            // LogisticDashboard dashboard = new LogisticDashboard(communicator);

            /*
             * Thread t = new Thread(dashboard);
             * t.start();
             */

            //adapter.add(ordenTrabajo, Util.stringToIdentity("Inventario"));
            adapter.add(ordenTrabajo, Util.stringToIdentity("Log"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
