import com.zeroc.Ice.*;

import McControlador.ControladorMQ;

import java.util.*;
import servicios.*;

public class CoffeeMach {
  public static void main(String[] args) {
    List<String> extPar = new ArrayList<>();
    try (Communicator communicator = Util.initialize(args, "coffeMach.cfg", extPar)) {

      // Services
      AlarmaServicePrx alarmaS = AlarmaServicePrx.checkedCast(communicator.propertyToProxy("alarmas")).ice_twoway();
      VentaServicePrx ventas = VentaServicePrx.checkedCast(communicator.propertyToProxy("ventas")).ice_twoway();
      ProxyServicePrx proxyServicePrx = ProxyServicePrx.checkedCast(communicator.propertyToProxy("proxy")).ice_twoway();
          
      // Controller
      ControladorMQ controller = new ControladorMQ();
      controller.setAlarmaService(alarmaS);
      controller.setVentas(ventas);
      controller.setProxyServicePrx(proxyServicePrx);
      controller.run();

      // Ice Adapter
      ObjectAdapter adapter = communicator.createObjectAdapter("CoffeMach");

      // Adding Supply connection
      adapter.add((ServicioAbastecimiento) controller, Util.stringToIdentity("supply"));
      adapter.activate();
      communicator.waitForShutdown();
    }
  }
}
