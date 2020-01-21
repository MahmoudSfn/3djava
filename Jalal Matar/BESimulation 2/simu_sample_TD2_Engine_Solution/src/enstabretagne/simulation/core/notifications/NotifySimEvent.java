/**
* Classe NotifySimEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.simulation.core.ISimEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotifySimEvent.
 */
@FunctionalInterface
public interface NotifySimEvent {
	
	/**
	 * Notify sim event.
	 *
	 * @param ev the ev
	 */
	void notifySimEvent(ISimEvent ev);
}

