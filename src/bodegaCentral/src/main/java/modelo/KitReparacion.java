package model;

public class KitReparacion{

    private int idKit;
    private String nombre;
    private int cantidad;

    public KitReparacion(int idKit, String nombre, int cantidad) {
        super();
        this.idKit = idKit;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public KitReparacion() {
        super();
    }

    public int getIdKit() {
        return idKit;
    }

    public void setIdKit(int idKit) {
        this.idKit = idKit;
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