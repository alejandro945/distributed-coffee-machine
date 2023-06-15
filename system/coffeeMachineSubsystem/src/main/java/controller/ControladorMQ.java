package controller;

import servicios.*;
import ui.Interfaz;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import javax.swing.JFrame;
import java.awt.event.*;

import repository.RecetaRepositorio;
import repository.AlarmaRepositorio;
import repository.MonedasRepositorio;
import repository.VentaRepositorio;
import repository.IngredienteRepositorio;

import model.DepositoMonedas;
import model.Venta;
import model.Receta;
import model.Alarma;
import model.Ingrediente;

import com.zeroc.Ice.Current;

public class ControladorMQ implements Runnable, ServicioAbastecimiento {

	// @Atributes

	// @ Connection
	private VentaServicePrx ventasService;
	private ProxyServicePrx proxyServicePrx;
	private MessageBrokerPrx messageBrokerPrx;
	private CallbackPrx callbackPrx;

	// @UI Interface
	private Interfaz frame;
	private int codMaquina;
	private double suma;

	// @Data Access from LDB
	private AlarmaRepositorio alarmas = AlarmaRepositorio.getInstance();
	private IngredienteRepositorio ingredientes = IngredienteRepositorio.getInstance();
	private MonedasRepositorio monedas = MonedasRepositorio.getInstance();
	private RecetaRepositorio recetas = RecetaRepositorio.getInstance();
	private VentaRepositorio ventas = VentaRepositorio.getInstance();

	// @Constructor
	public ControladorMQ(ProxyServicePrx proxyServicePrx, VentaServicePrx ventasS, MessageBrokerPrx alarmaS) {
		this.proxyServicePrx = proxyServicePrx;
		this.ventasService = ventasS;
		this.messageBrokerPrx = alarmaS;
	}

	public void setCallbackPrx(CallbackPrx callbackPrx) {
		System.out.println("CallbackPrx setted" + callbackPrx);
		this.callbackPrx = callbackPrx;
	}

	// @Methods

