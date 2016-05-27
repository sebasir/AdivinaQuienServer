package model;

import java.net.Socket;
import java.util.ArrayList;

public class Datos {
	private String serverAddress;
	private String userName;
	private String passWord;
	private String regUsername;
	private String regPassword;
	private String regName;
	private String selectedPersonage;
	private boolean connected;
	private byte[] waitImage;
	private byte[] winImage;
	private byte[] loseImage;
	private Socket socket;
	private ArrayList<Personage> tablero;
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

	public ArrayList<Personage> getTablero() {
		return tablero;
	}

	public void setTablero(ArrayList<Personage> tablero) {
		this.tablero = tablero;
	}

	public void setWaitImage(byte[] waitImage) {
		this.waitImage = waitImage;
	}

	public byte[] getWaitImage() {
		return waitImage;
	}

	public void reiniciar() {
		setConnected(false);
		setServerAddress(null);
		setSocket(null);
	}

	public String getSelectedPersonage() {
		return selectedPersonage;
	}

	public void setSelectedPersonage(String selectedPersonage) {
		this.selectedPersonage = selectedPersonage;
	}

	public byte[] getWinImage() {
		return winImage;
	}

	public void setWinImage(byte[] winImage) {
		this.winImage = winImage;
	}

	public byte[] getLoseImage() {
		return loseImage;
	}

	public void setLoseImage(byte[] loseImage) {
		this.loseImage = loseImage;
	}
}