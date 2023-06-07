package modelo;

public class Monedas {

    private int idMoneda;
    private String nombre;
    private int cantidad;

    public Monedas(int idMoneda, String nombre, int cantidad) {
        super();
        this.idMoneda = idMoneda;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Monedas() {
        super();
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}