package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import model.Datos;
import view.AdivinaQuien;
import view.LogIn;
import view.SignUp;

public class Connection extends Thread {
	private AdivinaQuien vistaJuego;
	private LogIn vistaLogin;
	private SignUp vistaSignup;
	private Datos datos;
	private DataInputStream in;
	private DataOutputStream out;
	private Recibir recibir;

	public Connection(AdivinaQuien vistaJuego, LogIn vistaLogin, SignUp vistaSignup, Datos datos) {
		this.vistaJuego = vistaJuego;
		this.vistaLogin = vistaLogin;
		this.vistaSignup = vistaSignup;
		this.datos = datos;
	}

	public void run() {
		String message = "";
		boolean success = false;
		try {
			datos.setSocket(new Socket(datos.getServerAddress(), Datos.serverPort));
			in = new DataInputStream(datos.getSocket().getInputStream());
			out = new DataOutputStream(datos.getSocket().getOutputStream());
			success = true;
			message = "Conexion exitosa!";
		} catch (UnknownHostException e) {
			message = "Socket:" + e.getMessage();
		} catch (EOFException e) {
			message = "EOF:" + e.getMessage();
		} catch (IOException e) {
			message = "Readline:" + e.getMessage();
		}
		vistaLogin.showMessage(message);
		if (!success) {
			vistaLogin.setConnection(false);
			vistaLogin.setEstadoLabel("Error");
		} else {
			vistaLogin.setConnection(true);
			datos.setConnected(true);
			vistaLogin.setEstadoBoton("Desconectar");
			recibir = new Recibir(vistaJuego, vistaLogin, vistaSignup, datos);
			recibir.start();
		}
		vistaLogin.releaseInterface();
	}

	public void disconnect() {
		if (datos.getSocket() != null) {
			datos.setConnected(false);
			try {
				datos.getSocket().close();
			} catch (IOException e) {
				vistaLogin.showMessage("Cerrar: " + e.getMessage());
			} finally {
				datos.setSocket(null);
			}
		}
	}

	public void sendMessage(String message) {
		try {
			out.writeUTF(utils.encryptURL(message));
		} catch (IOException e) {
			vistaLogin.showMessage("Error al enviar mensaje: " + e.getMessage());
		}
	}

	class Recibir extends Thread {
		private AdivinaQuien vistaJuego;
		private LogIn vistaLogin;
		private SignUp vistaSignup;
		private Datos datos;

		public Recibir(AdivinaQuien vistaJuego, LogIn vistaLogin, SignUp vistaSignup, Datos datos) {
			this.vistaJuego = vistaJuego;
			this.vistaLogin = vistaLogin;
			this.vistaSignup = vistaSignup;
			this.datos = datos;
		}

		public void run() {
			String line = "";
			try {
				while (datos.isConnected() && (line = in.readUTF()) != null) {
					line = utils.decryptURL(line);
					if (line.equals("")) {
						vistaJuego.dispose();
					} else if (line.equals("log:")) {
						vistaLogin.showMessage(line.substring(4));
					} else if (line.startsWith("reg:")) {
						vistaSignup.showMessage(line.substring(4));
					}
				}
			} catch (IOException e) {

			}
		}
	}
}