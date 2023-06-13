import java.util.*;
import com.zeroc.Ice.*;

import service.OrdenTrabajo;
import service.PlanificadorRuta;

public class CmLogistics {
    public static void main(String[] args) {
        List<String> extArgs = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "logistica.cfg", extArgs)) {
            ObjectAdapter adapter = communicator.createObjectAdapter("CmLogistic");
            
            OrdenTrabajo ordenTrabajo = new OrdenTrabajo();
            PlanificadorRuta planificadorRuta = new PlanificadorRuta();

            adapter.add(ordenTrabajo, Util.stringToIdentity("OrdenTrabajo"));
            adapter.add(planificadorRuta, Util.stringToIdentity("PlanificadorRuta"));

            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
