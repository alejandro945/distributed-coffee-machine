package service;

import com.zeroc.Ice.Current;

import repository.AlarmasManager;
import repository.OrdenManager;
import servicios.AlarmaService;
import servicios.CallbackPrx;
import servicios.MessageBrokerPrx;
import servicios.Moneda;
import servicios.OrdenBodegaPrx;
import servicios.OrdenLogisticaPrx;

public class AlarmaServiceImp implements AlarmaService {

    private AlarmasManager manager;
    private OrdenManager ordenManager;
    private ThreadPoolService handler = ThreadPoolService.getInstance();
    private OrdenLogisticaPrx ordenLogistica;
    private OrdenBodegaPrx ordenBodega;

    public AlarmaServiceImp(AlarmasManager manager, OrdenManager ordenManager, OrdenLogisticaPrx ordenLogistica,
            OrdenBodegaPrx ordenBodega) {
        this.manager = manager;
        this.ordenManager = ordenManager;
        this.ordenLogistica = ordenLogistica;
        this.ordenBodega = ordenBodega;
    }

    public OrdenLogisticaPrx getOrdenLogistica() {
        return ordenLogistica;
    }

    public OrdenBodegaPrx getOrdenBodega() {
        return ordenBodega;
    }

    @Override
    public void recibirNotificacionEscasezIngredientes(String iDing, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        System.out.println("Callback machine en entry point" + cb);
        TaskService tk = new TaskService(ordenManager, manager, messageBroker, cb, 1, idMaq, handler.getSemaphore(), this);
        tk.setDescription(iDing);
        handler.execute(tk);
    }

    @Override
    public void recibirNotificacionInsuficienciaMoneda(Moneda moneda, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        TaskService tk = new TaskService(ordenManager, manager, messageBroker, cb, 2, idMaq, handler.getSemaphore(), this);
        tk.setMoneda(moneda);
        handler.execute(tk);
    }

    @Override
    public void recibirNotificacionEscasezSuministro(String idSumin, int idMaq, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 3, idMaq, handler.getSemaphore(), this));
    }

    @Override
    public void recibirNotificacionMalFuncionamiento(int idMaq, String descri, MessageBrokerPrx messageBroker,
            CallbackPrx cb,
            Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 4, idMaq, handler.getSemaphore(), this));
    }

    /**
     * Caso de uso de resolucion de alarmas solo esta definido para el proceso de
     * abastecimiento
     * de ingredientes.
     */
    @Override
    public void recibirNotificacionAbastesimiento(int idMaq, String idInsumo, int cantidad,
            MessageBrokerPrx messageBroker, CallbackPrx cb, Current current) {
        handler.execute(new TaskService(ordenManager, manager, messageBroker, cb, 5, idMaq, handler.getSemaphore(), this));
    }

}
