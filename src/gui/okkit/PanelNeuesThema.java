package gui.okkit;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel zur Eingabe eines neuen Themas.
 * <p>
 * Bietet ein Textfeld für den Titel und einen mehrzeiligen Info-Bereich, der
 * in eine {@link JScrollPane} eingebettet wird. Die Anordnung erfolgt mit
 * {@link GridBagLayout}; ein umschließender Titelrahmen kennzeichnet den Bereich
 * als „Neues Thema“.
 * </p>
 * <p>
 * Die tatsächliche Verarbeitung (Erstellen/Speichern) erfolgt außerhalb dieses Panels,
 * z. B. durch einen Controller.
 * </p>
 *
 * @author Eleni Ntousi
 * @version 1.0
 * @since 12 Aug 2025
 * @see HauptPanel
 */
public class PanelNeuesThema extends JPanel {

    JTextField titelFeld;
    JLabel titelFeldLabel;

    JLabel infoAreaLabel;
    JTextArea infoArea;

    /**
     * Erzeugt das Panel „Neues Thema“, initialisiert die UI-Komponenten und
     * fügt sie gemäß Layout hinzu.
     * <p>
     * Ruft intern {@link #initComponents()} und {@link #addComponents()} auf.
     * </p>
     */
    public PanelNeuesThema() {
        super();
        initComponents();
        addComponents();
    }

    /**
     * Initialisiert Labels und Eingabefelder.
     * <ul>
     *   <li>{@code titelFeld}: einzeiliges Textfeld (18 Spalten) für den Titel,</li>
     *   <li>{@code infoArea}: mehrzeilige Texteingabe (7×22) für zusätzliche Informationen.</li>
     * </ul>
     * Setzt außerdem die Beschriftungen „Titel“ und „Information zum Thema“.
     */
    void initComponents() {

        titelFeldLabel = new JLabel("Titel");
        titelFeld = new JTextField(18);

        infoAreaLabel = new JLabel("Information zum Thema");
        infoArea = new JTextArea(7, 22);
    }

    /**
     * Baut das Layout mit {@link GridBagLayout} auf, setzt einen
     * {@link javax.swing.border.TitledBorder TitledBorder} „Neues Thema“ und
     * positioniert die Komponenten:
     * <ol>
     *   <li>Label „Titel“ und Titel-Feld in der ersten Zeile,</li>
     *   <li>Label „Information zum Thema“ in der zweiten Zeile über zwei Spalten,</li>
     *   <li>{@link JScrollPane} mit der {@code infoArea} in der dritten Zeile,
     *       dehnt sich horizontal und vertikal aus.</li>
     * </ol>
     */
    void addComponents() {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Neues Thema"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(titelFeldLabel, gbc);

        gbc.gridx = 1;
        add(titelFeld, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 6, 6, 6);
        add(infoAreaLabel, gbc);

        JScrollPane infoScroll = new JScrollPane(infoArea);
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(infoScroll, gbc);
    }
}
