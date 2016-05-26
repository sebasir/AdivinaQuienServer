package view;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class AdivinaQuien extends JFrame {
	private static final long serialVersionUID = 1L;

	public AdivinaQuien() {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println(e.getMessage());
		}
		initComponents();
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
	}

	public void setControl(boolean set) {
		groupQuestion.setEnabled(set);
		featureQuestion.setEnabled(set);
		question.setEnabled(set);
		yes.setEnabled(set);
		no.setEnabled(set);
		ask.setEnabled(set);
		questionForAsk.setEnabled(set);
		summary.setEnabled(set);
	}

	public void startGame() {
		personajes.removeAll();
		personajes.setLayout(new GridLayout(6, 4));
		for (int i = 0; i < 24; i++) {
			personajes.add(new JLabel(new ImageIcon("1.png")));
		}
		setControl(true);
	}

	private void initComponents() {
		personajes = new JPanel();
		jSeparator1 = new JSeparator();
		responderLabel = new JLabel();
		jSeparator2 = new JSeparator();
		grupolabel = new JLabel();
		featurelabel = new JLabel();
		questionLabel = new JLabel();
		question = new JTextField();
		yes = new JButton();
		no = new JButton();
		groupQuestion = new JTextField();
		featureQuestion = new JTextField();
		gruposlabel = new JLabel();
		questionlabel = new JLabel();
		questionForAsk = new JTextField();
		itemslabel = new JLabel();
		ask = new JButton();
		jSeparator3 = new JSeparator();
		selectedPersonajelabel = new JLabel();
		personajePic = new JPanel();
		namelabel = new JLabel();
		summarylabel = new JLabel();
		jScrollPane1 = new JScrollPane();
		summary = new JTextArea();
		personageInfolabel = new JLabel();
		askLabel = new JLabel();
		salir = new JButton();
	}

	private JButton ask;
	private JLabel askLabel;
	private JTextField featureQuestion;
	private JLabel featurelabel;
	private JTextField groupQuestion;
	private JLabel grupolabel;
	private JLabel gruposlabel;
	private JLabel itemslabel;
	private JScrollPane jScrollPane1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JSeparator jSeparator3;
	private JLabel namelabel;
	private JButton no;
	private JLabel personageInfolabel;
	private JPanel personajePic;
	private JPanel personajes;
	private JTextField question;
	private JTextField questionForAsk;
	private JLabel questionLabel;
	private JLabel questionlabel;
	private JLabel responderLabel;
	private JButton salir;
	private JLabel selectedPersonajelabel;
	private JTextArea summary;
	private JLabel summarylabel;
	private JButton yes;
}
