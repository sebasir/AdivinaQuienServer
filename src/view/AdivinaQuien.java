package view;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import model.Personage;

public class AdivinaQuien extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contenedor;
	private JButton botonEnviar;
	private JButton botonSalir;
	private JButton botonConnect;
	private JLabel labelTitulo;
	private JLabel labelEstado;
	private JLabel labelChat;
	private JLabel labelHistory;
	private JTextArea areaEscritura;
	private JPanel areaPersonage;
	private JTextArea areaHistory;
	private JScrollPane scrollEscritura;
	private JScrollPane scrollHistory;
	private JScrollPane scrollPersonage;

	public AdivinaQuien() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {

		}

		initComponents();
		setSize(750, 510);
		setTitle("Sistemas Distribuidos: Adivina Quien");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(0);
		setPositions();
		contenedor = getContentPane();
		contenedor.setLayout(null);
		areaEscritura.setEditable(false);
		areaHistory.setEditable(false);
		areaEscritura.setFont(new Font("Consolas", Font.PLAIN, 12));
		areaPersonage.setFont(new Font("Consolas", Font.PLAIN, 12));
		areaHistory.setFont(new Font("Consolas", Font.PLAIN, 12));
		scrollPersonage.setViewportView(areaPersonage);
		scrollEscritura.setViewportView(areaEscritura);
		scrollHistory.setViewportView(areaHistory);
		labelTitulo.setText("Sistemas Distribuidos: Chat sobre TCP");
		labelHistory.setText("Historial de Preguntas:");
		labelChat.setText("Chat de Grupo");
		botonEnviar.setText("Enviar");
		botonEnviar.setActionCommand("send");
		botonConnect.setText("Conectar");
		botonConnect.setActionCommand("connect");
		botonSalir.setText("Salir");
		botonSalir.setActionCommand("exitGame");
		labelEstado.setText("Desconectado");
		labelEstado.setFont(new Font("Consolas", Font.BOLD, 12));
		contenedor.add(labelTitulo);
		contenedor.add(botonConnect);
		contenedor.add(botonSalir);
		contenedor.add(botonEnviar);
		contenedor.add(labelEstado);
		contenedor.add(labelHistory);
		contenedor.add(labelChat);
		contenedor.add(scrollPersonage);
		contenedor.add(scrollEscritura);
		contenedor.add(scrollHistory);
		reiniciar();
	}

	private void initComponents() {
		labelEstado = new JLabel();
		labelTitulo = new JLabel();
		labelHistory = new JLabel();
		labelChat = new JLabel();
		botonEnviar = new JButton();
		botonSalir = new JButton();
		botonConnect = new JButton();
		areaEscritura = new JTextArea();
		areaPersonage = new JPanel();
		areaHistory = new JTextArea();
		scrollPersonage = new JScrollPane();
		scrollEscritura = new JScrollPane();
		scrollHistory = new JScrollPane();
	}

	private void setPositions() {
		labelTitulo.setBounds(15, 10, 500, 23);
		labelEstado.setBounds(470, 10, 240, 23);
		labelChat.setBounds(15, 56, 240, 23);
		labelHistory.setBounds(525, 56, 240, 23);
		botonSalir.setBounds(675, 10, 60, 23);
		botonConnect.setBounds(575, 10, 93, 23);
		botonEnviar.setBounds(525, 436, 210, 37);
		scrollPersonage.setBounds(15, 76, 500, 295);
		scrollEscritura.setBounds(15, 382, 500, 90);
		scrollHistory.setBounds(525, 76, 210, 350);
	}

	public void addControlListener(ActionListener controlListener) {
		botonEnviar.addActionListener(controlListener);
		botonSalir.addActionListener(controlListener);
		botonConnect.addActionListener(controlListener);
	}

	public void addKeyListener(KeyListener controlListener) {
		areaEscritura.addKeyListener(controlListener);
	}

	public void setHistory(String input) {
		areaHistory.setText(input);
	}

	public void lockInterface() {
		botonConnect.setEnabled(false);
		botonSalir.setEnabled(false);
	}

	public void releaseInterface() {
		botonConnect.setEnabled(true);
		botonSalir.setEnabled(true);
	}

	public int showConfirmDisconnect() {
		return JOptionPane.showConfirmDialog(null, "Seguro cerrar este juego? Perderás la partida.");
	}

	public int showConfirmAnswer(String question) {
		return JOptionPane.showConfirmDialog(null, question);
	}

	public String showInputQuestion() {
		return JOptionPane.showInputDialog(null, "Ingresa una pregunta:");
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public String getMessage() {
		String msg = areaEscritura.getText();
		areaEscritura.setText("");
		return msg;
	}

	public void loadPersonages() {
		areaPersonage.removeAll();
		areaPersonage.setLayout(new GridLayout(3, 5));
		JButton boton = null;
		for(int i = 1; i <= 15; i++) {
			boton = new JButton();
			boton.setBorder(BorderFactory.createEmptyBorder());
			areaPersonage.add(boton);
		}
		revalidate();
	}
	
	public void loadPersonages(List<Personage> personages) {
		JButton boton = null;
		try {
			areaPersonage.setLayout(new GridLayout(3, 5));
			for (Personage p: personages) {
				boton = new JButton();
				BufferedImage buff = ImageIO.read(new ByteArrayInputStream(p.getImagen()));
				boton.setIcon(new ImageIcon(buff.getScaledInstance(90, 90, 0)));
				boton.setBorder(BorderFactory.createEmptyBorder());
				areaPersonage.add(boton);
			}
			revalidate();
		} catch (Exception e) {
		}
	}

	public void reiniciar() {
		areaPersonage.setEnabled(false);
		areaEscritura.setEnabled(false);
		areaHistory.setEnabled(false);
		areaEscritura.setText("");
		areaHistory.setText("");
		botonEnviar.setEnabled(false);
	}

	public void showWait(byte[] waitImage) {
		BufferedImage buff;
		try {
			buff = ImageIO.read(new ByteArrayInputStream(waitImage));
			areaPersonage.removeAll();
			areaPersonage.add(new JLabel(new ImageIcon(buff.getScaledInstance(485, 280, 0))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}