/**
* Classe NotifyLogicalDateChange.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.notifications;

import enstabretagne.base.time.LogicalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotifyLogicalDateChange.
 */
@FunctionalInterface
public interface NotifyLogicalDateChange {
	
	/**
	 * Notify logical date change.
	 *
	 * @param t the t
	 */
	void notifyLogicalDateChange(LogicalDateTime t);
}

