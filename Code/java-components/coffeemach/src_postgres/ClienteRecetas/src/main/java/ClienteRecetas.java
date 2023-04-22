
import com.zeroc.Ice.*;
import java.util.*;
import servicios.*;
import controladorClienteReceta.*;

public class ClienteRecetas {
    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args,"ClientRec.cfg",params)) {
            ServicioClienteRecServidorPrx clientRec = ServicioClienteRecServidorPrx
                .checkedCast(communicator.propertyToProxy("receta.proxy")).ice_twoway();
            if(clientRec != null){
                System.out.println("conection succesful");
                Controlador control= new Controlador();
                control.setServicioClienteRecServidor(clientRec);
                control.run();
                communicator.waitForShutdown();
            }
        }        
    }
}
