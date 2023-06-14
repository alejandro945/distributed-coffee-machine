package controller;

import repository.LogisticManager;
import servicios.ServicioComLogistica;

import com.zeroc.Ice.*;
import java.util.*;

// Controller for expose iteraction between logistic and remote administration
public class ControlComLogistica implements ServicioComLogistica{
 
	private LogisticManager control;

	public ControlComLogistica(LogisticManager con) {
		control = con;
		/* ConsolaAdministracion cAdmin = new ConsolaAdministracion(con);
		Thread th = new Thread(cAdmin);
		th.start(); */
	}

    @Override
	public List<String> asignacionMaquina(int codigoOperador, Current current) {
		return control.listaAsignaciones(codigoOperador);
	}

	// Funciona correctamente
	@Override
	public List<String> asignacionMaquinasDesabastecidas(int codigoOperador, Current current) {
		return control.listaAsignacionesMDanada(codigoOperador);
	}

	@Override
	public boolean inicioSesion(int codigoOperador, String password, Current current) {
		return control.existeOperador(codigoOperador, password);
	}

}
