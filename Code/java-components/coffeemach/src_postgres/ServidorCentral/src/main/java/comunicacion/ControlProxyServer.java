package comunicacion;

import com.zeroc.Ice.*;
import servicios.*;
import ServerControl.*;

public class ControlProxyServer implements ServicioProxyServer{

    private ServerControl control;

	public ControlProxyServer(ServerControl con) {
		control = con;
	}

    @Override
	public String[] update(Current current) {

		String[] temp = control.update();

		return temp;
	}

}
