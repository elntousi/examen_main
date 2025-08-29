package io.github.elntousi.examen.data;

public class ThemaObject {

	private int id;
	private String titel;
	private String info;

	// --- DB table & columns ---
	public static final String DB_TABLE = "themen";
	public static final String COL_ID = "id";
	public static final String COL_TITEL = "titel";
	public static final String COL_INFO = "info";

	// --- SQL Queries als Konstanten ---
	public static final String DB_INSERT = "INSERT INTO " + DB_TABLE + " (" + COL_TITEL + ", " + COL_INFO
			+ ") VALUES (?, ?)";

	public static final String DB_DELETE = "DELETE FROM " + DB_TABLE + " WHERE " + COL_ID + " = ?";

	public static final String DB_SELECT_ALL = "SELECT " + COL_ID + ", " + COL_TITEL + ", " + COL_INFO + " FROM "
			+ DB_TABLE;

	// --- Getter & Setter ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		// Anforderung 2: Markiere Themen ohne Info mit "* "
		String t = (titel == null) ? "" : titel.trim();
		boolean ohneInfo = (info == null) || info.trim().isEmpty();
		return ohneInfo ? "* " + t : t;
	}
}
