import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import services.OrdenEntregaImp;
import servicios.OrdenLogisticaPrx;

public class BodegaCentral {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "bodegaCentral.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("BodegaCentral");

            // Ice Services
            OrdenLogisticaPrx logistica = OrdenLogisticaPrx.checkedCast(communicator.propertyToProxy("logistica"))
                    .ice_twoway();

            // Commented for automatization purposes
            /* Interfaz interfaz = new Interfaz(operador, orden, communicator); */
            /* Thread t = new Thread(interfaz); */
            /* t.start(); */

            // Adapter Configuration
            adapter.add(new OrdenEntregaImp(logistica, communicator), Util.stringToIdentity("Logistica"));
            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
