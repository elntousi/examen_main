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
 *   <li>{@link PanelNeuesThema} – links, zum Erstellen eines neuen Themas.</li>
 *   <li>{@link PanelThemenListe} – rechts, zur Anzeige einer Liste vorhandener Themen.</li>
 *   <li>{@link PanelAktionen} – unten, mit Schaltflächen für Aktionen (z. B. Speichern, Löschen).</li>
 * </ul>
 * Die Initialisierung der Unter-Panels erfolgt in {@link #initPanels()},
 * das Layout und die Anordnung in {@link #addPanels()}.
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 */
public class HauptPanel extends JPanel implements PanelThemenListe.ListSelectionDelegate, PanelAktionen.Delegate {

    // Unter-Panels, die im Layout verwendet werden
    PanelThemenListe panelThemenListe;
    PanelNeuesThema panelNeuesThema;
    PanelAktionen panelAktionen;

    DataManager manager = new DataManager(); 
    
    @Override
    public void recieveSelectedObject(ThemaObject selection) {
    	
    	  panelNeuesThema.titelFeld.setText(selection.getTitel());
    	  panelNeuesThema.infoArea.setText(selection.getInfo());
    	  
    }
    /**
     * Erstellt ein neues HauptPanel, initialisiert alle Unter-Panels
     * und fügt sie gemäß Layout in das Panel ein.
     */
    public HauptPanel() {
        super();
        initPanels();
        addPanels();
    }
    
	public interface PanelAktionenDelegate {
		/**
		 * Methoden, die von {@link HauptPanel} aufgerufen werden, wenn die
		 * entsprechenden Aktionen ausgelöst werden.
		 */
		public void saveTheme();

		public void deleteTheme();

		public void newTheme();
	}

	/**
	 * Implementiert die Methoden des {@link PanelAktionenDelegate} Interfaces, um
	 * die Aktionen auszuführen, wenn die entsprechenden Buttons geklickt werden.
	 * Diese Methoden geben lediglich eine Konsolenausgabe aus, können aber später
	 * erweitert werden, um tatsächliche Logik zu implementieren.
	 */
	
	
	public void saveTheme()   {
		System.out.println("saveTheme() wurde aufgerufen");
		}
	
	
    public void deleteTheme() {
    	System.out.println("deleteTheme() wurde aufgerufen"); 
    	}
	
    public void newTheme()    {
    	System.out.println("newTheme() wurde aufgerufen"); 
    	}
    
 
    /**
     * Initialisiert alle Unter-Panels und weist sie den entsprechenden Feldern zu.
     */
    
    
    void initPanels() {
        panelThemenListe = new PanelThemenListe();
        panelThemenListe.setListData(manager.ladeAlleThemen());
        panelThemenListe.delegate = this;
        panelNeuesThema = new PanelNeuesThema();
        panelAktionen = new PanelAktionen();
        panelAktionen.setDelegate(this); // <-- ΣΗΜΑΝΤΙΚΟ

    }

    /**
     * Fügt die Unter-Panels dem HauptPanel hinzu und ordnet sie mithilfe
     * eines {@link GridBagLayout} an.
     * <p>
     * - {@link PanelNeuesThema} wird links platziert.<br>
     * - {@link PanelThemenListe} wird rechts platziert.<br>
     * - {@link PanelAktionen} wird unten über die gesamte Breite platziert.
     * </p>
     */
    void addPanels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Linkes Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(15, 10, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        add(panelNeuesThema, gbc);

        // Rechtes Panel
        gbc.gridx = 1;
        gbc.insets = new Insets(15, 5, 0, 10);
        add(panelThemenListe, gbc);

        // Unteres Panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(panelAktionen, gbc);
    }

}

