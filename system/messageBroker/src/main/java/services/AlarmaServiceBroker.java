package services;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import servicios.AlarmaService;
import servicios.Moneda;

public class AlarmaServiceBroker implements AlarmaService {

    private Communicator communicator;

    public AlarmaServiceBroker(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void recibirNotificacionEscasezIngredientes(String iDing, int idMaq, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirNotificacionEscasezIngredientes'");
    }

    @Override
    public void recibirNotificacionInsuficienciaMoneda(Moneda moneda, int idMaq, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirNotificacionInsuficienciaMoneda'");
    }

    @Override
    public void recibirNotificacionEscasezSuministro(String idSumin, int idMaq, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirNotificacionEscasezSuministro'");
    }

    @Override
    public void recibirNotificacionAbastesimiento(int idMaq, String idInsumo, int cantidad, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirNotificacionAbastesimiento'");
    }

    @Override
    public void recibirNotificacionMalFuncionamiento(int idMaq, String descri, Current current) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recibirNotificacionMalFuncionamiento'");
    }

}
