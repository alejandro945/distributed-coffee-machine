
module servicios{

    dictionary<string,int> MapStrInt;
    sequence<string> StringArr;
    ["java:type:java.util.ArrayList<String>"] 
    sequence<string> StringSeq;
    ["java:serializable:java.util.Date"]
    sequence<byte> Date;

    struct Alarma {
      int idAlarma;
      int codMaquina;
      int externalType;
      bool isTerminated;
      string message;
    }

    enum Moneda{
      CIEN, DOCIENTOS, QUINIENTOS
    }

    interface ServicioComLogistica{
      StringSeq asignacionMaquina(int codigoOperador);
	    StringSeq asignacionMaquinasDesabastecidas(int codigoOperador);
	    bool inicioSesion(int codigoOperador, string password);
    }

    interface ServicioAbastecimiento {
	   void abastecer(int codMaquina, int tipoAlarma);
    }

    interface MessageBroker{
      void queueAlarma(Alarma am);
      bool acknowledge(int code, int machine);    
    }

    interface AlarmaService{
      void recibirNotificacionEscasezIngredientes(string iDing, int idMaq, MessageBroker* broker);
      void recibirNotificacionInsuficienciaMoneda(Moneda moneda, int idMaq, MessageBroker* broker);
      void recibirNotificacionEscasezSuministro(string idSumin, int idMaq, MessageBroker* broker);
      void recibirNotificacionAbastesimiento(int idMaq, string idInsumo, int cantidad, MessageBroker* broker);
      void recibirNotificacionMalFuncionamiento(int idMaq, string descri, MessageBroker* broker);
    }

    interface VentaService{
      void registrarVenta(int codMaq, StringArr ventas);
    }

    interface RecetaService{
      StringArr consultarIngredientes();
	    StringArr consultarRecetas();
      StringArr consultarProductos();
      void definirProducto(string nombre, int precio, MapStrInt ingredientes);
	    void borrarReceta(int cod);
	    void definirRecetaIngrediente(int idReceta, int idIngrediente,int valor);
	    string registrarReceta(string nombre, int precio);
	    string registrarIngrediente(string nombre);
    }

    interface ProxyService{
      StringArr consultarIngredientesProxy();
      StringArr consultarRecetasProxy();
      StringArr consultarProductosProxy();
    }

    interface Observable{
      void update(StringArr data);
    }

    interface Observer{
      bool attach(Observable* subscriber);
      void notifyAll();
    }

    interface OrdenLogistica {
      bool confirmarOrden(int codOrden);
    }

    interface OperadorLogistica {
      bool atenderOperador(int codOp, int codOrden);
    }
    
}