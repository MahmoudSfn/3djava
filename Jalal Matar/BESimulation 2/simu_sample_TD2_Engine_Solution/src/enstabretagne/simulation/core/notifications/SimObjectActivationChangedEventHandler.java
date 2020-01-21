/**
* Classe SimObjectActivationChangedEventHandler.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.simulation.core.ISimObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface SimObjectActivationChangedEventHandler.
 */
@FunctionalInterface
public interface SimObjectActivationChangedEventHandler {
    
    /**
     * Sim object activation changed.
     *
     * @param o the o
     * @param active the active
     */
    void simObjectActivationChanged(ISimObject o, boolean active);
}

