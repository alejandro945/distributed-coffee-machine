package controladorLogistica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;


import servicios.ServicioAbastecimientoPrx;
import servicios.ServicioComLogisticaPrx;
import interfazUsuario.Login;
import interfazUsuario.Opciones;

public class Control implements Runnable {

	private ServicioComLogisticaPrx servicioComLogistica;
		
	public void setServicioComLogistica(ServicioComLogisticaPrx servicioComLogistica) {
		this.servicioComLogistica = servicioComLogistica;
	}

	private ServicioAbastecimientoPrx servicioAbastecimiento;
	
	public void setServicioAbastecimiento(
		ServicioAbastecimientoPrx servicioAbastecimiento) {
		this.servicioAbastecimiento = servicioAbastecimiento;
	}

	private Login login;
	private Opciones opciones;
	private int codOp;


	@Override
	public void run() {
		
		login=new Login();
		login.setVisible(true);
		eventosLogin();
		System.out.println("run");
	}
	
	private void eventosLogin(){
		
		login.getBtnLogin().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String userName=login.getTextUserName().getText();
				String password=new String(login.getPassword().getPassword());
				codOp=Integer.parseInt(userName);
				
				
				boolean result=servicioComLogistica.inicioSesion(codOp, password);
				
				if(result){
					opciones=new Opciones();
					opciones.setVisible(true);
					cargarOpciones();
					eventosOpciones();
				}else{
					
					JOptionPane.showMessageDialog(login, "Nombre de usuario o contraseña incorrecta");
				}
				
			}
		});
		
	}
	
	private void cargarOpciones(){
		
		List<String>listaAs=servicioComLogistica.asignacionMaquina(codOp);
		List<String>listaAsD=servicioComLogistica.asignacionMaquinasDesabastecidas(codOp);
		
		String maqs="";
		
		for(String dato:listaAs){
			maqs+="Maquina id: "+dato+"\n";
		}
		opciones.getTxtAsignaciones().setText("");
		opciones.getTxtAsignaciones().setText(maqs);
		
		opciones.getComboReparar().removeAllItems();
		
		String maqs1="";
		for(String dato:listaAsD){
			String[]splitted=dato.split("#");
			maqs1+="Maquina: "+splitted[0]+" - Ubicación: "+splitted[1]+" - Fecha de fallo:"+splitted[2]+
					" - Fallo: "+splitted[4]+"\n";
			
			opciones.getComboReparar().addItem("Maquina #"+splitted[0]+"# IdAlarma: #"+splitted[3]+"# Fallo: #"+splitted[4]);
			
		}
		opciones.getTxtAsignacionesRep().setText("");
		opciones.getTxtAsignacionesRep().setText(maqs1);
		
	}
	
	private void eventosOpciones(){
		
		opciones.getBtnReabastecer().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Entra al evento del distribuidor");
				
				if(opciones.getComboReparar().getItemCount()<0){
					JOptionPane.showMessageDialog(opciones, "No hay maquinas por reparar");
				}
				
				String dato=opciones.getComboReparar().getSelectedItem().toString();
				
				if(dato!=null | dato!=""){
				String arr[]=dato.split("#");
				System.out.println("Lo que se selecciona: "+Integer.parseInt(arr[1])+"-"+Integer.parseInt(arr[3]));
				servicioAbastecimiento.abastecer(Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
				cargarOpciones();
				}
				
			}
		});
		
		opciones.getBtnRefrescar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cargarOpciones();
				
			}
		});
		
	}

}
