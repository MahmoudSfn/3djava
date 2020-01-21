/**
* Classe SimObjectAddedListener.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.simulation.core.ISimObject;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving simObjectAdded events.
 * The class that is interested in processing a simObjectAdded
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSimObjectAddedListener<code> method. When
 * the simObjectAdded event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SimObjectAddedEvent
 */
@FunctionalInterface
public interface SimObjectAddedListener {
	
	/**
	 * Sim object added.
	 *
	 * @param obj the obj
	 */
	void SimObjectAdded(ISimObject obj);
}


