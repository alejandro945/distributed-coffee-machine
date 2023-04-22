package interfaz;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class InterfazRecetas extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNombreRec,textFieldNombreIng, textFieldPrecioRec, textFieldAsociacion;
	private JButton btnAgregarReceta, btnAgregarIngrediente, btnBorrarReceta, btnRIC;
	private JTextArea textAreaRecetas, textAreaIngredientes;
	
	public JTextArea getTextAreaRecetas() {
		return textAreaRecetas;
	}

	public JTextArea getTextAreaIngredientes() {
		return textAreaIngredientes;
	}

	/**
	 * Create the frame.
	 */
	public InterfazRecetas() {
		setTitle("Interfaz Recetas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Recetas");
		lblNewLabel.setBounds(10, 180, 69, 14);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 205, 178, 226);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 157, 204);
		panel.add(scrollPane);
		
		textAreaRecetas = new JTextArea();
		scrollPane.setViewportView(textAreaRecetas);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(215, 208, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblIngrediente = new JLabel("Agregar Ingrediente");
		lblIngrediente.setBounds(215, 11, 178, 14);
		contentPane.add(lblIngrediente);
		
		textFieldNombreRec = new JTextField();
		textFieldNombreRec.setBounds(294, 205, 130, 20);
		contentPane.add(textFieldNombreRec);
		textFieldNombreRec.setColumns(10);
		
		textFieldNombreIng = new JTextField();
		textFieldNombreIng.setBounds(294, 43, 130, 20);
		contentPane.add(textFieldNombreIng);
		textFieldNombreIng.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(215, 243, 46, 14);
		contentPane.add(lblPrecio);
		
		JLabel lblIngrediente_1 = new JLabel("Ingredientes");
		lblIngrediente_1.setBounds(10, 11, 79, 14);
		contentPane.add(lblIngrediente_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 28, 178, 145);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 11, 158, 123);
		panel_1.add(scrollPane_1);
		
		textAreaIngredientes = new JTextArea();
		scrollPane_1.setViewportView(textAreaIngredientes);
		
		textFieldPrecioRec = new JTextField();
		textFieldPrecioRec.setBounds(294, 240, 130, 20);
		contentPane.add(textFieldPrecioRec);
		textFieldPrecioRec.setColumns(10);
		
		JLabel lblNombre_1 = new JLabel("Nombre");
		lblNombre_1.setBounds(215, 46, 69, 14);
		contentPane.add(lblNombre_1);
		
		JLabel lblAsociarRecetaingrediente = new JLabel("Asociar Receta-Ingrediente-Cantidad");
		lblAsociarRecetaingrediente.setBounds(215, 342, 209, 14);
		contentPane.add(lblAsociarRecetaingrediente);
		
		textFieldAsociacion = new JTextField();
		textFieldAsociacion.setBounds(215, 368, 209, 20);
		contentPane.add(textFieldAsociacion);
		textFieldAsociacion.setColumns(10);
		
		btnAgregarReceta = new JButton("Agregar Receta");
		btnAgregarReceta.setBounds(215, 275, 209, 23);
		contentPane.add(btnAgregarReceta);
		
		btnAgregarIngrediente = new JButton("Agregar Ingrediente");
		btnAgregarIngrediente.setBounds(215, 83, 209, 23);
		contentPane.add(btnAgregarIngrediente);
		
		btnBorrarReceta = new JButton("Borrar Receta");
		btnBorrarReceta.setBounds(215, 308, 209, 23);
		contentPane.add(btnBorrarReceta);
		
		btnRIC = new JButton("Asociar R-I-C");
		btnRIC.setBounds(215, 399, 209, 23);
		contentPane.add(btnRIC);
	}

	public JTextField getTextFieldNombreRec() {
		return textFieldNombreRec;
	}

	public JTextField getTextFieldNombreIng() {
		return textFieldNombreIng;
	}

	public JTextField getTextFieldPrecioRec() {
		return textFieldPrecioRec;
	}

	public JTextField getTextFieldAsociacion() {
		return textFieldAsociacion;
	}

	public JButton getBtnAgregarReceta() {
		return btnAgregarReceta;
	}

	public JButton getBtnAgregarIngrediente() {
		return btnAgregarIngrediente;
	}

	public JButton getBtnBorrarReceta() {
		return btnBorrarReceta;
	}

	public JButton getBtnRIC() {
		return btnRIC;
	}
	
	
	
	
}
