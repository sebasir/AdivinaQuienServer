package controller;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import model.Datos;
import model.Personage;
import view.AdivinaQuien;
import view.LogIn;
import view.SignUp;

public class Connection extends Thread {
	private AdivinaQuien vistaJuego;
	private LogIn vistaLogin;
	private SignUp vistaSignup;
	private Datos datos;
	private ObjectInputStream in;
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
			out = new DataOutputStream(datos.getSocket().getOutputStream());
			in = new ObjectInputStream(datos.getSocket().getInputStream());
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
			Object object = "";
			String[] serverMessage;
			try {
				datos.setTablero(new ArrayList<Personage>());
				while (datos.isConnected() && (object = in.readObject()) != null) {
					if (object instanceof Personage)
						datos.getTablero().add((Personage) object);
					if (object instanceof byte[])
						datos.setWaitImage((byte[]) object);
					if (object instanceof String) {
						String line = (String) object;
						line = utils.decryptURL(line);
						serverMessage = line.split("@");
						System.out.println(Arrays.toString(serverMessage));
						if (line.equals("")) {
							vistaJuego.dispose();
						} else if (serverMessage[0].equals("log")) {
							if (serverMessage[1].equals("error"))
								vistaLogin.showMessage(serverMessage[2]);
							else if (serverMessage[1].equals("ok")) {
								vistaLogin.showMessage(serverMessage[2]);
								vistaLogin.dispose();
								vistaJuego.showWait(datos.getWaitImage());
								sendMessage("ready");
								vistaJuego.setVisible(true);
							}
						} else if (serverMessage[0].equals("reg")) {
							if (serverMessage[1].equals("error"))
								vistaSignup.showMessage(serverMessage[2]);
							else if (serverMessage[1].equals("ok")) {
								vistaSignup.showMessage(serverMessage[2]);
								vistaSignup.dispose();
							}
						} else if (serverMessage[0].equals("ready")) {
							if (serverMessage[1].equals("error"))
								vistaJuego.showMessage("Error en el emparejamiento.");
							else if (serverMessage[1].equals("ok")) {
								vistaJuego.loadPersonages();
							}
						} else if (serverMessage[0].equals("user")) {
							if (serverMessage[1].equals("error"))
								vistaJuego.showMessage(serverMessage[2]);
							else if (serverMessage[1].equals("ok")) {
								vistaJuego.showMessage(serverMessage[2]);
								vistaJuego.showMessage("HOLiiii");
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}
}