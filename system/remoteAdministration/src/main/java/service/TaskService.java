package service;

import java.util.Date;
import java.util.concurrent.Semaphore;

import model.OrdenEntrega;
import model.OrdenTrabajo;
import repository.AlarmasManager;
import repository.OrdenManager;
import servicios.CallbackPrx;
import servicios.MessageBrokerPrx;
import servicios.Moneda;

public class TaskService implements Runnable {

    public static final int ALARMA_INGREDIENTE = 1;
    public static final int ALARMA_MONEDA_CIEN = 2;
    public static final int ALARMA_MONEDA_DOS = 3;
    public static final int ALARMA_MONEDA_QUI = 4;
    public static final int ALARMA_SUMINISTRO = 5;
    public static final int ALARMA_MAL_FUNCIONAMIENTO = 6;

    private OrdenManager ordenManager;
    private AlarmasManager manager;
    private MessageBrokerPrx messageBroker;
    private CallbackPrx cb;
    private int type;
    private int idMaq;
    private String description;
    private Moneda moneda;
    private Semaphore sem;

    public TaskService(OrdenManager ordenManager, AlarmasManager manager, MessageBrokerPrx messageBroker,
            CallbackPrx cb, int type, int idMaq, Semaphore sem) {
        this.ordenManager = ordenManager;
        this.manager = manager;
        this.messageBroker = messageBroker;
        this.cb = cb;
        this.type = type;
        this.idMaq = idMaq;
        this.sem = sem;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method responsible for running the task
     */
    @Override
    public void run() {
        try {
            switch (type) {
                case 1:
                    recibirNotificacionEscasezIngredientes(this.description);
                    break;
                case 2:
                    recibirNotificacionInsuficienciaMoneda(this.moneda);
                    break;
                case 3:
                    recibirNotificacionEscasezSuministro();
                    break;
                case 4:
                    recibirNotificacionMalFuncionamiento();
                    break;
                case 5:
                    recibirNotificacionAbastesimiento();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recibirNotificacionEscasezIngredientes(String iDing) throws InterruptedException {
        sem.acquire();
        System.out.println(idMaq + " " + iDing);
        int alarmId = manager.alarmaMaquina(ALARMA_INGREDIENTE, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 0, 0, 1 });
        sem.release();
        feedback(ALARMA_INGREDIENTE, idMaq, alarmId, response, messageBroker, cb);
    }

    private void recibirNotificacionInsuficienciaMoneda(Moneda moneda) throws InterruptedException {
        sem.acquire();

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
        sem.release();

        feedback(alarmamoneda, idMaq, alarmId, response, messageBroker, cb);
    }

    private void recibirNotificacionEscasezSuministro() throws InterruptedException {
        sem.acquire();

        int alarmId = manager.alarmaMaquina(ALARMA_SUMINISTRO, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 0, 1, 0 });

        sem.release();

        feedback(ALARMA_SUMINISTRO, idMaq, alarmId, response, messageBroker, cb);
    }

    private void recibirNotificacionMalFuncionamiento() throws InterruptedException {
        sem.acquire();

        int alarmId = manager.alarmaMaquina(ALARMA_MAL_FUNCIONAMIENTO, idMaq, new Date());
        int[] response = createOrdenes(idMaq, alarmId, new int[] { 1, 0, 0 });
        sem.release();

        feedback(ALARMA_MAL_FUNCIONAMIENTO, idMaq, alarmId, response, messageBroker, cb);
    }

    private void recibirNotificacionAbastesimiento() throws InterruptedException {
        sem.acquire();

        manager.desactivarAlarma(ALARMA_INGREDIENTE, idMaq, new Date());
        sem.release();

        feedback(ALARMA_INGREDIENTE, idMaq, 0, new int[] {}, messageBroker, cb);
    }

    /**
     * Metodo que envia la confirmacion de la alarma and for debug porpuses
     */
    private void feedback(int typeAlarm, int idMaq, int alarmId, int[] type, MessageBrokerPrx messageBroker,
            CallbackPrx cb) {
        if (alarmId != 0)
            System.out.println("Orden de entrega: " + type[0] + " Orden de trabajo: " + type[1]
                    + "Asoicada a la alarma: " + alarmId);
        else
            System.out.println("Alarma repetida en el servidor");
        // Recibí la notificación, ahora debo enviar la confirmación
        System.out.println("Se ha llamado al callback");
        messageBroker.acknowledge(idMaq, typeAlarm, cb);

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
        if (alarmId != 0) {
            int entregaId = ordenManager
                    .insertOrdenEntrega(new OrdenEntrega(idMaq, alarmId, new Date(), type[0], type[1], type[2]));
            // En el componnete de logistica debe existir cuando se asigna un operador a la
            // orden de trabajo el respectivo update de la orden refereciada
            int trabajoId = ordenManager.insertOrdenTrabajo(new OrdenTrabajo(alarmId, idMaq, new Date(), entregaId));
            // Notificar a logistica y bodega ???
            return new int[] { entregaId, trabajoId };
        }
        return new int[] { 0, 0 };
    }
}
