package servicios;

import modelo.*;

import java.util.*;

import org.osoa.sca.annotations.*;

@Service
public interface ServicioCom_MQCafeSalida {
	
	public void cargarReceta_to_Maquinas(int idReceta, String nombreReceta, Double precioReceta, HashMap<Ingrediente, Double> listaIng);

}
