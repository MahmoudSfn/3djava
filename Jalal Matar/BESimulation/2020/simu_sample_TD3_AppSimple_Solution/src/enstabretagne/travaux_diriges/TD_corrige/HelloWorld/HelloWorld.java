/*
 * 
 */
package enstabretagne.travaux_diriges.TD_corrige.HelloWorld;

import java.time.Instant;
import enstabretagne.monitor.implementation.UniversalMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloWorld.
 */
public class HelloWorld{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Debut =" + Instant.now());
		
		
		//création du moniteur
		UniversalMonitor um = new UniversalMonitor();

		um.loadExperiencePlanFromSettings();
		um.runPlan();
		um.exit();
				
		System.out.println("Fin : " + Instant.now());
	}


}
