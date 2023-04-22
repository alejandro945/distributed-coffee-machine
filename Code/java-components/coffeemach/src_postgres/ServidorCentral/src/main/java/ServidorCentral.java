
import java.util.ArrayList;
import java.util.List;
import com.zeroc.Ice.*;
import comunicacion.*;
import servicios.*;
import ServerControl.*;

public class ServidorCentral {

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        try (Communicator communicator = Util.initialize(args, "server.cfg", params)) {
            
            ObjectAdapter adapter = communicator.createObjectAdapter("Server");
            

            ServerControl control = new ServerControl(communicator);


            ServicioComLogistica log = new ControlComLogistica(control);
            ServicioComMQCafe comMQ = new ControlComMQCafe(control);
            ServicioProxyServer pro = new ControlProxyServer(control);
            ServicioClienteRecServidor recetas = new ControlClienteRecServidor(control);
            adapter.add(comMQ, Util.stringToIdentity("MQCafe"));
            adapter.add(log, Util.stringToIdentity("logistica"));
            adapter.add(pro, Util.stringToIdentity("proxy"));
            adapter.add(recetas, Util.stringToIdentity("recetas"));

            adapter.activate();
            communicator.waitForShutdown();

        }
    }
}
