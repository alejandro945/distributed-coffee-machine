package componentes;

import java.util.ArrayList;

import modelo.Alarma;

public class Alarmario {

	private ArrayList<Alarma> listaAlarmas;

	public Alarmario() {
		super();
		this.listaAlarmas = new ArrayList<Alarma>();
	}

	public ArrayList<Alarma> getListaAlarmas() {
		return listaAlarmas;
	}

	public void setListaAlarmas(ArrayList<Alarma> listaAlarmas) {
		this.listaAlarmas = listaAlarmas;
	}

}
