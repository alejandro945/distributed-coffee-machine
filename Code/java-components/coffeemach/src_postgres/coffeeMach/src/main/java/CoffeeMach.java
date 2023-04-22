import controladorMaquinaCafe.*;
import com.zeroc.Ice.*;
import java.util.*;
import servicios.*;

public class CoffeeMach {
  public static void main(String[] args) {
    List<String> extPar = new ArrayList<>();
    try (Communicator communicator =  Util.initialize(args,"coffeMach.cfg",extPar)) {
        
        ServicioComMQCafePrx comMQcafe = ServicioComMQCafePrx.checkedCast(
            communicator.propertyToProxy("MqCafe")
        ).ice_twoway();
        ServicioProxyServerPrx proxySer = ServicioProxyServerPrx.checkedCast(
            communicator.propertyToProxy("ProxyServer")
        ).ice_twoway();

        ObjectAdapter adapter = communicator.createObjectAdapter("CoffeMach");
        ControladorMQ service = new ControladorMQ();
        service.setServicioComMQCafe(comMQcafe);
        service.setServicioMQProxy(proxySer);
        service.run();
        adapter.add((ServicioAbastecimiento)service, Util.stringToIdentity("abastecer"));
        adapter.activate();
        communicator.waitForShutdown();
    }
  }  
}
