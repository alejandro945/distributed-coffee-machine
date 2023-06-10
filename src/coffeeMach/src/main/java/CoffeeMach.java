import com.zeroc.Ice.*;

import McControlador.ControladorMQ;

import java.util.*;
import servicios.*;

public class CoffeeMach {
  public static void main(String[] args) {
    List<String> extPar = new ArrayList<>();
    try (Communicator communicator = Util.initialize(args, "coffeMach.cfg", extPar)) {

      AlarmaServicePrx alarmaS = AlarmaServicePrx.checkedCast(
          communicator.propertyToProxy("alarmas")).ice_twoway();
      VentaServicePrx ventas = VentaServicePrx.checkedCast(
          communicator.propertyToProxy("ventas")).ice_twoway();

      System.out.println(communicator.getProperties().getProperty("proxy"));
      System.out.println(communicator.getProperties().getProperty("alarmas"));
      System.out.println(communicator.getProperties().toString());

      ProxyServicePrx proxyServicePrx = ProxyServicePrx.checkedCast(
          communicator.propertyToProxy("proxy")).ice_twoway();

      ObjectAdapter adapter = communicator.createObjectAdapter("CoffeMach");
      ControladorMQ service = new ControladorMQ();
      service.setAlarmaService(alarmaS);
      service.setVentas(ventas);
      service.setProxyServicePrx(proxyServicePrx);

      service.run();
      adapter.add((ServicioAbastecimiento) service, Util.stringToIdentity("abastecer"));
      adapter.activate();
      communicator.waitForShutdown();
    }
  }
}
