package componentes;

import java.io.Serializable;
import java.util.HashMap;

import modelo.Ingrediente;

public class Ingredientario implements Serializable{
	
	private HashMap<Ingrediente, Double> listaIngredientes;

	public Ingredientario(HashMap<Ingrediente, Double> listaIngredientes) {
		super();
		this.listaIngredientes = listaIngredientes;
	}

	public HashMap<Ingrediente,Double> getListaIngredientes() {
		return listaIngredientes;
	}

	public void setListaIngredientes(HashMap<Ingrediente, Double> listaIngredientes) {
		this.listaIngredientes = listaIngredientes;
	}
	

}
