package interfazUsuario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Opciones extends JFrame{
	
	private JButton btnReabastecer;
	private JButton btnRefrescar;
	private JTextArea txtAsignaciones;
	private JTextArea txtAsignacionesRep;
	private JComboBox comboReparar;
	private JPanel panel;
	
	public Opciones(){
		setSize(400,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel=new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		this.setResizable(false);
		
		JLabel labtit1=new JLabel("Maquinas asignadas");
		JLabel labtit2=new JLabel("Maquinas que requieren atencion");
		JLabel labTit3=new JLabel("Seleccione maquina a reparar");
		
		txtAsignaciones=new JTextArea(10,10);
		txtAsignaciones.setEditable(false);
		
		JScrollPane scroll1=new JScrollPane(txtAsignaciones);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        txtAsignacionesRep=new JTextArea(10,10);
        txtAsignacionesRep.setEditable(false);
        
        JScrollPane scroll2=new JScrollPane(txtAsignacionesRep);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        comboReparar=new JComboBox();
        
        btnReabastecer=new JButton("Reabastecer");
        btnRefrescar=new JButton("Refrescar");
        
        panel.add(labtit1);
        labtit1.setBounds(120,15,200,30);
        
        panel.add(scroll1);
        scroll1.setBounds(10,60,380,150);
        
        panel.add(labtit2);
        labtit2.setBounds(80, 230, 240, 30);
        
        panel.add(scroll2);
        scroll2.setBounds(10, 275, 380, 150);
        
        panel.add(labTit3);
        labTit3.setBounds(80, 435, 240, 30);
        
        panel.add(comboReparar);
        comboReparar.setBounds(10, 470, 380, 30);
        
        panel.add(btnReabastecer);
        btnReabastecer.setBounds(50, 510, 150, 30);
        
        panel.add(btnRefrescar);
        btnRefrescar.setBounds(200, 510, 150, 30);
	}

	public JButton getBtnReabastecer() {
		return btnReabastecer;
	}

	public void setBtnReabastecer(JButton btnReabastecer) {
		this.btnReabastecer = btnReabastecer;
	}

	public JTextArea getTxtAsignaciones() {
		return txtAsignaciones;
	}

	public void setTxtAsignaciones(JTextArea txtAsignaciones) {
		this.txtAsignaciones = txtAsignaciones;
	}

	public JTextArea getTxtAsignacionesRep() {
		return txtAsignacionesRep;
	}

	public void setTxtAsignacionesRep(JTextArea txtAsignacionesRep) {
		this.txtAsignacionesRep = txtAsignacionesRep;
	}

	public JComboBox getComboReparar() {
		return comboReparar;
	}

	public void setComboReparar(JComboBox comboReparar) {
		this.comboReparar = comboReparar;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JButton getBtnRefrescar() {
		return btnRefrescar;
	}

	public void setBtnRefrescar(JButton btnRefrescar) {
		this.btnRefrescar = btnRefrescar;
	}
	
	
	

}
