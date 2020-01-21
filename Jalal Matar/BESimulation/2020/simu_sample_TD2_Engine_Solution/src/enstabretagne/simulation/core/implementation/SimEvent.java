/**
* Classe SimEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.messages.MessagesSimEngine;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimEvent;
import enstabretagne.simulation.core.ISimObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SimEvent.
 */
public abstract class SimEvent implements ISimEvent{

	/**
	 * Instantiates a new sim event.
	 */
	public SimEvent(){
		
	}
	
    /** The schedule date. */
    /// <summary>Logical date of the event occurrence</summary>
    private LogicalDateTime scheduleDate;
	
	/** The owner. */
	private ISimObject owner;
    
    /** The post date. */
    @SuppressWarnings("unused")
	private LogicalDateTime postDate;
	
	/** The init counter. */
	private static int initCounter;
    
    /** The init rank. */
    @SuppressWarnings("unused")
	protected int initRank;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#ScheduleDate()
	 */
	public LogicalDateTime ScheduleDate() {
		
		return scheduleDate;
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#resetProcessDate(enstabretagne.base.time.LogicalDateTime)
	 */
	public void resetProcessDate(LogicalDateTime simulationDate) {
		scheduleDate=simulationDate;
	}


	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ISimEvent e) {
		
	        // Case where not comparable
	        if (e == null){
	        	Logger.Error(this, "compareTo", MessagesSimEngine.ComparisonNotPossible);
//	        	throw new Exception(MessagesSimEngine.ComparisonNotPossible);
	        }
	        if (this == e)
	            return 0;

	        // Comparison of dates
	        int comparison = scheduleDate.compareTo(e.ScheduleDate());
	         return comparison;

	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#Owner()
	 */
	public ISimObject Owner() {
		
		return owner;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#TimeEventLine(int)
	 */
	public String TimeEventLine(int r) {
        return r +  ";Date = " + ScheduleDate() + ";"+ this.getClass().getName() +";Owner=" + Owner();
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#initialize(enstabretagne.simulation.core.ISimObject, enstabretagne.base.time.LogicalDateTime)
	 */
	public void initialize(ISimObject simObject, LogicalDateTime t) {
        owner = simObject;
        scheduleDate = t;
        
        if(owner!=null)
        	postDate = owner.CurrentDate();
        initRank = ++initCounter;

	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#terminate()
	 */
	public void terminate() {
        initRank = 0;		
	}

}

