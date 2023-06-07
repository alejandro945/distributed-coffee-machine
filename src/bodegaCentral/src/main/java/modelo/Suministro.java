package modelo;

public class Suministro{

    private int idSuministro;
    private String nombre;
    private int cantidad;

    public Suministro(int idSuministro, String nombre, int cantidad) {
        super();
        this.idSuministro = idSuministro;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Suministro() {
        super();
    }

    public int getIdSuministro() {
        return idSuministro;
    }

    public void setIdSuministro(int idSuministro) {
        this.idSuministro = idSuministro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre= nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad= cantidad;
    }
}