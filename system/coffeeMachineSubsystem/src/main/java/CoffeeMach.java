import com.zeroc.Ice.*;

import controller.ControladorMQ;
import service.ObservableService;

import java.util.*;
import servicios.*;

public class CoffeeMach {
  public static void main(String[] args) {
    List<String> extPar = new ArrayList<>();
    try (Communicator communicator = Util.initialize(args, "coffeMach.cfg", extPar)) {

      // Ice Adapter
      ObjectAdapter adapter = communicator.createObjectAdapter("CoffeMach");

      // Ice Services
      AlarmaServicePrx alarmaS = AlarmaServicePrx.checkedCast(communicator.propertyToProxy("alarmas")).ice_twoway();
      VentaServicePrx ventas = VentaServicePrx.checkedCast(communicator.propertyToProxy("ventas")).ice_twoway();
      ProxyServicePrx proxyServicePrx = ProxyServicePrx.checkedCast(communicator.propertyToProxy("proxy")).ice_twoway();
      ObserverPrx observerServicePrx = ObserverPrx.checkedCast(communicator.propertyToProxy("observer")).ice_twoway();

      // Controller
      ControladorMQ controller = new ControladorMQ(proxyServicePrx, ventas, alarmaS);
      controller.run();
      
      // Services
      ObservablePrx observablePrx = ObservablePrx.uncheckedCast(adapter.add(new ObservableService(controller), Util.stringToIdentity("observable")));
      observerServicePrx.attach(observablePrx);

      // Add services to adapter
      adapter.add((ServicioAbastecimiento) controller, Util.stringToIdentity("supply"));

      // Activate adapter
      adapter.activate();
      communicator.waitForShutdown();
    }
  }
}