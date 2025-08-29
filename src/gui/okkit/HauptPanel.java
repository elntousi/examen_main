package gui.okkit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

import data.DataManager;
import data.ThemaObject;

/**
 * Zentrales Panel der Anwendung, das im {@link ExamenFrame} eingebettet wird.
 * <p>
 * Das HauptPanel enthält und verwaltet drei Unter-Panels:
 * <ul>
 * <li>{@link PanelNeuesThema} – links, zum Erstellen eines neuen Themas.</li>
 * <li>{@link PanelThemenListe} – rechts, zur Anzeige einer Liste vorhandener
 * Themen.</li>
 * <li>{@link PanelAktionen} – unten, mit Schaltflächen für Aktionen (z. B.
 * Speichern, Löschen).</li>
 * </ul>
 * Die Initialisierung der Unter-Panels erfolgt in {@link #initPanels()}, das
 * Layout und die Anordnung in {@link #addPanels()}.
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 */
public class HauptPanel extends JPanel implements PanelThemenListe.ListSelectionDelegate, AktionPanelDelegate {

	// Unter-Panels, die in diesem HauptPanel verwendet werden
	PanelThemenListe panelThemenListe;
	PanelNeuesThema panelNeuesThema;
	PanelAktionen panelAktionen;

	// Datenmanager zum Laden und Speichern von Themen
	DataManager manager = new DataManager();

	/**
	 * Konstruktor: Initialisiert das HauptPanel und alle Unter-Panels.
	 */
	public HauptPanel() {
		super();
		initPanels();
		addPanels();
	}

	/**
	 * Wird aufgerufen, wenn ein Thema in der Liste ausgewählt wird. Aktualisiert
	 * die Eingabefelder im PanelNeuesThema.
	 */
	@Override
	public void recieveSelectedObject(ThemaObject selection) {
		if (selection == null) {
			// Formular zurücksetzen, falls keine Auswahl vorhanden ist
			panelNeuesThema.titelFeld.setText("");
			panelNeuesThema.infoArea.setText("");
			return;
		}
		panelNeuesThema.titelFeld.setText(selection.getTitel());
		panelNeuesThema.infoArea.setText(selection.getInfo());
	}

	/**
	 * Speichert ein neues oder bearbeitetes Thema. Wird vom "Speichern"-Button in
	 * PanelAktionen aufgerufen.
	 */

	@Override
	public void saveTheme() {
		System.out.println("saveTheme() wurde aufgerufen");

		// 1) Eingabedaten auslesen
		String titel = panelNeuesThema.titelFeld.getText();
		String info = panelNeuesThema.infoArea.getText();

		// 2) Titel validieren
		if (titel == null || titel.isBlank()) {
			panelAktionen.setMessage("Bitte einen Titel eingeben.");
			panelNeuesThema.titelFeld.requestFocusInWindow();
			return;
		}

		// 3) Wenn nur der Titel eingegeben wurde, um Bestätigung bitten
		if (info == null || info.isBlank()) {
			int opt = javax.swing.JOptionPane.showOptionDialog(this,
					"Es wurde nur der Titel eingegeben.\nMöchten Sie trotzdem speichern?", "Nur Titel speichern?",
					javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "OK", "Abbrechen" }, // benannte Buttons
					"OK");
			if (opt != javax.swing.JOptionPane.OK_OPTION) {
				// Abgebrochen → zurück ins Formular
				panelNeuesThema.infoArea.requestFocusInWindow();
				return;
			}
		}

		// 4) Ausgewähltes Thema holen oder neues Thema erstellen
		ThemaObject selected = panelThemenListe.getSelected();
		ThemaObject thema = (selected != null) ? selected : new ThemaObject();
		thema.setTitel(titel);
		thema.setInfo(info);

		// 5) Speichern des Themas über DataManager
		String msg = manager.saveThema(thema);

		// 6) Liste mit aktuellen Daten aus der DB neu laden
		panelThemenListe.setListData(manager.ladeAlleThemen());

		// 7) Auswahl zurücksetzen und Statusmeldung anzeigen
		panelThemenListe.clearSelection();
		panelAktionen.setMessage(msg);
	}

	/**
	 * Löscht das aktuell ausgewählte Thema. Wird vom "Thema Löschen"-Button in
	 * PanelAktionen aufgerufen.
	 */
	@Override
	public void deleteTheme() {
		System.out.println("deleteTheme() wurde aufgerufen");

		// 1) Ausgewähltes Thema holen
		ThemaObject selected = panelThemenListe.getSelected();
		if (selected == null) {
			panelAktionen.setMessage("Kein Thema ausgewählt.");
			return;
		}

		// 2) Benutzer um Bestätigung fragen
		int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
				"Möchten Sie das ausgewählte Thema wirklich löschen?", "Löschen bestätigen",
				javax.swing.JOptionPane.YES_NO_OPTION);
		if (confirm != javax.swing.JOptionPane.YES_OPTION) {
			return; // Abbruch durch Benutzer
		}

		// 3) Thema in der Datenbank löschen
		boolean ok = manager.deleteThema(selected);

		if (ok) {
			// 4) Liste mit aktuellen Daten aus der DB neu laden
			panelThemenListe.setListData(manager.ladeAlleThemen());

			// 5) Formular zurücksetzen
			panelNeuesThema.titelFeld.setText("");
			panelNeuesThema.infoArea.setText("");

			// 6) Erfolgsnachricht anzeigen
			panelAktionen.setMessage("Thema gelöscht: " + selected.getTitel());
		} else {
			panelAktionen.setMessage("Fehler: Thema konnte nicht gelöscht werden.");
		}
	}

	/**
	 * Bereitet das Formular für die Eingabe eines neuen Themas vor. Wird vom "Neues
	 * Thema"-Button in PanelAktionen aufgerufen.
	 */
	@Override
	public void newTheme() {
		System.out.println("newTheme() wurde aufgerufen");

		// 1) Auswahl in der Liste aufheben
		panelThemenListe.clearSelection();

		// 2) Formular zurücksetzen
		panelNeuesThema.titelFeld.setText("");
		panelNeuesThema.infoArea.setText("");
		panelNeuesThema.titelFeld.requestFocusInWindow();

		// 3) Statusmeldung anzeigen
		panelAktionen.setMessage("Neues Thema: Daten eingeben und 'Speichern' klicken.");
	}

	/**
	 * Initialisiert alle Unter-Panels und verknüpft die Delegates.
	 */
	void initPanels() {
		panelThemenListe = new PanelThemenListe();
		panelThemenListe.setListData(manager.ladeAlleThemen());
		panelThemenListe.delegate = this;
		panelNeuesThema = new PanelNeuesThema();
		panelAktionen = new PanelAktionen();
		panelAktionen.setDelegate(this); // Buttons mit diesem Panel verbinden
	}

	/**
	 * Fügt die Unter-Panels in das HauptPanel ein und ordnet sie mit Hilfe von
	 * {@link GridBagLayout} an.
	 */
	void addPanels() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Linkes Panel (Formular für neue Themen)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(15, 10, 0, 5);
		gbc.fill = GridBagConstraints.BOTH;
		add(panelNeuesThema, gbc);

		// Rechtes Panel (Themenliste)
		gbc.gridx = 1;
		gbc.insets = new Insets(15, 5, 0, 10);
		add(panelThemenListe, gbc);

		// Unteres Panel (Buttons für Aktionen)
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(5, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(panelAktionen, gbc);
	}
}
