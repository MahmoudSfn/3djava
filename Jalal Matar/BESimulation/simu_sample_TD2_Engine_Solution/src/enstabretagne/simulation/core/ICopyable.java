/**
* Classe ICopyable.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICopyable.
 */
/// <summary>Common minimum interface to be state or rate using Runge-Kutta.</summary>
public interface ICopyable {
	    /// <summary>Copy the content of its argument in this ICopyable</summary>
	    /**
    	 * Copy from.
    	 *
    	 * @param item the item
    	 */
    	/// <param name="item">copied item</param>
	    void CopyFrom(Object item);
}

