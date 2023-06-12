package repository;

import model.Venta;

public class VentasRepository extends IRepositorio<String, Venta> {

    private static VentasRepository instance;

    public static VentasRepository getInstance() {
        if (instance == null) {
            instance = new VentasRepository();
        }
        return instance;
    }

    private VentasRepository() {
        super("ventas.bd");
    }

    public void loadDataP() {

    }
}