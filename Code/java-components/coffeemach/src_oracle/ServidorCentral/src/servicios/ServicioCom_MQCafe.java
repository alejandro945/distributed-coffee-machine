package servicios;

import java.util.Date;
import org.osoa.sca.annotations.Service;

@Service
public interface ServicioCom_MQCafe {

	public void alarma_maquina(int idAlarma, int idMaquina, Date fecha_inicial);
	public void reporte_ventas(int idMaquina, Date fechaInicial, Date fechaFinal, String[]detalle);
	public void desactivarAlarma(int idMaquina, int idAlarma, Date fecha_final);
}
