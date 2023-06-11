package interfaces;

public interface IAlarma {
    public void notificarAbastecimiento();
    public void notificarReparacion();

    public void notificarError();

    public void notificarAusenciaMoneda();
    public void notificarEscazesIngredientes();
    public void notificarEscasezSuministros();
    public void notificarMalFuncionamiento();
}
