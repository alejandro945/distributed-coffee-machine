package servicios;

import java.util.List;

import org.osoa.sca.annotations.Service;

@Service
public interface ServicioCom_Logistica {

	public List<String>asignacionMaquina(int codigoOperador);
	public List<String>asignacionMaquinasDesabastecidas(int codigoOperador);
	public boolean inicioSesion(int codigoOperador, String password);
}
