/**
* Classe SimObjectRequest.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

// TODO: Auto-generated Javadoc
/**
 * The Interface SimObjectRequest.
 */
@FunctionalInterface
public interface SimObjectRequest {

	/**
	 * Filter.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	boolean filter(ISimObject o);
	
}

