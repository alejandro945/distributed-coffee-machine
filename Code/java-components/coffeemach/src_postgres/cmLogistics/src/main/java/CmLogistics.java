import java.util.*;
import com.zeroc.Ice.*;
import controladorLogistica.*;
import servicios.*;

public class CmLogistics {
    public static void main(String[] args) {
        List<String> extArgs = new ArrayList();
        try(Communicator communicator = Util.initialize(args, "CmLogistic.cfg", extArgs)) {
            ServicioComLogisticaPrx serverService = ServicioComLogisticaPrx.checkedCast(
                communicator.propertyToProxy("ServerCentral")
            ).ice_twoway();

            ServicioAbastecimientoPrx mqCafe = ServicioAbastecimientoPrx.checkedCast(
                communicator.propertyToProxy("MaquinaCafe")
            ).ice_twoway();

            if(serverService != null && mqCafe != null){
                Control control = new Control();
                System.out.println("control");
                control.setServicioComLogistica(serverService);
                System.out.println("setLog");
                control.setServicioAbastecimiento(mqCafe);
                System.out.println("setAb");
                control.run();
                System.out.println("run");
                communicator.waitForShutdown();

            }else{
                System.out.println("Error en la conexión");
            }

        }
    }
}
