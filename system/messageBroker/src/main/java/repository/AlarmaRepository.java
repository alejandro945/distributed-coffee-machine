package repository;

import model.Alarma;

public class AlarmaRepository extends IRepositorio<Alarma> {

    private static AlarmaRepository instance;

    public static AlarmaRepository getInstance() {
        if (instance == null) {
            instance = new AlarmaRepository();
        }
        return instance;
    }

    private AlarmaRepository() {
        super("alarmas.bd");
    }

    public void loadDataP() {

    }

}