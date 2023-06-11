package interfaces;

public interface ISuministro {
    boolean verificatExistenciaSuministro(String sumId);
    String[] darInsumos();
    void dispensarSuministro(String sumId);
}
