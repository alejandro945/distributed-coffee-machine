package modelo;

public class IngredientesInv{

    private int idIngrediente;
    private int cantidad;

    public IngredientesInv(int idIngrediente, int cantidad) {
        super();
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public IngredientesInv() {
        super();
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}