/**
* Classe NotifySimTimeEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.base.time.LogicalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotifySimTimeEvent.
 */
@FunctionalInterface
public interface NotifySimTimeEvent {
	
	/**
	 * Notify sim time event.
	 *
	 * @param t the t
	 */
	void notifySimTimeEvent(LogicalDateTime t);
}

