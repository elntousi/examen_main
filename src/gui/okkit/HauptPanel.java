package gui.okkit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

import data.DataManager;
import data.ThemaObject;
import gui.okkit.PanelThemenListe.ListSelectionDelegate;

/**
 * Zentrales Panel der Anwendung, das im {@link ExamenFrame} eingebettet wird.
 * <p>
 * Das HauptPanel enthält und verwaltet drei Unter-Panels:
 * <ul>
 * <li>{@link PanelNeuesThema} – links, zum Erstellen eines neuen Themas.</li>
 * <li>{@link PanelThemenListe} – rechts, zur Anzeige einer Liste vorhandener Themen.</li>
 * <li>{@link PanelAktionen} – unten, mit Schaltflächen für Aktionen (z. B. Speichern, Löschen).</li>
 * </ul>
 * Die Initialisierung der Unter-Panels erfolgt in {@link #initPanels()},
 * das Layout und die Anordnung in {@link #addPanels()}.
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
     * Wird aufgerufen, wenn ein Thema in der Liste ausgewählt wird.
     * Aktualisiert die Eingabefelder im PanelNeuesThema.
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
     * Speichert ein neues oder bearbeitetes Thema.
     * Wird vom "Speichern"-Button in PanelAktionen aufgerufen.
     */
    @Override
    public void saveTheme() {
        System.out.println("saveTheme() wurde aufgerufen");

        // 1) Eingabedaten lesen
        String titel = panelNeuesThema.titelFeld.getText();
        String info  = panelNeuesThema.infoArea.getText();

        // 2) Validierung des Titels
        if (titel == null || titel.isBlank()) {
            panelAktionen.setMessage("Bitte einen Titel eingeben.");
            panelNeuesThema.titelFeld.requestFocusInWindow();
            return;
        }

        // 3) Ausgewähltes Thema holen oder neues Thema anlegen
        ThemaObject selected = panelThemenListe.getSelected();
        ThemaObject thema = (selected != null) ? selected : new ThemaObject();
        thema.setTitel(titel);
        thema.setInfo(info);

        // 4) Speichern des Themas über DataManager
        String msg = manager.saveThema(thema);

        // 5) Aktualisierung der Themenliste
        if (selected == null) {
            // Neues Thema hinzufügen
            panelThemenListe.addThema(thema);
        } else {
            // Darstellung des bearbeiteten Themas aktualisieren
            panelThemenListe.refreshSelected();
        }

        // 6) Statusmeldung anzeigen
        panelAktionen.setMessage(msg);
    }

    /**
     * Löscht das aktuell ausgewählte Thema aus der Liste.
     * Wird vom "Löschen"-Button in PanelAktionen aufgerufen.
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

        // 2) Thema aus der Liste entfernen
        panelThemenListe.removeSelected();

        // 3) Formular zurücksetzen
        panelNeuesThema.titelFeld.setText("");
        panelNeuesThema.infoArea.setText("");

        // 4) Statusmeldung anzeigen
        panelAktionen.setMessage("Thema gelöscht: " + selected.getTitel());
    }

    /**
     * Erstellt ein leeres Formular für ein neues Thema.
     * Wird vom "Neu"-Button in PanelAktionen aufgerufen.
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
        panelAktionen.setDelegate(this); // Buttons an dieses Panel anbinden
    }

    /**
     * Fügt die Unter-Panels in das HauptPanel ein und ordnet sie
     * mithilfe von {@link GridBagLayout}.
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
