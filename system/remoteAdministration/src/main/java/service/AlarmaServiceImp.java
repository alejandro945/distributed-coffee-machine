package service;

import com.zeroc.Ice.Current;

import repository.AlarmasManager;
import repository.OrdenManager;
import servicios.AlarmaService;
import servicios.CallbackPrx;
import servicios.MessageBrokerPrx;
import servicios.Moneda;

public class AlarmaServiceImp implements AlarmaService {

    private AlarmasManager manager;
    private OrdenManager ordenManager;
    private ThreadPoolService handler = ThreadPoolService.getInstance();

    public AlarmaServiceImp(AlarmasManager manager, OrdenManager ordenManager) {
        this.manager = manager;
        this.ordenManager = ordenManager;
    }

    @Override
    public void recibirNotificacionEscasezIngredientes(String iDing, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        System.out.println("Callback machine en entry point" + cb);
        TaskService tk = new TaskService(ordenManager, manager, messageBroker, cb, 1, idMaq);
        tk.setDescription(iDing);
        handler.execute(tk);
    }

    @Override
    public void recibirNotificacionInsuficienciaMoneda(Moneda moneda, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        TaskService tk = new TaskService(ordenManager, manager, messageBroker, cb, 2, idMaq);
        tk.setMoneda(moneda);
        handler.execute(tk);
    }

    @Override
    public void recibirNotificacionEscasezSuministro(String idSumin, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 3, idMaq));
    }

    @Override
    public void recibirNotificacionMalFuncionamiento(int idMaq, String descri, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 4, idMaq));
    }

    /**
     * Caso de uso de resolucion de alarmas solo esta definido para el proceso de
     * abastecimiento
     * de ingredientes.
     */
    @Override
    public void recibirNotificacionAbastesimiento(int idMaq, String idInsumo, int cantidad,
            MessageBrokerPrx messageBroker, CallbackPrx cb, Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 5, idMaq));
    }

}
