package model;

import servicios.CallbackPrx;

public class Alarma extends AbstractClass {
    private int idAlarma;
    private Boolean isTerminated;
    private String message;
    private CallbackPrx cb;

    public Alarma(int idAlarma, int codMaquina, int externalType, Boolean isTerminated, String message, CallbackPrx cb) {
        super(codMaquina, externalType);
        this.idAlarma = idAlarma;
        this.isTerminated = isTerminated;
        this.message = message;
        this.cb = cb;
    }

    public int getIdAlarma() {
        return idAlarma;
    }

    public void setIdAlarma(int idAlarma) {
        this.idAlarma = idAlarma;
    }

    public Boolean getIsTerminated() {
        return isTerminated;
    }

    public CallbackPrx getCb() {
        return cb;
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
