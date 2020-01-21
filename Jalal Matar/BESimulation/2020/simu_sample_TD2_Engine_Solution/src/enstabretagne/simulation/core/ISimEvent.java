/*
 * 
 */
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISimEvent.
 */
public interface ISimEvent extends Comparable<ISimEvent>{
	
	/**
	 * Process.
	 */
	public void Process();
	
	/**
	 * Schedule date.
	 *
	 * @return the logical date time
	 */
	public LogicalDateTime ScheduleDate();
	
	/**
	 * Owner.
	 *
	 * @return the i sim object
	 */
	public ISimObject Owner();
	
	/**
	 * Initialize.
	 *
	 * @param simObject the sim object
	 * @param t the t
	 */
	public void initialize(ISimObject simObject, LogicalDateTime t);
	
	/**
	 * Terminate.
	 */
	public void terminate();
	
	/**
	 * Reset process date.
	 *
	 * @param simulationDate the simulation date
	 */
	public void resetProcessDate(LogicalDateTime simulationDate);
	
	/**
	 * Time event line.
	 *
	 * @param r the r
	 * @return the string
	 */
	public String TimeEventLine(int r);
}
