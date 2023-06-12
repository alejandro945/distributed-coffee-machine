import com.zeroc.Ice.*;

import controller.ControladorMQ;
import service.ObservableService;

import java.util.*;
import servicios.*;
import servicios.Observer;

public class CoffeeMach {
  public static void main(String[] args) {
    List<String> extPar = new ArrayList<>();
    try (Communicator communicator = Util.initialize(args, "coffeMach.cfg", extPar)) {

      System.out.println("Testeo");

      // Services
      AlarmaServicePrx alarmaS = AlarmaServicePrx.checkedCast(communicator.propertyToProxy("alarmas")).ice_twoway();
      VentaServicePrx ventas = VentaServicePrx.checkedCast(communicator.propertyToProxy("ventas")).ice_twoway();
      ProxyServicePrx proxyServicePrx = ProxyServicePrx.checkedCast(communicator.propertyToProxy("proxy")).ice_twoway();

      // Controller
      ControladorMQ controller = new ControladorMQ(proxyServicePrx, ventas, alarmaS);
      controller.run();

      // Ice Adapter
      ObjectAdapter adapter = communicator.createObjectAdapter("CoffeMach");

      ObserverPrx observerServicePrx = ObserverPrx.checkedCast(communicator.propertyToProxy("observer")).ice_twoway();
      ObservablePrx observablePrx = ObservablePrx.uncheckedCast(communicator.propertyToProxy("CoffeMach"));

      new ObservableService(controller);

      observerServicePrx.attach(observablePrx);

      // Adding Supply connection
      adapter.add((ServicioAbastecimiento) controller, Util.stringToIdentity("supply"));
      adapter.activate();
      communicator.waitForShutdown();
    }
  }
}