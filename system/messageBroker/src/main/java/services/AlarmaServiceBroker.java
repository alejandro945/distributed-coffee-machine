package services;

import com.zeroc.Ice.Current;

import repository.AlarmaRepository;
import servicios.Alarma;
import servicios.AlarmaServicePrx;
import servicios.MessageBroker;
import servicios.Moneda;

public class AlarmaServiceBroker implements MessageBroker {

    private AlarmaRepository alarmaRepository;

    private AlarmaServicePrx alarmaServicePrx;

    public AlarmaServiceBroker(AlarmaRepository alarmaRepository, AlarmaServicePrx alarmaServicePrx) {
        this.alarmaRepository = alarmaRepository;
        this.alarmaServicePrx = alarmaServicePrx;
    }

    @Override
    public void queueAlarma(Alarma am, Current current) {
        // Guarda en la capa de persistencia
        alarmaRepository.enqueue(
                new model.Alarma(am.idAlarma, am.codMaquina, am.externalType, am.isTerminated, am.message));
        sendNotifications(am);
    }

    @Override
    public boolean acknowledge(Current current) {
        model.Alarma am = alarmaRepository.dequeue();
        if (am != null) {
            return true;
        }
        return false;
    }

    public void sendNotifications(Alarma am) {
        if (am.isTerminated) {
            // Si la alarma es terminada, se envía a la capa de servicios
            alarmaServicePrx.recibirNotificacionAbastesimiento(am.codMaquina, am.idAlarma + "", 0);
            return;
        }

        if (am.externalType == 1) {
            alarmaServicePrx.recibirNotificacionEscasezIngredientes(am.message, am.codMaquina);
        } else if (am.externalType == 2) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.CIEN, am.codMaquina);
        } else if (am.externalType == 3) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.DOCIENTOS, am.codMaquina);
        } else if (am.externalType == 4) {
            alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.QUINIENTOS, am.codMaquina);
        } else if (am.externalType == 5) {
            alarmaServicePrx.recibirNotificacionEscasezSuministro(am.message, am.codMaquina);
        } else if (am.externalType == 6) {
            alarmaServicePrx.recibirNotificacionMalFuncionamiento(am.codMaquina, am.message);
        }
    }
}
