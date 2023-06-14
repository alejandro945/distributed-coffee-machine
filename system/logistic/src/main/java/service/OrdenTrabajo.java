package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import repository.ConexionBD;
import repository.ManejadorDatos;
import servicios.OrdenBodegaPrx;
import servicios.OrdenLogistica;

public class OrdenTrabajo implements OrdenLogistica {

    private Communicator communicator;
    private OrdenBodegaPrx bodega;

    public OrdenTrabajo(Communicator com, OrdenBodegaPrx bodega) {
        communicator = com;
        this.bodega = bodega;
    }

    @Override
    public boolean confirmarOrden(int codOrden, Current current) {
        // Metodo llamado desde inventario para notificar que se ha entregado los
        // insumos al operador
        System.out.println("Recibimos confirmacion de entrega a nuestro operario");
       /*  boolean resultado = false;
        if (codOrden != 0) {
            ConexionBD connection = new ConexionBD(communicator);
            connection.conectarBaseDatos();
            ManejadorDatos dataManager = new ManejadorDatos();
            dataManager.setConexion(connection.getConnection());
            resultado = dataManager.confirmarOrden(codOrden);
            connection.cerrarConexion();
        } */
        System.out.println("El operador ha salido a abastecer la maquina");
        System.out.println("Final :)");
        return true;
    }

    @Override
    public void notificarOrdenTrabajo(int code,Current current) {
        System.out.println("Ha llegado una notificacion de alarma de orden de trabajo de alarma" + code);
        // Asignar operario a orden de trabajo
        /* if (code != 0) {
            ConexionBD connection = new ConexionBD(communicator);
            connection.conectarBaseDatos();
            ManejadorDatos dataManager = new ManejadorDatos();
            dataManager.setConexion(connection.getConnection());
            dataManager.asignarOperador(2,code);
            connection.cerrarConexion();
        } */
        System.out.println("El operador se le ha asignado una orden de trabajo");
        bodega.atenderOperador(2, code);
        System.out.println("Se ha enviado a Donatello por el pedido");
    }

}
