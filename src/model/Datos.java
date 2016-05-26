package model;

import java.net.Socket;

public class Datos {
	private String serverAddress;
	private String userName;
	private String passWord;
	private String regUsername;
	private String regPassword;
	private String regName;
	private boolean connected;
	private Socket socket;
	public static final int serverPort = 12345;

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String resultado) {
		this.serverAddress = resultado;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getRegUsername() {
		return regUsername;
	}

	public void setRegUsername(String regUsername) {
		this.regUsername = regUsername;
	}

	public String getRegPassword() {
		return regPassword;
	}

	public void setRegPassword(String regPassword) {
		this.regPassword = regPassword;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public void reiniciar() {
		setConnected(false);
		setServerAddress(null);
		setSocket(null);
	}
}