package comunicacion;

import com.zeroc.Ice.*;
import servicios.ServicioComMQCafe;
import ServerControl.*;
import java.util.*;

public class ControlComMQCafe implements ServicioComMQCafe{

    private ServerControl control;

	public ControlComMQCafe(ServerControl con) {
		control = con;
	}

    @Override
	public void alarmaMaquina(int idAlarma, int idMaquina, Date fechainicial, Current current) {

		//
		// System.out.println(idAlarma+" - "+idMaquina+" - "+fechainicial);

		String dato = control.alarmaMaquina(idAlarma, idMaquina, fechainicial);
		if (dato != null) {

			// String[] opSub = dato.split("#");
			// System.out.println(opSub[1]);
			// No es capaz de encontrar la clase
			// ComunicacionViaEmail cVE = new ComunicacionViaEmail();
			// cVE.prueba();
			// cVE.enviarEmail(opSub[0], opSub[1]);

		}

	}

	@Override
	public void reporteVentas(int idMaquina, Date fechaInicial,
			Date fechaFinal, String[] detalle, Current current) {
		control.reporteVentas(idMaquina, fechaInicial, fechaFinal, detalle);

	}

	@Override
	public void desactivarAlarma(int idMaquina, int idAlarma, Date fechafinal, Current current) {

		control.desactivarAlarma(idAlarma, idMaquina, fechafinal);

	}

    
}
