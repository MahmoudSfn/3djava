/**
* Classe NotifySimEngineEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.simulation.core.ISimTimeEvent;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotifySimEngineEvent.
 */
@FunctionalInterface
public interface NotifySimEngineEvent {
	
	/**
	 * Notify sim engine event.
	 *
	 * @param ev the ev
	 */
	void notifySimEngineEvent(ISimTimeEvent ev);
}

