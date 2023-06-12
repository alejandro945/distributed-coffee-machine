package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import model.ConexionBD;
import model.VentasMaquina;
import model.VentasReceta;
import servicios.VentaService;

public class VentasManager implements VentaService {

    private Communicator communicator;

    public VentasManager(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    public void registrarVenta(int codMaq, String[] ventas, Current current) {
        reporteVentas(codMaq, ventas);
    }

    public void reporteVentas(int idMaquina, String[] detalle) {
        ConexionBD cbd = new ConexionBD(communicator);
        cbd.conectarBaseDatos();
        ManejadorDatos md = new ManejadorDatos();
        md.setConexion(cbd.getConnection());

        VentasMaquina vM = new VentasMaquina();
        vM.setFechaInicial(new Date());
        vM.setFechaFinal(new Date());
        vM.setIdMaquina(idMaquina);

        List<VentasReceta> vR = new ArrayList<VentasReceta>();

        for (String dato : detalle) {

            String[] div = dato.split("#");
            VentasReceta vRTemp = new VentasReceta();
            vRTemp.setIdReceta(Integer.parseInt(div[0]));

            vRTemp.setValorreceta(Integer.parseInt(div[1]));

            vR.add(vRTemp);
        }

        vM.setDetalle(vR);
        vM.setValor(0);

        md.registrarReporteVentas(vM);
        cbd.cerrarConexion();
    }

}
