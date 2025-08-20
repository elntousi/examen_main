package gui.okkit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.ThemaObject;
import gui.okkit.PanelThemenListe.ListSelectionDelegate;

/**
 * Panel mit den Hauptaktionen der Anwendung und einer Meldungszeile.
 * <p>
 * Stellt drei Schaltflächen bereit:
 * <ul>
 *   <li><b>Thema Löschen</b> – entfernt das aktuell ausgewählte Thema,</li>
 *   <li><b>Speichern</b> – speichert Änderungen am aktuellen Thema,</li>
 *   <li><b>Neues Thema</b> – legt ein neues Thema an.</li>
 * </ul>
 * Oberhalb der Schaltflächen befindet sich ein schreibgeschütztes
 * {@link JTextField} als Meldungs-/Statusbereich. Die Anordnung erfolgt
 * mittels {@link GridBagLayout}. Die eigentliche Logik (ActionListener)
 * wird außerhalb dieses Panels registriert (z. B. im Controller).
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 * @see HauptPanel
 */
public class PanelAktionen extends JPanel {
	
	JButton btnLoeschen;
	JButton btnSpeichern;
	JButton btnNeuesThema;

	JTextField messageArea;


	
	private AktionPanelDelegate delegate;
	public void setDelegate(AktionPanelDelegate delegate) { this.delegate = delegate; }

	/**
	 * Erstellt das Aktionen-Panel, initialisiert alle UI-Komponenten und
	 * fügt sie gemäß Layout ein.
	 * <p>
	 * Ruft intern {@link #initComponents()} und {@link #addComponents()} auf.
	 * </p>
	 */
	public PanelAktionen() {
		super();
		initComponents();
		addComponents();
       }

	/**
	 * Initialisiert die UI-Komponenten dieses Panels.
	 * <p>
	 * Erzeugt die drei Schaltflächen (<i>Thema Löschen</i>, <i>Speichern</i>,
	 * <i>Neues Thema</i>) sowie das schreibgeschützte {@link JTextField}
	 * {@code messageArea} für Statusmeldungen und setzt einen
	 * Standardtext.
	 * </p>
	 */
	
	
	void initComponents() {
        btnLoeschen = new JButton("Thema Löschen");
        btnLoeschen.addActionListener(e -> {
            if (delegate != null) delegate.deleteTheme();
        });

        btnSpeichern = new JButton("Speichern");
        btnSpeichern.addActionListener(e -> {
            if (delegate != null) delegate.saveTheme();
        });

        btnNeuesThema = new JButton("Neues Thema");
        btnNeuesThema.addActionListener(e -> {
            if (delegate != null) delegate.newTheme();
        });

        messageArea = new JTextField();
        messageArea.setEditable(false);
        messageArea.setText("Message area");
    }


	/**
	 * Fügt die initialisierten Komponenten dem Panel hinzu und ordnet sie
	 * mithilfe von {@link GridBagConstraints} in einem {@link GridBagLayout} an.
	 * <p>
	 * Der Meldungsbereich wird oben über drei Spalten gestreckt platziert.
	 * Darunter werden die drei Schaltflächen in einer Zeile angeordnet.
	 * </p>
	 */
	void addComponents() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(7, 7, 7, 7);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		add(messageArea, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;

		gbc.gridx = 0;
		add(btnLoeschen, gbc);

		gbc.gridx = 1;
		add(btnSpeichern, gbc);

		gbc.gridx = 2;
		add(btnNeuesThema, gbc);
	}
}

