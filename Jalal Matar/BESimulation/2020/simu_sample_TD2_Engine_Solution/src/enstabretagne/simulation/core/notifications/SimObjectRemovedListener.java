/**
* Classe SimObjectRemovedListener.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.simulation.core.ISimObject;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving simObjectRemoved events.
 * The class that is interested in processing a simObjectRemoved
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSimObjectRemovedListener<code> method. When
 * the simObjectRemoved event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SimObjectRemovedEvent
 */
@FunctionalInterface
public interface SimObjectRemovedListener {
	
	/**
	 * Sim object removed.
	 *
	 * @param o the o
	 */
	void SimObjectRemoved(ISimObject o);
}

