/**
* Classe CreationNotification.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.notifications;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;

// TODO: Auto-generated Javadoc
/**
 * The Interface CreationNotification.
 */
@FunctionalInterface
public interface CreationNotification {
	
	/**
	 * Notify creation.
	 *
	 * @param sender the sender
	 * @param name the name
	 * @param params the params
	 */
	void NotifyCreation(IEntity sender,String name, SimFeatures params);
}

