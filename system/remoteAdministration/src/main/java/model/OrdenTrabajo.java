package model;

import java.util.Date;

public class OrdenTrabajo {
    private int idAlarma;
    private int idOperador;
    private int idorden;
    private int idmaquina;
    private Date fecha;
    private int idOrdenEntrega;

    public OrdenTrabajo(int idAlarma, int idOperador, int idorden, int idmaquina, Date fecha, int idOrdenEntrega) {
        this.idAlarma = idAlarma;
        this.idOperador = idOperador;
        this.idorden = idorden;
        this.idmaquina = idmaquina;
        this.fecha = fecha;
        this.idOrdenEntrega = idOrdenEntrega;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdorden() {
        return idorden;
    }

    public void setIdorden(int idorden) {
        this.idorden = idorden;
    }

    public int getIdmaquina() {
        return idmaquina;
    }

    public void setIdmaquina(int idmaquina) {
        this.idmaquina = idmaquina;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdOrdenEntrega() {
        return idOrdenEntrega;
    }

    public void setIdOrdenEntrega(int idOrdenEntrega) {
        this.idOrdenEntrega = idOrdenEntrega;
    }

}
