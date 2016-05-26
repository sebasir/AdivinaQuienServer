package view;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;

public class SignUp extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contenedor;
	private JButton botonEnviar;
	private JButton botonSalir;
	private JTextField textName;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JLabel labelName;
	private JLabel labelPassword;
	private JLabel labelUsername;
	private JLabel labelTitulo;

	public SignUp() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			
		}
		
		initComponents();
		setSize(245, 180);
		setTitle("Registro");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPositions();
		contenedor = getContentPane();
		contenedor.setLayout(null);
		labelTitulo.setText("Adivina Quien: Registro de Usuario nuevo");
		labelName.setText("Nombre:");
		labelUsername.setText("Username:");
		labelPassword.setText("Password:");
		botonEnviar.setText("Aceptar");
		botonEnviar.setActionCommand("sendRegister");
		botonSalir.setText("Salir");
		botonSalir.setActionCommand("cancelRegister");
		textName.getDocument().putProperty("field", "regName");
		textPassword.getDocument().putProperty("field", "regUsername");
		textUsername.getDocument().putProperty("field", "regPassword");
		contenedor.add(labelName);
		contenedor.add(labelTitulo);
		contenedor.add(labelUsername);
		contenedor.add(labelPassword);
		contenedor.add(botonEnviar);
		contenedor.add(botonSalir);
		contenedor.add(textName);
		contenedor.add(textUsername);
		contenedor.add(textPassword);
		reiniciar();
	}

	private void initComponents() {
		labelName = new JLabel();
		labelPassword = new JLabel();
		labelUsername = new JLabel();
		labelTitulo = new JLabel();
		textName = new JTextField();
		textUsername = new JTextField();
		textPassword = new JPasswordField();
		botonSalir = new JButton();
		botonEnviar = new JButton();
	}

	private void setPositions() {
		labelTitulo.setBounds(15, 10, 500, 23);
		labelName.setBounds(35, 33, 500, 23);
		labelUsername.setBounds(35, 56, 500, 23);
		labelPassword.setBounds(35, 79, 500, 23);
		textName.setBounds(100, 33, 100, 20);
		textUsername.setBounds(100, 56, 100, 20);
		textPassword.setBounds(100, 79, 100, 20);
		botonEnviar.setBounds(15, 110, 95, 25);
		botonSalir.setBounds(125, 110, 95, 25);
	}

	public void addControlListener(ActionListener controlListener) {
		botonEnviar.addActionListener(controlListener);
		botonSalir.addActionListener(controlListener);
	}
	
	public void addDocumentListener(DocumentListener documentListener) {
		textUsername.getDocument().addDocumentListener(documentListener);
		textPassword.getDocument().addDocumentListener(documentListener);
		textName.getDocument().addDocumentListener(documentListener);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void lockInterface() {
		botonEnviar.setEnabled(false);
		botonSalir.setEnabled(false);
		textName.setEnabled(false);
		textPassword.setEnabled(false);
		textUsername.setEnabled(false);
	}
	
	public void releaseInterface() {
		botonEnviar.setEnabled(true);
		botonSalir.setEnabled(true);
		textName.setEnabled(true);
		textPassword.setEnabled(true);
		textUsername.setEnabled(true);
	}

	public void reiniciar() {
		textName.setText("");
		textUsername.setText("");
		textPassword.setText("");
		botonEnviar.setEnabled(false);
	}
	
	public void setRegisterStatus(boolean regStatus) {
		botonEnviar.setEnabled(regStatus);
	}
}
