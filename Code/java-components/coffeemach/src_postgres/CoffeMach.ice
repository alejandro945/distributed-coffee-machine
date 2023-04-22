
module servicios{

    sequence<string> StringArr;
    ["java:type:java.util.ArrayList<String>"] 
    sequence<string> StringSeq;
    ["java:serializable:java.util.Date"]
    sequence<byte> Date;


    interface ServicioClienteRecServidor{
	    StringArr consultarIngredientes();
	    StringArr consultarRecetas();
	    void borrarReceta(int cod);
	    void registrarRecetaIngrediente(int idReceta, int idIngrediente,
			int valor);
	    string registrarReceta(string nombre, int precio);
	    string registrarIngrediente(string nombre);
    }

    interface ServicioComLogistica{

      StringSeq asignacionMaquina(int codigoOperador);
	    StringSeq asignacionMaquinasDesabastecidas(int codigoOperador);
	    bool inicioSesion(int codigoOperador, string password);
    }

    interface ServicioComMQCafe {

	    void alarmaMaquina(int idAlarma, int idMaquina, Date fechaInicial);
	    void reporteVentas(int idMaquina, Date fechaInicial, Date fechaFinal, StringArr detalle);
	    void desactivarAlarma(int idMaquina, int idAlarma, Date fechaFinal);
    }
    interface ServicioProxyServer {
	    StringArr update();
    }
    interface ServicioAbastecimiento {
	   void abastecer(int codMaquina, int tipoAlarma);
    }

}