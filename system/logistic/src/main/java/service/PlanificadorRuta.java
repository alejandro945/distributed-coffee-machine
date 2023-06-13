package service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import model.Operario;
import servicios.OperadorLogistica;

public class PlanificadorRuta implements OperadorLogistica{
    private Operario operario;
    private Communicator communicator;

    public PlanificadorRuta(Communicator com) {
        communicator = com;
    }

    @Override
    public void atenderOperador(Current current) {

        

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderOperador'");
    }


    
}
