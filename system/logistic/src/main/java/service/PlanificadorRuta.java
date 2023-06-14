package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

public class PlanificadorRuta{

    private Communicator communicator;

    public PlanificadorRuta(Communicator com) {
        communicator = com;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public void setCommunicator(Communicator com) {
        communicator = com;
    }
    
    public boolean getRuta(int codOp, int codOrden, Current current) {
       return true;
    }

}
