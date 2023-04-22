package componentes;

import java.io.Serializable;
import java.util.ArrayList;

import modelo.Receta;

public class Recetario implements Serializable{
	
	private ArrayList<Receta> listaRecetas;

	public Recetario(ArrayList<Receta> listaRecetas) {
		super();
		this.listaRecetas = listaRecetas;
	}

	public ArrayList<Receta> getListaRecetas() {
		return listaRecetas;
	}

	public void setListaRecetas(ArrayList<Receta> listaRecetas) {
		this.listaRecetas = listaRecetas;
	}
	
	

}
