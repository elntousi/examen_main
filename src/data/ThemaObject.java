package data;

public class ThemaObject {
	
	private int id;
	
	private String titel;
	private String info;
	
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

	public String toString() {
		return titel;
	}
}
