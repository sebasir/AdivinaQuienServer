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

import model.Feature;
import model.Personage;

public class AdivinaQuien extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container contenedor;
	private JButton botonEnviar;
	private JButton botonSalir;
	private JButton botonAdivinar;
	private JLabel labelTitulo;
	private JLabel labelJuego;
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
		labelTitulo.setText("Sistemas Distribuidos: Adivina Quien");
		labelHistory.setText("Historial de Preguntas:");
		labelJuego.setText("Tablero");
		botonEnviar.setText("Enviar");
		botonEnviar.setActionCommand("send");
		botonAdivinar.setText("Adivinar");
		botonAdivinar.setActionCommand("guess");
		botonSalir.setText("Salir");
		botonSalir.setActionCommand("exitGame");
		contenedor.add(labelTitulo);
		contenedor.add(botonAdivinar);
		contenedor.add(botonSalir);
		contenedor.add(botonEnviar);
		contenedor.add(labelHistory);
		contenedor.add(labelJuego);
		contenedor.add(scrollPersonage);
		contenedor.add(scrollEscritura);
		contenedor.add(scrollHistory);
		reiniciar();
	}

	private void initComponents() {
		labelTitulo = new JLabel();
		labelHistory = new JLabel();
		labelJuego = new JLabel();
		botonEnviar = new JButton();
		botonSalir = new JButton();
		botonAdivinar = new JButton();
		areaEscritura = new JTextArea();
		areaPersonage = new JPanel();
		areaHistory = new JTextArea();
		scrollPersonage = new JScrollPane();
		scrollEscritura = new JScrollPane();
		scrollHistory = new JScrollPane();
	}

	private void setPositions() {
		labelTitulo.setBounds(15, 10, 500, 23);
		labelJuego.setBounds(15, 56, 240, 23);
		labelHistory.setBounds(525, 56, 240, 23);
		botonSalir.setBounds(675, 10, 60, 23);
		botonAdivinar.setBounds(575, 10, 93, 23);
		botonEnviar.setBounds(525, 436, 210, 37);
		scrollPersonage.setBounds(15, 76, 500, 295);
		scrollEscritura.setBounds(15, 382, 500, 90);
		scrollHistory.setBounds(525, 76, 210, 350);
	}

	public void addControlListener(ActionListener controlListener) {
		botonEnviar.addActionListener(controlListener);
		botonSalir.addActionListener(controlListener);
		botonAdivinar.addActionListener(controlListener);
	}

	public void addKeyListener(KeyListener controlListener) {
		areaEscritura.addKeyListener(controlListener);
	}

	public void setHistory(String input) {
		areaHistory.setText(input);
	}

	public void lockInterface() {
		areaEscritura.setEditable(false);
		areaEscritura.setEnabled(false);
		botonAdivinar.setEnabled(false);
		botonEnviar.setEnabled(false);
	}

	public void releaseInterface() {
		areaEscritura.setText("");
		botonEnviar.setEnabled(true);
		areaEscritura.setEditable(true);
		areaEscritura.setEnabled(true);
		botonAdivinar.setEnabled(true);
	}

	public int showConfirmDisconnect() {
		return JOptionPane.showConfirmDialog(null, "Seguro cerrar este juego? Perderás la partida.");
	}

	public int showConfirmAnswer(String question) {
		return JOptionPane.showConfirmDialog(null, question, "Te han preguntado...", JOptionPane.YES_NO_OPTION);
	}

	public int showConfirmPersonage(String text, Personage p) {
		try {
			String features = "Características: \n";
			for (Feature f : p.getFeature())
				features += f.getGrupo() + ": " + f.getItem() + "\n";
			ImageIcon icon = new ImageIcon(ImageIO.read(new ByteArrayInputStream(p.getImagen())));
			return JOptionPane.showConfirmDialog(null, text + p.getNombre() + "?\n" + features, "Confirma selección", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
		} catch (Exception e) {
			return JOptionPane.showConfirmDialog(null, text + p.getNombre(), "Confirma selección", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
		}
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

	public void loadPersonages(List<Personage> personages, ActionListener controlListener) {
		JButton boton = null;
		try {
			areaPersonage.removeAll();
			areaPersonage.setLayout(new GridLayout(3, 5));
			for (Personage p : personages) {
				boton = new JButton();
				boton.addActionListener(controlListener);
				boton.setActionCommand("pers_" + p.getIndex());
				boton.setToolTipText(p.getNombre());
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

	public void setChatText(String string) {
		areaEscritura.setText(string);
		areaEscritura.setEditable(false);
	}

	public void showImage(byte[] byteImage) {
		BufferedImage buff;
		try {
			buff = ImageIO.read(new ByteArrayInputStream(byteImage));
			areaPersonage.removeAll();
			areaPersonage.add(new JLabel(new ImageIcon(buff.getScaledInstance(485, 280, 0))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendHistory(String history) {
		areaHistory.append(history + "\n");
	}
}