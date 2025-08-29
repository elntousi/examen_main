package io.github.elntousi.examen.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.github.elntousi.examen.data.ThemaObject;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Panel zur Anzeige einer scrollbaren Themenliste.
 * <p>
 * Zeigt eine {@link JList} mit Themen-Titeln innerhalb einer
 * {@link JScrollPane}. Das Layout erfolgt mit {@link GridBagLayout}; ein
 * titulierte Rahmen kennzeichnet den Bereich als „Themen“.
 * </p>
 *
 * <p>
 * <b>Hinweis:</b> Die Liste ist hier mit statischen Beispieldaten befüllt. In
 * einer echten Anwendung sollte ein {@link javax.swing.ListModel} (z.&nbsp;B.
 * {@link javax.swing.DefaultListModel}) verwendet und die Daten dynamisch
 * verwaltet werden.
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 * @see HauptPanel
 */
public class PanelThemenListe extends JPanel implements ListSelectionListener {

	public interface ListSelectionDelegate {
		/**
		 * Callback-Methode, die aufgerufen wird, wenn ein Listenelement ausgewählt
		 * wird.
		 * 
		 * @param selected Der aktuell ausgewählte Listeneintrag.
		 */

		public void recieveSelectedObject(ThemaObject selected);

		/**
		 * Implementiert die Methoden des {@link PanelAktionenDelegate} Interfaces, um
		 * die Aktionen auszuführen, wenn die entsprechenden Buttons geklickt werden.
		 * Diese Methoden geben lediglich eine Konsolenausgabe aus, können aber später
		 * erweitert werden, um tatsächliche Logik zu implementieren.
		 */

	}

	/**
	 * Erzeugt das Panel, initialisiert die Liste und fügt sie dem Layout hinzu.
	 * <p>
	 * Ruft intern {@link #initList()} und {@link #addList()} auf.
	 * </p>
	 */

	JList<ThemaObject> themenListe;
	private DefaultListModel<ThemaObject> model = new DefaultListModel<ThemaObject>();
	ListSelectionDelegate delegate;

	// Hilfsfunktionen für Sortierung
	private static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}

	private static final Collator DE_COLLATOR = Collator.getInstance(Locale.GERMAN);

	public PanelThemenListe() {
		super();
		initList();
		addList();
	}

	private void initList() {

		themenListe = new JList<>(model); // <--- συνδέεται με το DefaultListModel
		themenListe.setFont(new Font("Serif", Font.PLAIN, 15));
		themenListe.addListSelectionListener(this);

	}

	/**
	 * Fügt die Liste in eine {@link JScrollPane} ein und ordnet sie mit
	 * {@link GridBagLayout} so an, dass sie den verfügbaren Platz ausfüllt.
	 */
	void addList() {

		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Themen"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		JScrollPane scrollPane = new JScrollPane(themenListe);

		add(scrollPane, gbc);
	}

	// --- ListSelectionListener ---

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Überprüfen, ob die Auswahländerung abgeschlossen ist
		if (!e.getValueIsAdjusting() && delegate != null) {
			ThemaObject selected = themenListe.getSelectedValue();
			delegate.recieveSelectedObject(selected);
		}
	}

	/**
	 * Setzt die Listendaten neu und sortiert: zuerst Themen ohne Info
	 * (Sternchen-Gruppe), dann mit Info; innerhalb jeder Gruppe alphabetisch
	 * (deutsche Sortierung).
	 */
	public void setListData(List<ThemaObject> themen) {
		// Sortierung: ohne Info zuerst (zeigt sich als "* Titel"), dann mit Info;
		// innerhalb jeder Gruppe nach Titel (deutscher Collator)
		themen.sort(Comparator.comparing((ThemaObject t) -> isBlank(t.getInfo()) ? 0 : 1).thenComparing(t -> {
			String tt = (t.getTitel() == null) ? "" : t.getTitel().trim();
			return tt;
		}, DE_COLLATOR));

		// Model neu aufbauen
		DefaultListModel<ThemaObject> neuesModel = new DefaultListModel<>();
		for (ThemaObject t : themen) {
			neuesModel.addElement(t);
		}

		// Model in der JList setzen und Referenz aktualisieren,
		// damit addThema()/removeSelected()/refreshSelected() weiter mit dem selben
		// Model arbeiten
		this.themenListe.setModel(neuesModel);
		this.model = neuesModel;
	}

	/** Liefert das aktuell ausgewählte Thema (oder null). */
	public ThemaObject getSelected() {
		return themenListe.getSelectedValue();
	}

	/** Fügt ein neues Thema hinzu und wählt es aus. */
	public void addThema(ThemaObject thema) {
		if (thema == null)
			return;
		model.addElement(thema);
		themenListe.setSelectedValue(thema, true);
	}

	/** Erzwingt ein Repaint des ausgewählten Elements (z.B. nach Titeländerung). */
	public void refreshSelected() {
		int idx = themenListe.getSelectedIndex();
		if (idx >= 0) {
			model.set(idx, model.get(idx)); // setze dasselbe Objekt -> repaint
		}
	}

	/** Entfernt das aktuell ausgewählte Thema. */
	public void removeSelected() {
		int idx = themenListe.getSelectedIndex();
		if (idx >= 0)
			model.remove(idx);
	}

	/** Hebt die Auswahl auf. */
	public void clearSelection() {
		themenListe.clearSelection();
	}

//		// Optional: Aktualisiert die Anzeige, wenn die Liste geändert wurde
//		themenListe.revalidate();
//		themenListe.repaint();
//
//		// Setzt den Listener für die Auswahländerung
//		themenListe.addListSelectionListener(this);
//		

}
