package repository;

import interfaces.IRepositorio;
import model.Venta;

public class VentaRepositorio extends IRepositorio<String, Venta> {

    private static VentaRepositorio instance;

    public static VentaRepositorio getInstance() {
        if (instance == null) {
            instance = new VentaRepositorio();
        }
        return instance;
    }

    private VentaRepositorio() {
        super("ventas.bd");
    }

    public void loadDataP() {

    }
}
