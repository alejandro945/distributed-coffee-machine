package service;

import interfaces.IAlarma;
import servicios.AlarmaServicePrx;

public class AlarmaService implements IAlarma {

    //private AlarmaServicePrx alarmaServicePrx;

    public void setAlarmaService(AlarmaServicePrx a) {
        //alarmaServicePrx = a;
    }

    @Override
    public void notificarAbastecimiento() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarAbastecimiento'");
    }

    @Override
    public void notificarReparacion() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarReparacion'");
    }

    @Override
    public void notificarEscasezSuministros() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarEscasezSuministros'");
    }

    @Override
    public void notificarError() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarError'");
    }

    @Override
    public void notificarAusenciaMoneda() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarAusenciaMoneda'");
    }

    @Override
    public void notificarEscazesIngredientes() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarEscazesIngredientes'");
    }

    @Override
    public void notificarMalFuncionamiento() {
        throw new UnsupportedOperationException("Unimplemented method 'notificarMalFuncionamiento'");
    }

}
