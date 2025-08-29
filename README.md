# examen_main – Java Swing

👤 Author
elntousi — GitHub: https://github.com/elntousi/examen_main

Kleine, übersichtliche Themen-Verwaltungsapp (Titel + Info) mit **Java Swing** und **MySQL**.  
Fokus: klare GUI, saubere DB-Anbindung, und ein paar hilfreiche UX-Details.

## ✨ Features
- **Speichern/Löschen** mit sofortigem **Refresh** der Liste aus der Datenbank.
- Bestätigungsdialog beim Speichern **nur mit Titel**: _„Nur Titel speichern?“_ (**OK** / **Abbrechen**).
- **Themenliste**: Themen **ohne Info** werden mit `*` markiert und **zuerst** angezeigt; danach alphabetisch (deutscher **Collator**).
- **SQL-Queries als Konstanten** im Modell (`ThemaObject`) – kein Hardcoding im `DataManager`.
- Paketstruktur nach GitHub-Schema: `io.github.elntousi.examen` (`ui`, `data`).

## 📸 Finales UI
Vorschau nach allen Anpassungen (Sortierung, Sternchen, Dialog):

![Themenliste – sortiert und markiert](./assets/final-ui.png)

> Falls die Grafik nicht angezeigt wird: Screenshot als `assets/final-ui.png` ins Repo legen.

▶️ Starten
Main-Klasse: io.github.elntousi.examen.ui.ExamenFrame

In Eclipse: ExamenFrame → Run As → Java Application

JDBC-Treiber (mysql-connector-j) muss im Classpath liegen.

🧱 Projektstruktur
src/
└─ io/github/elntousi/examen/
   ├─ ui/
   │  ├─ ExamenFrame.java
   │  ├─ HauptPanel.java
   │  ├─ PanelNeuesThema.java
   │  ├─ PanelThemenListe.java
   │  └─ PanelAktionen.java
   └─ data/
      ├─ DataManager.java
      └─ ThemaObject.java

ℹ️ Hinweise
AUTO_INCREMENT-IDs können Lücken haben (z. B. nach Löschungen) – das ist normal.

Für produktionsnahe Setups DB-Zugangsdaten in ENV/Properties auslagern.
