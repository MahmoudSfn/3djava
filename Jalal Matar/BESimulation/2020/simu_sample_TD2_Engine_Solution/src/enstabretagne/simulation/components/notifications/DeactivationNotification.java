/**
* Classe DeactivationNotification.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.notifications;

import enstabretagne.simulation.components.IEntity;

// TODO: Auto-generated Javadoc
/**
 * The Interface DeactivationNotification.
 */
@FunctionalInterface
public interface DeactivationNotification {
	
	/**
	 * Notify deactivation.
	 *
	 * @param sender the sender
	 * @param stopping the stopping
	 */
	void NotifyDeactivation(IEntity sender, boolean stopping);
}

