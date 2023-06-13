package model;

import java.util.Date;

public class Venta extends AbstractClass {
    private String nombre, id;
    private int valor;
    private Date fecha;

    public Venta(String nombre, String id, int valor, Date fecha) {
        super(id, valor);
        this.nombre = nombre;
        this.id = id;
        this.valor = valor;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