	/**
	 * Method that starts the coffee machine
	 * Also we have call to other methods that help us to automatize the process
	 * of sending alarms to the proxy instance
	 */
	public void run() {
		try {
			/*
			 * frame = new Interfaz();
			 * frame.setLocationRelativeTo(null);
			 * frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			 * frame.setVisible(true);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		initMachine();
		verificarProductos();
		verificarMonedas();
		// eventos();
	}

	/**
	 * Method that initializes the events of the interface
	 */
	public void initMachine() {
		// Code
		readMachineCode();
		// Interface
		/*
		 * actualizarRecetasCombo();
		 * actualizarRecetasGraf();
		 * actualizarInsumosGraf();
		 * actualizarAlarmasGraf();
		 */
	}

	/**
	 * Method that reads the machine code from a file
	 */
	private void readMachineCode() {
		FileInputStream fstream;
		try {
			String path = "codMaquina.cafe";
			File file = new File(path);
			fstream = new FileInputStream(file);
			DataInputStream entrada = new DataInputStream(fstream);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			this.codMaquina = Integer.parseInt(buffer.readLine());
			entrada.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Machine code: " + this.codMaquina);
	}

	/**
	 * Method that acts as the logistic man that fix alarms of the coffee machine
	 */
	@Override
	public void abastecer(int codMaquina, int idAlarma, Current current) {
		System.out.println("Entra a abastecer maquina" + this.codMaquina);
		System.out.println("Orden de trabajo con id de alarma " + idAlarma + " para maquina " + codMaquina);

		if (codMaquina == this.codMaquina) {
			if (idAlarma == 1) {
				// Reference to the alarm maintainance
			}
			// Reference to the alarm with out cash
			else if (idAlarma == 2 | idAlarma == 3) {
				rechargeSpecificCurrency("100");
			} else if (idAlarma == 4 | idAlarma == 5) {
				rechargeSpecificCurrency("200");
			} else if (idAlarma == 6 | idAlarma == 7) {
				rechargeSpecificCurrency("500");
			} else if (idAlarma == 8 | idAlarma == 12) {
				// Reference to the alarm with out ingredients
				recargarIngredienteEspecifico("Agua");
			} else if (idAlarma == 9 | idAlarma == 13) {
				recargarIngredienteEspecifico("Cafe");
			} else if (idAlarma == 10 | idAlarma == 14) {
				recargarIngredienteEspecifico("Azucar");
			} else if (idAlarma == 11 | idAlarma == 15) {
				recargarIngredienteEspecifico("Vaso");
			}

			// Alarm Resolved locally
			alarmas.removeElement(idAlarma + "");

			// Enable Interface
			if (alarmas.getValues().isEmpty()) {
				frame.setEnabled(true);
				frame.interfazHabilitada();
				System.out.println("Entra al if de habilitacion");
			}

			// Respaldo
			respaldarMaq();

			// Update Interface
			actualizarRecetasGraf();
			actualizarInsumosGraf();
			actualizarAlarmasGraf();

			// DRIVER # 2 notify to the server alarm resolved @alexandersanchezjr
			// alarmaServicePrx.recibirNotificacionAbastesimiento(codMaquina, idAlarma + "",
			// cantidad);
			System.out.println("Se envía alarma de finalización en maquina" + codMaquina);
			messageBrokerPrx.queueAlarma(new servicios.Alarma(idAlarma, codMaquina, 0, true, ""), this.callbackPrx);
		}
	}

	/**
	 * Method that recharge a specific currency
	 * 40 is the maximum amount of coins that the machine can have
	 */
	private void rechargeSpecificCurrency(String currency) {
		DepositoMonedas moneda = monedas.findByKey(currency);
		moneda.setCantidad(40);
		monedas.addElement(currency, moneda);
	}

	/**
	 * Method that recharge a specific ingredient
	 * 
	 * @param ingrediente != "" || != null
	 */
	public void recargarIngredienteEspecifico(String ingrediente) {
		Ingrediente ing = ingredientes.findByKey(ingrediente);
		ing.setCantidad(ing.getMaximo());
		ingredientes.addElement(ingrediente, ing);
	}

	/**
	 * Ui methods for interface components
	 * User Interaction
	 */
	public void eventos() {

		frame.getBtnIngresar100().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int saldo = Integer.parseInt(frame.getTextAreaSaldo().getText());
				frame.getTextAreaSaldo().setText((saldo + 100) + "");
				suma += 100;
				DepositoMonedas moneda = monedas.findByKey("100");
				moneda.setCantidad(moneda.getCantidad() + 1);
				monedas.addElement("100", moneda);
				actualizarInsumosGraf();
			}
		});

		frame.getBtnIngresar200().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int saldo = Integer.parseInt(frame.getTextAreaSaldo().getText());
				frame.getTextAreaSaldo().setText((saldo + 200) + "");
				suma += 200;
				DepositoMonedas moneda = monedas.findByKey("200");
				moneda.setCantidad(moneda.getCantidad() + 1);
				monedas.addElement("200", moneda);
				actualizarInsumosGraf();

			}
		});

		frame.getBtnIngresar500().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int saldo = Integer.parseInt(frame.getTextAreaSaldo().getText());
				frame.getTextAreaSaldo().setText((saldo + 500) + "");
				suma += 500;
				DepositoMonedas moneda = monedas.findByKey("500");
				moneda.setCantidad(moneda.getCantidad() + 1);
				monedas.addElement("500", moneda);
				actualizarInsumosGraf();
			}
		});

		frame.getBtnCancelar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getTextAreaSaldo().setText("0");
				if (suma > 0) {
					frame.getTextAreaDevuelta()
							.setText(frame.getTextAreaDevuelta().getText() + "Se devolvio: " + suma + "\n");
					devolverMonedas();
				}
			}
		});

		frame.getBtnVerificar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int precio = 0;
				List<Receta> rec = recetas.getValues();
				for (int i = 0; i < rec.size(); i++) {
					if (frame.getComboBoxProducto().getSelectedItem().equals(rec.get(i).getDescripcion())) {
						precio = rec.get(i).getValor();
					}
				}
				frame.getTextAreaInfo().setText(frame.getTextAreaInfo().getText()
						+ "El producto cuesta: " + precio + "\n");
				frame.getTextAreaInfo().repaint();
			}
		});

		frame.getBtnOrdenar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int precio = 0;
				Receta temp = null;
				List<Receta> rec = recetas.getValues();
				for (int i = 0; i < rec.size(); i++) {

					temp = rec.get(i);

					if (frame.getComboBoxProducto().getSelectedItem()
							.equals(temp.getDescripcion())) {
						precio = rec.get(i).getValor();

						if (Integer.valueOf(frame.getTextAreaSaldo().getText()) >= precio) {

							frame.getTextAreaInfo().setText(
									frame.getTextAreaInfo().getText()
											+ "Se ordeno: "
											+ frame.getComboBoxProducto()
													.getSelectedItem()
											+ "\n");

							frame.getTextAreaSaldo().setText(
									Integer.valueOf(frame.getTextAreaSaldo()
											.getText()) - precio + "");

							suma -= precio;

							disminuirInsumos(temp);

							devolverMonedas();
							verificarProductos();

							String idV = rec.get(i).getId();
							ventas.addElement(idV, new Venta(frame.getComboBoxProducto()
									.getSelectedItem().toString(), idV,
									precio, new Date()));

							respaldarMaq();

							frame.getTextAreaSaldo().setText("0");

						} else {
							frame.getTextAreaInfo().setText(
									frame.getTextAreaInfo().getText()
											+ "Saldo insuficiente \n");

						}

					}

				}

			}

		});

		frame.getBtnMantenimiento().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Enviar Alarma por SCA
				Alarma temp = new Alarma("1", "Se requiere mantenimiento", new Date());
				frame.getTextAreaAlarmas().setText(
						frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Mantenimiento" + "\n");

				// DRIVER # 2 - Alarma de mal funcionamiento @alexandersanchezjr
				// alarmaServicePrx.recibirNotificacionMalFuncionamiento(codMaquina, "Se
				// requiere mantenimiento");
				System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo mal funcionamiento");
				messageBrokerPrx
						.queueAlarma(new servicios.Alarma(0, codMaquina, 6, false, "Se requiere mantenimiento"),
								callbackPrx);

				// LDB Adding alarm to the list
				alarmas.addElement("1", temp);
				frame.interfazDeshabilitada();
			}
		});

		// Schedule Action already dispatch every week
		frame.getBtnEnviarReporte().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Venta> vents = ventas.getValues();
				String[] arregloVentas = new String[vents.size()];
				for (int i = 0; i < arregloVentas.length; i++) {
					arregloVentas[i] = vents.get(i).getId() + "#"
							+ vents.get(i).getValor();
					System.out.println(arregloVentas[i]);
				}

				// DRIVER # 3 - Reporte de ventas @alexandersanchezjr
				ventasService.registrarVenta(codMaquina, arregloVentas);

			}
		});

		frame.getBtnActualizar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarRecetaMaquinas();
			}
		});
	}

	public String[] cargarInformacionReceta() {
		return null;
	}

	public void updateRecipes(String[] data) {
		for (int i = 0; i < data.length; i++) {

			String[] splitInicial = data[i].split("#");

			String[] receta = splitInicial[0].split("-");

			HashMap<Ingrediente, Double> listaIngredientes = new HashMap<Ingrediente, Double>();

			for (int i2 = 1; i2 < splitInicial.length; i2++) {

				String[] splitdeIng = splitInicial[i2].split("-");

				Ingrediente ingred = ingredientes.findByKey(splitdeIng[1]);
				if (ingred == null) {
					ingred = new Ingrediente(splitdeIng[1], splitdeIng[2], 500, 50, 1000, 1000);
				}
				listaIngredientes.put(ingred, Double.parseDouble(splitdeIng[4]));

			}

			Receta r = new Receta(receta[1], receta[0],
					Integer.parseInt(receta[2]), listaIngredientes);

			recetas.addElement(receta[0], r);
		}

		// Actualizar Archivo Plano
		recetas.saveData();
		actualizarInsumosGraf();
		actualizarRecetasGraf();
		actualizarRecetasCombo();
	}

	/**
	 * Method Manual for get recipes from proxy cache DRIVER 1 MANUAL
	 */
	public Boolean cargarRecetaMaquinas() {
		recetas.setElements(new HashMap<String, Receta>());
		long start = System.currentTimeMillis();
		String[] recetasServer = proxyServicePrx.consultarProductosProxy();
		long end = System.currentTimeMillis();
		System.out.println("Tiempo de ejecucion: " + (end - start) + "ms");

		updateRecipes(recetasServer);

		return true;
	}

	/**
	 * Method that save the information locally
	 */
	public void respaldarMaq() {
		alarmas.saveData();
		ingredientes.saveData();
		monedas.saveData();
		recetas.saveData();
		ventas.saveData();
	}

	/**
	 * Method that verify the products
	 */
	public void verificarProductos() {

		Iterator<Ingrediente> itIng = ingredientes.getValues().iterator();

		while (itIng.hasNext()) {
			Ingrediente ing = itIng.next();

			if (ing.getCantidad() <= ing.getMinimo()
					&& ing.getCantidad() > ing.getCritico()) {

				Alarma alIng = new Alarma(ing.getCodAlarma(),
						ing.getNombre(), new Date());

				if (alarmas.findByKey(ing.getCodAlarma()) == null) {
					// Update Alarms locally
					alarmas.addElement(ing.getCodAlarma(), alIng);
					// Driver # 2 - Alarma de escasez de ingredientes @alexandersanchezjr
					// alarmaServicePrx.recibirNotificacionEscasezIngredientes(ing.getNombre(),
					// codMaquina);
					System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez de ingredientes");
					messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 1, false, ing.getNombre()),
							callbackPrx);
					/*
					 * frame.getTextAreaAlarmas().setText(
					 * frame.getTextAreaAlarmas().getText()
					 * + "Se genero una alarma de Ingrediente: "
					 * + alIng.getMensaje() + "\n");
					 */

				}
			}

			if (ing.getCantidad() <= ing.getCritico()) {

				int codAlarma = Integer.parseInt(ing.getCodAlarma()) + 4;

				Alarma alIng = new Alarma(codAlarma + "", ing.getNombre(), new Date());
				// Update Alarms locally
				alarmas.addElement(codAlarma + "", alIng);
				// Driver # 2 - Alarma de escasez CRITICO de ingredientes @alexandersanchezjr
				// alarmaServicePrx.recibirNotificacionEscasezIngredientes(ing.getNombre(),
				// codMaquina);
				System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez CRITICO de ingredientes");
				messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 1, false, ing.getNombre()),
						callbackPrx);
				/*
				 * frame.getTextAreaAlarmas().setText(
				 * frame.getTextAreaAlarmas().getText()
				 * + "Se genero una alarma de: Critico de "
				 * + alIng.getMensaje() + "\n");
				 * 
				 * frame.interfazDeshabilitada();
				 */

			}

		}
	}

	/**
	 * Method that decrease the supplies
	 */
	public void disminuirInsumos(Receta r) {
		Iterator<Entry<Ingrediente, Double>> receta = r.getListaIngredientes()
				.entrySet().iterator();
		while (receta.hasNext()) {
			Map.Entry<Ingrediente, Double> ingRec = (Map.Entry<Ingrediente, Double>) receta.next();
			Ingrediente ingrediente = ingredientes.findByKey(ingRec.getKey().getNombre());
			ingrediente.setCantidad(ingrediente.getCantidad() - ingRec.getValue());
			ingredientes.addElement(ingrediente.getNombre(), ingrediente);
		}
		// Modificar XML
		actualizarInsumosGraf();
	}

	/**
	 * Method that update the alarms interface
	 */
	public void actualizarAlarmasGraf() {
		frame.getTextAreaAlarmas().setText("");
	}

	/**
	 * Method that update the Cash interface
	 */
	public void actualizarInsumosGraf() {
		frame.getTextAreaInsumos().setText("");
		// Update Ingredients interface
		Iterator<Ingrediente> it = ingredientes.getValues().iterator();
		while (it.hasNext()) {
			Ingrediente ing = it.next();
			frame.getTextAreaInsumos().setText(
					frame.getTextAreaInsumos().getText()
							+ ing.getNombre() + ": "
							+ ing.getCantidad() + "\n");

		}
		// Update Cash interface
		DepositoMonedas dep = monedas.findByKey("100");
		frame.getTextAreaInsumos().setText(
				frame.getTextAreaInsumos().getText() + "Deposito "
						+ dep.getTipo() + ": "
						+ dep.getCantidad() + "\n");
		dep = monedas.findByKey("200");
		frame.getTextAreaInsumos().setText(
				frame.getTextAreaInsumos().getText() + "Deposito "
						+ dep.getTipo() + ": "
						+ dep.getCantidad() + "\n");
		dep = monedas.findByKey("500");
		frame.getTextAreaInsumos().setText(
				frame.getTextAreaInsumos().getText() + "Deposito "
						+ dep.getTipo() + ": "
						+ dep.getCantidad() + "\n");
	}

	/**
	 * Method that update the recipes Graf
	 */
	public void actualizarRecetasGraf() {
		frame.getTextAreaRecetas().setText("");
		// Update Recipes interface
		Iterator<Receta> it2 = recetas.getValues().iterator();
		while (it2.hasNext()) {
			Receta temp = it2.next();
			frame.getTextAreaRecetas().setText(
					frame.getTextAreaRecetas().getText()
							+ temp.getDescripcion() + "\n");
		}
	}

	/**
	 * Method that update the recipes comboBox
	 */
	public void actualizarRecetasCombo() {
		// Reestablece Combobox
		frame.getComboBoxProducto().removeAllItems();
		// Update Recipes interface
		List<Receta> rec = recetas.getValues();
		for (int i = 0; i < rec.size(); i++) {
			frame.getComboBoxProducto().addItem(rec.get(i).getDescripcion());
		}
	}

	/**
	 * Method that return the change
	 */
	public void devolverMonedas() {
		// Metodo para devolver monedas
		int monedas100 = 0;
		int monedas200 = 0;
		int monedas500 = 0;
		if (suma / 500 > 0) {
			monedas500 += (int) suma / 500;
			DepositoMonedas moneda = monedas.findByKey("500");
			moneda.setCantidad(moneda.getCantidad() - monedas500);
			monedas.addElement("500", moneda);
			suma -= 500 * ((int) suma / 500);
		}

		if (suma / 200 > 0) {
			monedas200 += (int) suma / 200;
			DepositoMonedas moneda = monedas.findByKey("200");
			moneda.setCantidad(moneda.getCantidad() - monedas200);
			monedas.addElement("200", moneda);
			suma -= 200 * ((int) suma / 200);
		}
		if (suma / 100 > 0) {
			monedas100 += (int) suma / 100;
			DepositoMonedas moneda = monedas.findByKey("100");
			moneda.setCantidad(moneda.getCantidad() - monedas100);
			monedas.addElement("100", moneda);
			suma -= 100 * ((int) suma / 100);
		}
		if (suma != 0) {
			System.out.println("Ocurrio un error en dar devueltas: " + suma);
		}

		frame.getTextAreaDevuelta().setText(
				frame.getTextAreaDevuelta().getText() + "Se devolvieron: "
						+ monedas500 + " monedas de 500, " + monedas200
						+ " monedas de 200 y " + monedas100
						+ " monedas de 100 \n");

		actualizarInsumosGraf();
		verificarMonedas();
	}

	/**
	 * Mehotd that verify the coins
	 */
	public void verificarMonedas() {
		System.out.println("hola");
		// Monedas de 100
		DepositoMonedas moneda = monedas.findByKey("100");
		if (moneda.getCantidad() <= moneda.getMinimo() && moneda.getCantidad() > moneda.getCritico()) {
			Alarma alMon = new Alarma("2", "Faltan monedas de 100", new Date());
			if (alarmas.findByKey("2") == null) {
				// Update Alarms locally
				alarmas.addElement("2", alMon);
				// Driver # 2 - Alarma de escasez de monedas 100 @alexandersanchezjr
				// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.CIEN,
				// codMaquina);
				System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez de moedas de 100");
				messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 2, false, ""), callbackPrx);
				// frame.getTextAreaAlarmas().setText(
				// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Monedas de
				// 100" + "\n");
			}
		}
		if (moneda.getCantidad() <= moneda.getCritico()) {
			Alarma alMon = new Alarma("3", "ESTADO CRITICO: Faltan monedas de 100", new Date());
			// Update Alarms locally
			alarmas.addElement("3", alMon);
			// Driver # 2 - Alarma de escasez CRITICO de monedas 100 @alexandersanchezjr
			// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.CIEN,
			// codMaquina);
			System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez critica de monedas 100");
			messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 2, false, ""), callbackPrx);

			// frame.getTextAreaAlarmas().setText(
			// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Critica
			// Monedas de 100" + "\n");
			// frame.interfazDeshabilitada();
		}

		// Monedas de 200
		moneda = monedas.findByKey("200");
		if (moneda.getCantidad() <= moneda.getMinimo() && moneda.getCantidad() > moneda.getCritico()) {
			Alarma alMon = new Alarma("4", "Faltan monedas de 200", new Date());
			if (alarmas.findByKey("4") == null) {
				// Update Alarms locally
				alarmas.addElement("4", alMon);
				// Driver # 2 - Alarma de escasez de monedas 200 @alexandersanchezjr
				// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.DOCIENTOS,
				// codMaquina);
				System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez de monedas de 200");
				messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 3, false, ""), callbackPrx);

				// frame.getTextAreaAlarmas().setText(
				// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Mondedas de
				// 200" + "\n");
			}
		}
		if (moneda.getCantidad() <= moneda.getCritico()) {
			Alarma alMon = new Alarma("5", "ESTADO CRITICO: Faltan monedas de 200", new Date());
			// Update Alarms locally
			alarmas.addElement("5", alMon);
			// Driver # 2 - Alarma de escasez CRITICO de monedas 200 @alexandersanchezjr
			// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.DOCIENTOS,
			// codMaquina);
			System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez critica de monedas 200");
			messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 3, false, ""), callbackPrx);

			// frame.getTextAreaAlarmas().setText(
			// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Critica de
			// Monedas de 200" + "\n");
			// frame.interfazDeshabilitada();
		}

		// Monedas de 500
		moneda = monedas.findByKey("500");
		if (moneda.getCantidad() <= moneda.getMinimo() && moneda.getCantidad() > moneda.getCritico()) {
			Alarma alMon = new Alarma("6", "Faltan monedas de 500", new Date());
			if (alarmas.findByKey("6") == null) {
				// Update Alarms locally
				alarmas.addElement("6", alMon);
				// Driver # 2 - Alarma de escasez de monedas 500 @alexandersanchezjr
				// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.QUINIENTOS,
				// codMaquina);
				System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez de monedas de 500");
				messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 4, false, ""), callbackPrx);

				// frame.getTextAreaAlarmas().setText(
				// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Monedas de
				// 500" + "\n");
			}
		}
		if (moneda.getCantidad() <= moneda.getCritico()) {
			Alarma alMon = new Alarma("7", "ESTADO CRITICO: Faltan monedas de 500", new Date());
			// Update Alarms locally
			alarmas.addElement("7", alMon);
			// Driver # 2 - Alarma de escasez CRITICO de monedas 500 @alexandersanchezjr
			// alarmaServicePrx.recibirNotificacionInsuficienciaMoneda(Moneda.QUINIENTOS,
			// codMaquina);
			System.out.println("Se envía alarma en maquina" + codMaquina + " de tipo escasez critica de monedas 500");
			messageBrokerPrx.queueAlarma(new servicios.Alarma(0, codMaquina, 4, false, ""), callbackPrx);

			// frame.getTextAreaAlarmas().setText(
			// frame.getTextAreaAlarmas().getText() + "Se genero una alarma de: Critica
			// Monedas de 500" + "\n");
			// frame.interfazDeshabilitada();
		}
	}

}
