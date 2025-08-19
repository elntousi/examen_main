package data;

import java.util.ArrayList;

public class DataManager {
	

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
}
