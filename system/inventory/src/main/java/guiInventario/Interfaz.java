package guiInventario;

import java.util.Scanner;

import com.zeroc.Ice.Communicator;

import controller.ControladorBodega;
import servicios.OperadorLogisticaPrx;
import servicios.OrdenLogisticaPrx;

//import bodega.Bodega;
//import mantenimientoExistencias.Inventario;

public class Interfaz implements Runnable {

    private ControladorBodega controlador;

    public Interfaz (OperadorLogisticaPrx operador, OrdenLogisticaPrx orden, Communicator com) {
        controlador = new ControladorBodega(operador, orden, com);
    }

    @Override
    public void run() {
        while (true) {
			try (Scanner lector = new Scanner(System.in)) {
				System.out.println("****************************");
				System.out.println("Consola de Bodega");
				System.out.println("****************************");

				System.out.println("\nOpciones:" +
						"\n1.Atender operador" +
						"\n2.Confirmar orden" +
						"\n3.Comprar suministros" +
						"\n4.Consultar suministros");
				int valor = lector.nextInt();

				switch (valor) {
					case 1:
						lector.nextLine();
						System.out.println(">>Codigo perador atendido: ");
						int codOp = lector.nextInt();
						lector.nextLine();
                        System.out.println(">>Codigo orden: ");
                        int codOrden = lector.nextInt();
                        lector.nextLine();

                        atenderOperador(codOp, codOrden);

						// guardarlo
						break;
					case 2:
                        lector.nextLine();
                        System.out.println(">>Codigo orden: ");
                        // int codOrden = lector.nextInt();
                        lector.nextLine();

                        // guardarlo
						break;
					case 3:

                        // guardarlo
                
						break;
					case 4:
						break;
					case 5:
						break;
					default:
						System.out.println("¡¡¡Opción incorrecta seleccionada!!!");
						break;
				}
			}
		}


    }

    private void atenderOperador (int codOp, int codOrden) {
        controlador.atenderOperador(codOp, codOrden);
    }

    private void confirmarOrden (int codOrden) {
        controlador.confirmarOrden(codOrden);
    }

    private void comprarSuministros () {
        controlador.comprarSuministros();
    }

    private void consultarSuministros () {
        controlador.consultarSuministros();
    }

}
