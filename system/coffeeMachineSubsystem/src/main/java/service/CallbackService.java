package service;

import com.zeroc.Ice.Current;

import servicios.Callback;

public class CallbackService implements Callback {

    @Override
    public void alarmConfirmation(String data, Current current) {
        System.out.println("Alarma confirmada: " + data);
    }
    
}
