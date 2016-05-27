package controller;

import model.Datos;
import view.AdivinaQuien;
import view.LogIn;
import view.SignUp;

public class InicializadorClient {
	private static AdivinaQuien vistaJuego;
	private static LogIn vistaLogin;
	private static SignUp vistaSignup;
	private static Datos datos;
	private static Controlador control;

	public static void main(String[] args) {
		vistaJuego = new AdivinaQuien();
		vistaLogin = new LogIn();
		vistaSignup = new SignUp();
		datos = new Datos();
		control = new Controlador(vistaJuego, vistaLogin, vistaSignup, datos);
		vistaJuego.addControlListener(control);
		vistaLogin.addControlListener(control);
		vistaLogin.addDocumentListener(control);
		vistaSignup.addControlListener(control);
		vistaSignup.addDocumentListener(control);
		vistaLogin.setVisible(true);

		/*
		 * vistaJuego.setVisible(true); ArrayList<Personage> personages = new
		 * ArrayList<>(); Personage p = null; try { for(int i = 1; i <= 15; i++)
		 * { p = new Personage(i, "Diego_" + i);
		 * p.setImagen(Files.readAllBytes(new File("images/" + i +
		 * ".jpg").toPath())); personages.add(p); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * vistaJuego.loadPersonages(personages);
		 */
	}
}
