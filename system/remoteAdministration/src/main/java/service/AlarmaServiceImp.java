package service;

import java.util.Date;

import com.zeroc.Ice.Current;

import model.OrdenEntrega;
import model.OrdenTrabajo;
import repository.AlarmasManager;
import repository.OrdenManager;
import servicios.AlarmaService;
import servicios.MessageBrokerPrx;
import servicios.Moneda;

public class AlarmaServiceImp implements AlarmaService {

    public static final int ALARMA_INGREDIENTE = 1;
    public static final int ALARMA_MONEDA_CIEN = 2;
    public static final int ALARMA_MONEDA_DOS = 3;
    public static final int ALARMA_MONEDA_QUI = 4;
    public static final int ALARMA_SUMINISTRO = 5;
    public static final int ALARMA_MAL_FUNCIONAMIENTO = 6;

    private AlarmasManager manager;
    private OrdenManager ordenManager;

    public AlarmaServiceImp(AlarmasManager manager, OrdenManager ordenManager) {
        this.manager = manager;
        this.ordenManager = ordenManager;
    }

    /**
     * Crea las ordenes de trabajo y entrega asociadas a la alarma
     * 
     * @param idMaq
     * @param alarmId
     * @param type    Arreglo cion posiciones 1 = Kit, 2 = Suministro, 3 =
     *                Ingrediente
     */
    private int[] createOrdenes(int idMaq, int alarmId, int[] type) {
        // En el componente de bodega cuando el operador se dirija hacia las
        // instalaciones y se le entregue el paquete se debe actualizar la orden de
        // entrega con el operador que la recibio
        // Ademas se debe actualizar el id del paquete asociado y cunato se necesita
        // para la entrega
        int entregaId = ordenManager
                .insertOrdenEntrega(new OrdenEntrega(idMaq, alarmId, new Date(), type[0], type[1], type[2]));
        // En el componnete de logistica debe existir cuando se asigna un operador a la
        // orden de trabajo el respectivo update de la orden refereciada
        int trabajoId = ordenManager.insertOrdenTrabajo(new OrdenTrabajo(alarmId, idMaq, new Date(), entregaId));
        // Notificar a logistica y bodega ???
        return new int[] { entregaId, trabajoId };
    }

    /**
     * Metodo que envia la confirmacion de la alarma and for debug porpuses
     */
    private void feedback(int typeAlarm, int idMaq, int alarmId, int[] type, MessageBrokerPrx messageBroker) {
        if (alarmId == 0)
            System.out.println("Orden de entrega: " + type[0] + " Orden de trabajo: " + type[1]
                    + "Asoicada a la alarma: " + alarmId);
        else
            System.out.println("Alarma de finalización desactivada correctamente");
        // Recibí la notificación, ahora debo enviar la confirmación
        messageBroker.acknowledge(typeAlarm, idMaq);
    }

    @Override
    public void recibirNotificacionEscasezIngredientes(String iDing, int idMaq, MessageBrokerPrx messageBroker,
            Current current) {
        System.out.println(idMaq + " " + iDing);
        int alarmId = manager.alarmaMaquina(ALARMA_INGREDIENTE, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 0, 0, 1 });
        feedback(ALARMA_INGREDIENTE, idMaq, alarmId, response, messageBroker);
    }

    @Override
    public void recibirNotificacionInsuficienciaMoneda(Moneda moneda, int idMaq, MessageBrokerPrx messageBroker,
            Current current) {
        int alarmId = 0;
        int alarmamoneda = 0;
        switch (moneda) {
            case CIEN:
                alarmId = manager.alarmaMaquina(ALARMA_MONEDA_CIEN, idMaq, new Date());
                alarmamoneda = ALARMA_MONEDA_CIEN;
                break;
            case DOCIENTOS:
                alarmId = manager.alarmaMaquina(ALARMA_MONEDA_DOS, idMaq, new Date());
                alarmamoneda = ALARMA_MONEDA_DOS;
                break;
            case QUINIENTOS:
                alarmId = manager.alarmaMaquina(ALARMA_MONEDA_QUI, idMaq, new Date());
                alarmamoneda = ALARMA_MONEDA_QUI;
                break;
            default:
                break;
        }
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 1, 0, 0 });
        feedback(alarmamoneda, idMaq, alarmId, response, messageBroker);
    }

    @Override
    public void recibirNotificacionEscasezSuministro(String idSumin, int idMaq, MessageBrokerPrx messageBroker,
            Current current) {
        int alarmId = manager.alarmaMaquina(ALARMA_SUMINISTRO, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 0, 1, 0 });
        feedback(ALARMA_SUMINISTRO, idMaq, alarmId, response, messageBroker);
    }

    @Override
    public void recibirNotificacionMalFuncionamiento(int idMaq, String descri, MessageBrokerPrx messageBroker,
            Current current) {
        int alarmId = manager.alarmaMaquina(ALARMA_MAL_FUNCIONAMIENTO, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 1, 0, 0 });
        feedback(ALARMA_MAL_FUNCIONAMIENTO, idMaq, alarmId, response, messageBroker);
    }

    /**
     * Caso de uso de resolucion de alarmas solo esta definido para el proceso de
     * abastecimiento
     * de ingredientes.
     */
    @Override
    public void recibirNotificacionAbastesimiento(int idMaq, String idInsumo, int cantidad,
            MessageBrokerPrx messageBroker, Current current) {
        manager.desactivarAlarma(ALARMA_INGREDIENTE, idMaq, new Date());
        feedback(ALARMA_INGREDIENTE, idMaq, 0, new int[] {}, messageBroker);
    }

}
