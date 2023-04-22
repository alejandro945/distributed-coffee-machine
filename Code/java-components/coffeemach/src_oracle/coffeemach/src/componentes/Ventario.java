package componentes;

import java.util.ArrayList;

import modelo.Venta;

public class Ventario {
	
	private ArrayList<Venta> listaVentas;

	public Ventario(ArrayList<Venta> listaVentas) {
		super();
		this.listaVentas = listaVentas;
	}

	public ArrayList<Venta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(ArrayList<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}
	
	

}
