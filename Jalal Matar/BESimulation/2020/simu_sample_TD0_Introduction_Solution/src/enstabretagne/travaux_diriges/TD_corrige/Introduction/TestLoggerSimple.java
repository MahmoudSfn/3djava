/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import enstabretagne.base.logger.IRecordable;
import enstabretagne.base.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class TestLoggerSimple.
 */
public class TestLoggerSimple {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//Version compliquée utilisant les classes anonymes 
		Logger.Data(new IRecordable() {
			
			@Override
			public String[] getTitles() {
				return new String[]{"Modèle","Marque"};
			}
			
			@Override
			public String[] getRecords() {
				// TODO Auto-generated method stub
				return new String[]{"807","Peugeot"};
			}
			
			@Override
			public String getClassement() {
				return "Voiture";
			}
		});
		
		//Version simplifiée d'usage du logger
		Logger.DataSimple("Test", "Nom","Prénom","Age");
		Logger.DataSimple("Test", "SKYWALKER","Anakin",30.0);
		Logger.DataSimple("Test", "KENOBI","ObiOne",80);
		Logger.DataSimple("Test", "MASTER","Yoda",900);
		Logger.DataSimple("TestON", "Name","Power");
		Logger.DataSimple("TestON", "SAURON",8.0);
		Logger.DataSimple("TestON", "GANDALF",18.0);
		Logger.Information(null, "main", "cela fonctionnne!");
		
		Logger.Terminate();
	}
}
