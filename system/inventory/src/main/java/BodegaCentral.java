import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import controller.ControladorBodega;
import guiInventario.Interfaz;
import servicios.OperadorLogisticaPrx;
import servicios.OrdenLogisticaPrx;

public class BodegaCentral {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "bodegaCentral.cfg", params)) {

            // Adapter
            ObjectAdapter adapter = communicator.createObjectAdapter("Bodega");

            // Ice Services
            OrdenLogisticaPrx orden = OrdenLogisticaPrx.checkedCast(communicator.propertyToProxy("logistica")).ice_twoway();
            OperadorLogisticaPrx operador = OperadorLogisticaPrx.checkedCast(communicator.propertyToProxy("operador")).ice_twoway();

            Interfaz interfaz = new Interfaz(operador, orden, communicator);
            Thread t = new Thread(interfaz);
            t.start();

            // Activate adapter
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
