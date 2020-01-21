/**
* Classe LoggerAndProba.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import enstabretagne.base.logger.CategoriesGenerator;
import enstabretagne.base.logger.CategoriesGenerator.Segment;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.math.MoreRandom;

// TODO: Auto-generated Javadoc
/**
 * The Class LoggerAndProba.
 */
@ToRecord(name="TD0")
public class LoggerAndProba{

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		// LoggerAndProba est un moniteur
		LoggerAndProba lap = new LoggerAndProba();
		lap.run();
		// Cloture des logger et qui procède notammnt à l'écriture du fichier
		// Excel
		Logger.Terminate();

	}

	/** The etat gaussien. */
	private double etatGaussien;
	
	/**
	 * Gets the etat gaussien.
	 *
	 * @return the etat gaussien
	 */
	@ToRecord(name="x")
	public double getEtatGaussien() {
		return etatGaussien;
	}

	/** The random. */
	private MoreRandom random;

	/**
	 * Gets the segment.
	 *
	 * @return the segment
	 */
	@ToRecord(name="cat")
	public Segment getSegment()
	{
		return cg.getSegmentOf(etatGaussien);
	}
	
	/** The cg. */
	CategoriesGenerator cg;
	
	/**
	 * Instantiates a new logger and proba.
	 */
	public LoggerAndProba() {

		Logger.Information(this, "Contructeur", "Construit!");

		cg = new CategoriesGenerator(-5, 5, 20, 2, 2);
		random = new MoreRandom();
	}

	/**
	 * Run.
	 */
	public void run() {
		int i = 0;
		
		for(i=0;i<100;i++) {
			etatGaussien = random.nextGaussian();
			
			Logger.Data(this);

		}
	}

}
