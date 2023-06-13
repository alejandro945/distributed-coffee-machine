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
            for (model.Alarma am : alarmaRepository.getElements()) {
                if (am != null) {
                    alarmaServiceBroker.sendNotifications(new servicios.Alarma(am.getIdAlarma(), am.getCode(),
                            am.getType(), am.getIsTerminated(), am.getMessage()));
                }
                Thread.sleep(1000);
            }
            Thread.sleep(120000);
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
