package model;

public class Alarma extends AbstractClass {
    private int idAlarma;
    private Boolean isTerminated;
    private String message;

    public Alarma(int idAlarma, int codMaquina, int externalType, Boolean isTerminated, String message) {
        super(codMaquina, externalType);
        this.idAlarma = idAlarma;
        this.isTerminated = isTerminated;
        this.message = message;
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
