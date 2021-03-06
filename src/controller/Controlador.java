
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import model.Datos;
import model.Personage;
import view.AdivinaQuien;
import view.LogIn;
import view.SignUp;

public class Controlador implements ActionListener, KeyListener, DocumentListener {
	private Datos datos;
	private AdivinaQuien vistaJuego;
	private LogIn vistaLogin;
	private SignUp vistaSignup;
	private Connection conn;

	public Controlador(AdivinaQuien vistaJuego, LogIn vistaLogin, SignUp vistaSignup, Datos datos) {
		this.vistaJuego = vistaJuego;
		this.vistaLogin = vistaLogin;
		this.vistaSignup = vistaSignup;
		this.datos = datos;
	}

	@Override
	public void actionPerformed(ActionEvent boton) {
		switch (boton.getActionCommand()) {
		case "exit":
			if (datos.isConnected())
				disconnect();
			vistaLogin.dispose();
			vistaSignup.dispose();
			vistaJuego.dispose();
			break;

		case "exitGame":
			if (vistaJuego.showConfirmDisconnect() == 0) {
				conn.sendMessage("dis");
				if (datos.isConnected())
					disconnect();
				vistaJuego.dispose();
				vistaLogin.setVisible(true);
			}
			break;
		case "connect":
			if (!datos.isConnected()) {
				datos.setServerAddress(vistaLogin.showInputAddress());
				if (datos.getServerAddress() != null) {
					do {
						if (checkValid())
							connect();
						else {
							vistaLogin.showMessage("Ingresa un direccion valida!!");
							datos.setServerAddress(vistaLogin.showInputAddress());
						}
					} while (datos.getServerAddress() != null && !checkValid());
				}
			} else {
				if (vistaLogin.showConfirmDisconnect() == 0)
					disconnect();
			}
			break;
		case "send":
			if (datos.isConnected())
				sendQuestion();
			break;

		case "register":
			vistaSignup.setVisible(true);
			break;

		case "cancelRegister":
			vistaSignup.reiniciar();
			vistaSignup.setVisible(false);
			break;

		case "sendRegister":
			vistaSignup.lockInterface();
			conn.sendMessage("reg@" + datos.getRegName() + "@" + datos.getRegUsername() + "@" + utils.encriptarClave(datos.getRegPassword()));
			vistaSignup.releaseInterface();
			break;

		case "login":
			conn.sendMessage("log@" + datos.getUserName() + "@" + utils.encriptarClave(datos.getPassWord()));
			break;

		default:
			String selectedPersonage = boton.getActionCommand();
			if (selectedPersonage != null && selectedPersonage.startsWith("pers_")) {
				selectedPersonage = selectedPersonage.substring(5);
				if (datos.getSelectedPersonage() == null) {
					if (vistaJuego.showConfirmPersonage("Estas seguro que deseas seleccionar a ", getPersonage(Integer.parseInt(selectedPersonage))) == 0) {
						datos.setSelectedPersonage(selectedPersonage);
						conn.sendMessage("pers@" + datos.getSelectedPersonage());
						vistaJuego.setChatText("Esperando al servidor...");
					}
				} else {
					selectedPersonage = datos.getSelectedPersonage();
					if (vistaJuego.showConfirmPersonage("Estas seguro que deseas seleccionar a ", getPersonage(Integer.parseInt(selectedPersonage))) == 0) {
						conn.sendMessage("guess@" + datos.getSelectedPersonage());
						vistaJuego.setChatText("Esperando al servidor...");
					}
				}
			}
			break;
		}
	}

	private void connect() {
		vistaLogin.setEstadoLabel("Conectando");
		vistaLogin.lockInterface();
		conn = new Connection(datos, vistaJuego, vistaLogin, vistaSignup, this);
		conn.start();
	}

	private void disconnect() {
		conn.disconnect();
		datos.reiniciar();
		vistaLogin.reiniciar();
	}

	private boolean checkValid() {
		if (datos.getServerAddress().trim().equals(""))
			return false;
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (datos.isConnected())
			if ((int) e.getKeyChar() == 10)
				sendQuestion();
	}

	private void sendQuestion() {
		String question = vistaJuego.getMessage().trim();
		if (!validateMessage(question))
			vistaJuego.showMessage("Debes hacer una pregunta v�lida");
		else {
			conn.sendMessage("q@" + question);
			vistaJuego.lockInterface();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		checkCredentials(e.getDocument());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		checkCredentials(e.getDocument());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		checkCredentials(e.getDocument());
	}

	private void checkCredentials(Document d) {
		try {
			if (d.getProperty("field").equals("username"))
				datos.setUserName(d.getText(0, d.getLength()));
			else if (d.getProperty("field").equals("password"))
				datos.setPassWord(d.getText(0, d.getLength()));
			else if (d.getProperty("field").equals("regUsername"))
				datos.setRegUsername(d.getText(0, d.getLength()));
			else if (d.getProperty("field").equals("regPassword"))
				datos.setRegPassword(d.getText(0, d.getLength()));
			else if (d.getProperty("field").equals("regName"))
				datos.setRegName(d.getText(0, d.getLength()));
		} catch (BadLocationException e1) {
			datos.setUserName("");
			datos.setPassWord("");
			datos.setRegName("");
			datos.setRegUsername("");
			datos.setRegPassword("");
		}
		vistaLogin.setLoginStatus(datos.getPassWord() != null && datos.getUserName() != null && !datos.getPassWord().isEmpty() && !datos.getUserName().isEmpty());
		vistaSignup.setRegisterStatus(datos.getRegName() != null && datos.getRegPassword() != null && datos.getRegUsername() != null && !datos.getRegPassword().isEmpty() && !datos.getRegUsername().isEmpty() && !datos.getRegName().isEmpty() && !datos.getRegName().isEmpty());
	}

	private boolean validateMessage(String message) {
		if (message != null)
			if (!message.trim().equals(""))
				return true;
		return false;
	}

	private Personage getPersonage(int idPersonage) {
		for (Personage p : datos.getTablero())
			if (p.getIndex() == idPersonage)
				return p;
		return null;
	}
}