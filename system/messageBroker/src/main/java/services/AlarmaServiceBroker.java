package services;

import com.zeroc.Ice.Current;

import repository.AlarmaRepository;
import servicios.Alarma;
import servicios.AlarmaServicePrx;
import servicios.CallbackPrx;
import servicios.MessageBroker;
import servicios.MessageBrokerPrx;
import servicios.Moneda;

public class AlarmaServiceBroker implements MessageBroker {

    private MessageBrokerPrx messageBroker;
    private AlarmaRepository alarmaRepository;
    private AlarmaServicePrx alarmaServicePrx;

    public AlarmaServiceBroker(AlarmaRepository alarmaRepository, AlarmaServicePrx alarmaServicePrx) {
        this.alarmaRepository = alarmaRepository;
        this.alarmaServicePrx = alarmaServicePrx;
    }

    public void setMessageBroker(MessageBrokerPrx messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void queueAlarma(Alarma am, CallbackPrx cb, Current current) {
        // Guarda en la capa de persistencia
        System.out.println("Agregamos alarma en capa de persistencia de maquina" + am.codMaquina);
        alarmaRepository
                .add(new model.Alarma(am.idAlarma, am.codMaquina, am.externalType, am.isTerminated, am.message, cb));
        System.out.println("Notificamos al server central de la alarma de maquina " + am.codMaquina);
        sendNotifications(am, cb);
    }

    @Override
    public boolean acknowledge(int code, int type, CallbackPrx cb, Current current) {
        model.Alarma am = alarmaRepository.getElement(code, type);
        System.out.println("Se recibe feedback de parte del server acerca de notificacion de alarma de maquina " + code);
        if (am != null) {
            // Mark Delivered
            System.out.println("Tamaño antes" + alarmaRepository.getElements().size());
            alarmaRepository.remove(am);
            System.out.println("Tamaño despues" + alarmaRepository.getElements().size());
            cb.alarmConfirmation(code + " Fue confirmada y almacenada en la capa de persistencia");
            return true;
        }
        return false;
    }

    public void sendNotifications(Alarma am, CallbackPrx cb) {
        if (am.isTerminated) {
            // Si la alarma es terminada, se envía a la capa de servicios
            alarmaServicePrx.recibirNotificacionAbastesimiento(am.codMaquina, am.idAlarma + "", 0, messageBroker, cb);
            return;
        }

        if (am.externalType == 1) {
            alarmaServicePrx.recibirNotificacionEscasezIngredientes(am.message, am.codMaquina, messageBroker, cb);
        } else if (am.externalType == 2) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.CIEN, am.codMaquina, messageBroker, cb);
        } else if (am.externalType == 3) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.DOCIENTOS, am.codMaquina, messageBroker, cb);
        } else if (am.externalType == 4) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.QUINIENTOS, am.codMaquina, messageBroker,
                    cb);
        } else if (am.externalType == 5) {
            alarmaServicePrx.recibirNotificacionEscasezSuministro(am.message, am.codMaquina, messageBroker, cb);
        } else if (am.externalType == 6) {
            alarmaServicePrx.recibirNotificacionMalFuncionamiento(am.codMaquina, am.message, messageBroker, cb);
        }

    }
}
