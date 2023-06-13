package repository;

import interfaces.IRepositorio;
import model.Ingrediente;

public class IngredienteRepositorio extends IRepositorio<String, Ingrediente> {

  private static IngredienteRepositorio instance;

  public static IngredienteRepositorio getInstance() {
    if (instance == null) {
      instance = new IngredienteRepositorio();
    }
    return instance;
  }

  private IngredienteRepositorio() {
    super("ingredientes.bd");
  }

  public void loadDataP() {
    Ingrediente agua = new Ingrediente("Agua", "8", (double) 500, (double) 50,
        (double) 1000, 5);
    Ingrediente cafe = new Ingrediente("Cafe", "9", (double) 500, (double) 50,
        (double) 1000, 6000);
    Ingrediente azucar = new Ingrediente("Azucar", "10", (double) 500, (double) 50,
        (double) 1000, 6000);
    Ingrediente vaso = new Ingrediente("Vaso", "11", (double) 50, (double) 5,
        (double) 200, 6000);

    addElement("Agua", agua);
    addElement("Cafe", cafe);
    addElement("Azucar", azucar);
    addElement("Vaso", vaso);

  }
}
