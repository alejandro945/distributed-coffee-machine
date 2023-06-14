import java.util.*;
import com.zeroc.Ice.*;

import service.OrdenTrabajo;
import service.PlanificadorRuta;
import ui.LogisticDashboard;

public class CmLogistics {
    public static void main(String[] args) {
        List<String> extArgs = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "logistica.cfg", extArgs)) {
            ObjectAdapter adapter = communicator.createObjectAdapter("CmLogistic");
            

            OrdenTrabajo ordenTrabajo = new OrdenTrabajo(communicator);
            PlanificadorRuta planificadorRuta = new PlanificadorRuta(communicator);

            LogisticDashboard dashboard = new LogisticDashboard(communicator);

            Thread t = new Thread(dashboard);
            t.start();

            adapter.add(ordenTrabajo, Util.stringToIdentity("OrdenTrabajo"));
            adapter.add(planificadorRuta, Util.stringToIdentity("PlanificadorRuta"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
