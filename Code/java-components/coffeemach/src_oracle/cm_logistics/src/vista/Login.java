package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {

	private JButton btn_Login;
	private JTextField textUserName;
	private JPasswordField password;
	private JPanel panel;

	public Login(){
		
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel=new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		this.setResizable(false);
		
		JLabel labelNombre=new JLabel("Usuario");
		JLabel labelPass=new JLabel("Contraseña");
		
		textUserName=new JTextField();
		password=new JPasswordField();
		btn_Login=new JButton("Ingresar");
		
		panel.add(labelNombre);
		labelNombre.setBounds(10, 30, 100, 30);
		
		panel.add(labelPass);
		labelPass.setBounds(10, 80, 100, 30);
		
		panel.add(textUserName);
		textUserName.setBounds(120, 30, 150, 30);
		
		panel.add(password);
		password.setBounds(120, 80, 150, 30);
		
		panel.add(btn_Login);
		btn_Login.setBounds(90, 130, 100, 30);
	}

	public JButton getBtn_Login() {
		return btn_Login;
	}

	public JTextField getTextUserName() {
		return textUserName;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public JPanel getPanel() {
		return panel;
	}
}
