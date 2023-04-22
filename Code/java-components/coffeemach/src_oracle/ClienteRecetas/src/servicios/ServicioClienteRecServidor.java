package servicios;

import org.osoa.sca.annotations.Service;

@Service
public interface ServicioClienteRecServidor {
	
	public void asociar(int idReceta, int idIngrediente, int valor);

	public String[] consultarIngredientes();
	
	public String[] consultarRecetas();
	
	public void borrarReceta(int cod);
	
	public void registrarReceta_Ingrediente(int idReceta, int idIngrediente,
			int valor);
	
	public String registrarReceta(String nombre, int precio);
	
	public String registrarIngrediente(String nombre);
	
	
}
