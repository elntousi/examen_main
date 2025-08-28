package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataManager {

	// === Thema speichern ===
	public String saveThema(ThemaObject thema) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement stmt = connection.prepareStatement(ThemaObject.DB_INSERT, // Verwendung der Konstanten
						Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, thema.getTitel());
			stmt.setString(2, thema.getInfo());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				thema.setId(rs.getInt(1)); // Generierte ID zurück ins Thema-Objekt
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Das Thema '" + thema.getTitel() + "' wurde erfolgreich gespeichert.";
	}

	// === Thema löschen ===
	public boolean deleteThema(ThemaObject thema) {
		if (thema == null)
			return false;

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = con.prepareStatement(ThemaObject.DB_DELETE)) { // Verwendung der Konstanten

			ps.setInt(1, thema.getId());
			int rows = ps.executeUpdate();
			return rows > 0; // true, wenn Thema gelöscht wurde

		} catch (SQLException e) {
			System.err.println("DB delete failed: " + e.getMessage());
			return false;
		}
	}

	// === Alle Themen laden ===
	public ArrayList<ThemaObject> ladeAlleThemen() {
		ArrayList<ThemaObject> liste = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = con.prepareStatement(ThemaObject.DB_SELECT_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				ThemaObject t = new ThemaObject();
				t.setId(rs.getInt(ThemaObject.COL_ID));
				t.setTitel(rs.getString(ThemaObject.COL_TITEL));
				t.setInfo(rs.getString(ThemaObject.COL_INFO));
				liste.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	// === DB Credentials ===
	private static final String URL = "jdbc:mysql://localhost:3306/examen_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";
}
