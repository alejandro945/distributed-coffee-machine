package model;

import java.util.Date;

public class OrdenEntrega {

    private int idOperador;
    private int idOrden;
    private int idMaquina;
    private int idKit;
    private int cantidad;
    private Date fecha;
    private int idSuministro;
    private int idIngrediente;
    private int idAlarma;

    public OrdenEntrega(int idOperador, int idOrden, int idMaquina, int idKit, int cantidad, Date fecha,
            int idSuministro, int idIngrediente, int idAlarma) {
        this.idOperador = idOperador;
        this.idOrden = idOrden;
        this.idMaquina = idMaquina;
        this.idKit = idKit;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idSuministro = idSuministro;
        this.idIngrediente = idIngrediente;
        this.idAlarma = idAlarma;
    }

    public OrdenEntrega() {
        super();
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdKit() {
        return idKit;
    }

    public void setIdKit(int idKit) {
        this.idKit = idKit;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdSuministro() {
        return idSuministro;
    }

    public void setIdSuministro(int idSuministro) {
        this.idSuministro = idSuministro;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }
}