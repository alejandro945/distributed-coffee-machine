package model;

import java.io.Serializable;

public class Alarma implements Serializable {
    private int idAlarma;
    private int codMaquina;
    private int externalType;
    private Boolean isTerminated;
    private String message;

    public Alarma(int idAlarma, int codMaquina, int externalType, Boolean isTerminated, String message) {
        this.idAlarma = idAlarma;
        this.codMaquina = codMaquina;
        this.externalType = externalType;
        this.isTerminated = isTerminated;
        this.message = message;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public int getCodMaquina() {
        return codMaquina;
    }

    public void setCodMaquina(int codMaquina) {
        this.codMaquina = codMaquina;
    }

    public int getExternalType() {
        return externalType;
    }

    public void setExternalType(int externalType) {
        this.externalType = externalType;
    }

    public Boolean getIsTerminated() {
        return isTerminated;
    }

    public void setIsTerminated(Boolean isTerminated) {
        this.isTerminated = isTerminated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
