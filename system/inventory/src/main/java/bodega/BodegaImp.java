package bodega;

public class BodegaImp implements Bodega {

    @Override
    public void consultarMonedas() {
        System.out.println("Consultando monedas");
    }

    @Override
    public void consultarIngredientes() {
        System.out.println("Consultando ingredientes");
    }

    @Override
    public void consultarSuministros() {
        System.out.println("Consultando suministros");
    }

    @Override
    public void entregaKitReparacion() {
        System.out.println("Entregando kit de reparación");
    }

    @Override
    public void retirarExistencias() {
        System.out.println("Retirando existencias");
    }

    @Override
    public void abastecerExistencia() {
        System.out.println("Abasteciendo existencias");
    }

    @Override
    public void separarExistencias() {
        System.out.println("Separando existencias");
    }
}
