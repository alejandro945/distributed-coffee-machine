package services;

import repository.AlarmaRepository;

public class ThreadMessage extends Thread {

    private AlarmaServiceBroker alarmaServiceBroker;
    private AlarmaRepository alarmaRepository;

    public ThreadMessage(AlarmaServiceBroker alarmaServiceBroker, AlarmaRepository alarmaRepository) {
        this.alarmaServiceBroker = alarmaServiceBroker;
        this.alarmaRepository = alarmaRepository;
    }

    @Override
    public void run() {
        try {
            while (!alarmaRepository.getQueue().isEmpty()) {
                model.Alarma am = alarmaRepository.dequeue();
                if (am != null) {
                    alarmaServiceBroker.sendNotifications(new servicios.Alarma(am.getIdAlarma(), am.getCodMaquina(),
                            am.getExternalType(), am.getIsTerminated(), am.getMessage()));
                }
                Thread.sleep(1000);
            }
            Thread.sleep(120000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
