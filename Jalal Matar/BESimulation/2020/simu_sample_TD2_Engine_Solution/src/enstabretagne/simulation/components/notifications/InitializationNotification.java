/**
* Classe InitializationNotification.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.notifications;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimInitParameters;

// TODO: Auto-generated Javadoc
/**
 * The Interface InitializationNotification.
 */
@FunctionalInterface
public interface InitializationNotification {
	
	/**
	 * Notify initialization.
	 *
	 * @param sender the sender
	 * @param init the init
	 */
	void NotifyInitialization(IEntity sender,SimInitParameters init);
}

