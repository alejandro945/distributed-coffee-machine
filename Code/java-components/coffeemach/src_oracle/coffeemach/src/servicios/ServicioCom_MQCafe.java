package servicios;

import java.util.Date;
import java.util.List;

import org.osoa.sca.annotations.Service;

import modelo.*;

@Service
public interface ServicioCom_MQCafe {

	public void alarma_maquina(int id, int idMaquina, Date fecha_inicial);

	public void reporte_ventas(int idMaquina, Date fechaInicial,
			Date fechaFinal, String[] detalle);
	
	public void desactivarAlarma(int idMaquina, int idAlarma, Date fecha_final);

}
