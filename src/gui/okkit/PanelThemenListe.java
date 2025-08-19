package gui.okkit;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.ThemaObject;

/**
 * Panel zur Anzeige einer scrollbaren Themenliste.
 * <p>
 * Zeigt eine {@link JList} mit Themen-Titeln innerhalb einer {@link JScrollPane}.
 * Das Layout erfolgt mit {@link GridBagLayout}; ein titulierte Rahmen
 * kennzeichnet den Bereich als „Themen“.
 * </p>
 *
 * <p><b>Hinweis:</b> Die Liste ist hier mit statischen Beispieldaten befüllt.
 * In einer echten Anwendung sollte ein {@link javax.swing.ListModel}
 * (z.&nbsp;B. {@link javax.swing.DefaultListModel}) verwendet und die Daten
 * dynamisch verwaltet werden.</p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 * @see HauptPanel
 */
public class PanelThemenListe extends JPanel implements ListSelectionListener {
    JList<ThemaObject> themenListe;
    ListSelectionDelegate delegate;

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
     * <p>Ruft intern {@link #initList()} und {@link #addList()} auf.</p>
     */

    @Override
	public void valueChanged(ListSelectionEvent e) {
		
		if (e.getValueIsAdjusting()) {
			
        ThemaObject selected  = themenListe.getSelectedValue();
        delegate.recieveSelectedObject(selected); 
		}

	}
    
    public PanelThemenListe() {
        super();
        initList();
        addList();
    }

    private void initList() {

        String[] themen = { "Titel des Themas 1", "Titel des Themas 2", "Titel des Themas 3",
                "Titel des Themas 4", "Titel des Themas 5", "Titel des Themas 6",
                "Titel des Themas 7", "Titel des Themas 8", "Titel des Themas 9",
                "Titel des Themas 10" };

        themenListe = new JList<ThemaObject>();
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

	public void setListData(ArrayList<ThemaObject> listData) {
		
	 DefaultListModel<ThemaObject> model = new DefaultListModel<ThemaObject>();
		for (ThemaObject th : listData) {
			model.addElement(th);
		}
		themenListe.setModel(model);

		

//		// Optional: Aktualisiert die Anzeige, wenn die Liste geändert wurde
//		themenListe.revalidate();
//		themenListe.repaint();
//
//		// Setzt den Listener für die Auswahländerung
//		themenListe.addListSelectionListener(this);
//		
		
	}
}

