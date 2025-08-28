package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DataManager {
	
	public String saveThema(ThemaObject thema) {
		
		Connection connection= null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			String command = "INSERT INTO themen (titel, info) VALUES (?, ?)";
			
			PreparedStatement stmt = connection.prepareStatement(
				    command, Statement.RETURN_GENERATED_KEYS
				);

			stmt.setString(1, thema.getTitel());
			stmt.setString(2, thema.getInfo());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				thema.setId(rs.getInt(1)); // Setze die generierte ID zurück ins Thema-Objekt
			}
			
			stmt.close();	
			
			} catch (SQLException e) {
				e.printStackTrace();
		}

		return "Das Thema '" + thema.getTitel() + "' wurde erfolgreich gespeichert.";
	}
	
	// DataManager.java  (package data)

	public boolean deleteThema(ThemaObject thema) {
	    if (thema == null) return false;

	    // 1) Προσπάθεια διαγραφής SQL
	    final String sql = "DELETE FROM themen WHERE id = ?";

	    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, thema.getId());
	        int rows = ps.executeUpdate();
	        if (rows > 0) {
	            return true; // OK, διαγράφηκε από τη ΒΔ
	        }
	    } catch (SQLException e) {
	        // Δεν ρίχνουμε τη διεργασία — κάνουμε fallback
	        System.err.println("DB delete failed, fallback to in-memory: " + e.getMessage());
	    }
		return false;

	}

	
	public ArrayList<ThemaObject> ladeAlleThemen() {
        ArrayList<ThemaObject> liste = new ArrayList<>();
        
        ThemaObject thema;
        
        for (int i = 1; i <= 10; i++) {
        
            thema = new ThemaObject();
            thema.setTitel("Titel des Thema Nr. " + i);
            thema.setInfo("Information zum Thema Nr." + i);
            
            liste.add(thema);
        }
        return liste;
        }

	
	private static final String URL ="jdbc:mysql://localhost:3306/examen_db";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
}
