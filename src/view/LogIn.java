package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;

public class LogIn extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contenedor;
	private JButton botonConectar;
	private JButton botonLogin;
	private JButton botonRegistrar;
	private JButton botonSalir;
	private JLabel labelConnection;
	private JLabel labelStatus;
	private JLabel labelUsername;
	private JLabel labelPassword;
	private JLabel labelWelcome;
	private JTextField textUsername;
	private JPasswordField textPassword;

	public LogIn() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			
		}
		
		initComponents();
		setSize(400, 213);
		setTitle("::: Adivina Quién!::: Bienvenido");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPositions();
		contenedor = getContentPane();
		contenedor.setLayout(null);
		botonLogin.setText("Jugar!!");
		botonLogin.setActionCommand("login");
		botonConectar.setText("Conectar");
		botonConectar.setActionCommand("connect");
		botonRegistrar.setText("Registrate");
		botonRegistrar.setActionCommand("register");
		botonSalir.setText("Salir");
		botonSalir.setActionCommand("exit");
		labelConnection.setFont(new Font("Consolas", Font.BOLD, 12));
		labelConnection.setForeground(new Color(0, 255, 0));
		labelWelcome.setText("Bienvenido a Adivina Quién!!!");
		labelStatus.setText("Server Status:");
		labelUsername.setText("Username:");
		labelPassword.setText("Password:");
		textPassword.getDocument().putProperty("field", "password");
		textUsername.getDocument().putProperty("field", "username");
		contenedor.add(botonConectar);
		contenedor.add(botonLogin);
		contenedor.add(botonRegistrar);
		contenedor.add(botonSalir);
		contenedor.add(labelConnection);
		contenedor.add(labelPassword);
		contenedor.add(labelStatus);
		contenedor.add(labelUsername);
		contenedor.add(labelWelcome);
		contenedor.add(textUsername);
		contenedor.add(textPassword);
		reiniciar();
	}

	private void initComponents() {
		labelUsername = new JLabel();
		labelWelcome = new JLabel();
		labelStatus = new JLabel();
		labelConnection = new JLabel();
		labelPassword = new JLabel();
		labelUsername = new JLabel();
		textUsername = new JTextField();
		textPassword = new JPasswordField();
		botonConectar = new JButton();
		botonRegistrar = new JButton();
		botonSalir = new JButton();
		botonLogin = new JButton();
	}

	private void setPositions() {
		labelWelcome.setBounds(15, 10, 500, 23);
		labelUsername.setBounds(35, 56, 500, 23);
		labelPassword.setBounds(35, 79, 500, 23);
		labelStatus.setBounds(15, 150, 500, 23);
		labelConnection.setBounds(90, 150, 500, 23);
		textUsername.setBounds(100, 56, 100, 20);
		textPassword.setBounds(100, 79, 100, 20);
		botonLogin.setBounds(15, 110, 95, 25);
		botonRegistrar.setBounds(120, 110, 95, 25);
		botonSalir.setBounds(287, 10, 95, 25);
		botonConectar.setBounds(287, 150, 95, 25);
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public int showConfirmDisconnect() {
		return JOptionPane.showConfirmDialog(null, "Seguro desconectar el chat?");
	}

	public String showInputAddress() {
		return JOptionPane.showInputDialog(null, "Ingresa la IP del Servidor:", "localhost");
	}
	
	public void setEstadoBoton(String estado) {
		botonConectar.setText(estado);
	}

	public void setEstadoLabel(String estado) {
		labelConnection.setText(estado);
	}

	public void lockInterface() {
		botonConectar.setEnabled(false);
		botonSalir.setEnabled(false);
	}
	
	public void releaseInterface() {
		botonConectar.setEnabled(true);
		botonSalir.setEnabled(true);
	}

	public void addControlListener(ActionListener controlListener) {
		botonConectar.addActionListener(controlListener);
		botonLogin.addActionListener(controlListener);
		botonRegistrar.addActionListener(controlListener);
		botonSalir.addActionListener(controlListener);
	}
	
	public void addDocumentListener(DocumentListener documentListener) {
		textUsername.getDocument().addDocumentListener(documentListener);
		textPassword.getDocument().addDocumentListener(documentListener);
	}
	
	public void reiniciar() {
		setConnection(false);
		botonLogin.setEnabled(false);
		botonRegistrar.setEnabled(false);
	}
	
	public void setConnection(boolean connected) {
		if(!connected) {
			labelConnection.setText("Desconectado");
			labelConnection.setForeground(new Color(255, 0, 0));
			botonConectar.setText("Conectar");
		} else {
			labelConnection.setText("Conectado");
			labelConnection.setForeground(new Color(52, 168, 83));
			botonConectar.setText("Desconectar");
		}
		textPassword.setText("");
		textUsername.setText("");
		textUsername.setEnabled(connected);
		textPassword.setEnabled(connected);
		botonRegistrar.setEnabled(connected);
	}

	public void setLoginStatus(boolean loginStatus) {
		botonLogin.setEnabled(loginStatus);
	}
}
