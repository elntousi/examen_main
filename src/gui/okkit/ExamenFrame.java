package gui.okkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Hauptfenster der Anwendung "Examen".
 * <p>
 * Das Frame setzt Titel, Standardgröße, zentriert sich auf dem Bildschirm und
 * lädt das {@link HauptPanel} als Inhaltsbereich. Die Klasse dient als
 * Einstiegspunkt und Container für das Haupt-GUI.
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 */
public class ExamenFrame extends JFrame {

	/**
	 * Erzeugt ein neues Hauptfenster und initialisiert die grundlegenden
	 * Eigenschaften (Titel, Close-Operation, Größe, Position) sowie das
	 * {@link HauptPanel} als Content Pane.
	 */
	public ExamenFrame() {
		setTitle("Examen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 480);
		setLocationRelativeTo(null);
		setContentPane(new HauptPanel());
	}

	/**
	 * Einstiegspunkt der Anwendung.
	 * <p>
	 * Startet das GUI im Event Dispatch Thread (EDT), sodass alle Swing-
	 * Operationen thread-sicher ausgeführt werden. Öffnet anschließend das
	 * {@link ExamenFrame}.
	 * </p>
	 *
	 * @param args Kommandozeilenparameter (werden nicht verwendet)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ExamenFrame().setVisible(true));
	}
}
