/*
 * 
 */
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISimTimeEvent.
 */
public interface ISimTimeEvent extends ISimEvent{
	
	/**
	 * Gets the process date.
	 *
	 * @return the process date
	 */
	public LogicalDateTime getProcessDate();
}
