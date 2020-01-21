/**
* Classe ActivationNotification.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.notifications;

import enstabretagne.simulation.components.IEntity;

// TODO: Auto-generated Javadoc
/**
 * The Interface ActivationNotification.
 */
@FunctionalInterface
public interface ActivationNotification {
	
	/**
	 * Notify activation.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	void NotifyActivation(IEntity sender, boolean starting);
}

