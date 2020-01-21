/**
* Classe TerminatingNotification.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.notifications;

import enstabretagne.simulation.components.IEntity;

// TODO: Auto-generated Javadoc
/**
 * The Interface TerminatingNotification.
 */
@FunctionalInterface
public interface TerminatingNotification {
	
	/**
	 * Notify terminating.
	 *
	 * @param sender the sender
	 * @param restart the restart
	 */
	void NotifyTerminating(IEntity sender,boolean restart);
}

