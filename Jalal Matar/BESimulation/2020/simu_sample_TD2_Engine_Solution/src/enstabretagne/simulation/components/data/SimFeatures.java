/**
* Classe SimFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.data;

// TODO: Auto-generated Javadoc
/**
 * The Class SimFeatures.
 */
public abstract class SimFeatures {
	
	/** The id. */
	private String id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Instantiates a new sim features.
	 *
	 * @param id the id
	 */
	public SimFeatures(String id) {
		this.id = id;
	}
}

