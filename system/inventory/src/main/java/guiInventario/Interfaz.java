package guiInventario;

import java.util.Scanner;

import controller.ControladorBodega;

//import bodega.Bodega;
//import mantenimientoExistencias.Inventario;

public class Interfaz implements Runnable {

    private ControladorBodega cb;

    public Interfaz (ControladorBodega cb) {
        this.cb = cb;
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

						// guardarlo
						break;
					case 2:
                        lector.nextLine();
                        System.out.println(">>Codigo orden: ");
                        int codOrden = lector.nextInt();
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

    

}
