package servicios;

import org.osoa.sca.annotations.Service;

@Service
public interface ServicioAbastecimiento {

	public void abastecer(int codMaquina, int tipoAlarma);
}
