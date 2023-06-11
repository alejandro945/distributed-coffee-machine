package repository;

import interfaces.IRepositorio;
import model.Alarma;

public class AlarmaRepositorio extends IRepositorio<String, Alarma> {

    private static AlarmaRepositorio instance;

    public static AlarmaRepositorio getInstance() {
        if (instance == null) {
            instance = new AlarmaRepositorio();
        }
        return instance;
    }

    private AlarmaRepositorio() {
        super("alarmas.bd");
    }

    public void loadDataP() {

    }

}
