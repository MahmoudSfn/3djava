/**
* Classe SimEngineEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.base.time.LogicalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Interface SimEngineEvent.
 */
@FunctionalInterface
public interface SimEngineEvent {
	
	/**
	 * Sim engine event.
	 *
	 * @param t the t
	 */
	void simEngineEvent(LogicalDateTime t);
}

