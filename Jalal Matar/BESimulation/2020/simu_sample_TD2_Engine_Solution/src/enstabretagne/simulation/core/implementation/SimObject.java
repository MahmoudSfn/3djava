/**
* Classe SimObject.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.messages.MessagesSimEngine;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.EngineInterruptReason;
import enstabretagne.simulation.core.IEngine;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.ISimEvent;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.SortedList;
import enstabretagne.simulation.core.notifications.SimObjectActivationChangedEventHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class SimObject.
 */
public abstract class SimObject implements ISimObject{

	/** The is local. */
	boolean isLocal=true;
	
	/**
	 * Instantiates a new sim object.
	 */
	protected SimObject(){
		ObjID++;
	    simObjID=ObjID;

	    isLocal = true;
	    reinitSimObject();
	    Logger.Detail(this,"SimObject",MessagesSimEngine.Creating, MessagesSimEngine.UnnamedUnregistered);
	}
	
	/**
	 * Reinit sim object.
	 */
	protected void reinitSimObject() {
	      timeEvents = new SortedList<ISimEvent>();
	      simObjectActivationChangedListeners = new ArrayList<SimObjectActivationChangedEventHandler>();
		
	}
	
	
	/**
	 * Gets the current logical date.
	 *
	 * @return the current logical date
	 */
	protected LogicalDateTime getCurrentLogicalDate() {
		return getEngine().SimulationDate();
	}
	    

    /** The engine. */
    ISimEngine engine;
	
	/**
	 * Gets the engine.
	 *
	 * @return the engine
	 */
	public IEngine getEngine()
	{
		return engine;
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#interruptEngineByDate()
	 */
	public void interruptEngineByDate()
	{
		engine.interrupt(EngineInterruptReason.ByDate);
	}
	
	/**
	 * Sets the engine.
	 *
	 * @param value the new engine
	 */
	public void setEngine(ISimEngine value)
	{	        // Reaffectation of same engine : nothing to do
        if (value == engine)
            return;

          // Deconnection from engine
          if (engine != null)
          {
            if (IsActive())
              deactivate();
            engine.OnSimObjectRemoved(this);
          }

          // Connection to new engine
          engine = value;
          if (engine != null)
          {
            engine.OnSimObjectAdded(this);
            if (IsActive())
              activate();
          }
          
          
	}
	

    /**
     * Checks if is active.
     *
     * @return true, if successful
     */
    public abstract boolean IsActive();

    /** The sim object activation changed listeners. */
    List<SimObjectActivationChangedEventHandler> simObjectActivationChangedListeners;
	
	/**
	 * Activate.
	 */
	protected void activate() {
	      // add local timeEvent to engine Event list
	      for(ISimEvent ev : timeEvents)
	        engine.OnEventPosted(ev);

	      // Fire event
	      if (simObjectActivationChangedListeners.size()>0)
	    	  simObjectActivationChangedListeners.forEach((l)->l.simObjectActivationChanged(this, true));		
	}

    /**
     * Deactivate.
     */
    protected void deactivate() {
	      // remove local timeEvents from engine Event list 
	      for(ISimEvent ev : timeEvents)
	        engine.OnEventUnPosted(ev);

	      // Fire event
	      if (simObjectActivationChangedListeners.size()>0)
	    	  simObjectActivationChangedListeners.forEach((l)->l.simObjectActivationChanged(this, false));
	      
	      engine.OnSimObjectRemoved(this);
	}

    /* (non-Javadoc)
     * @see enstabretagne.simulation.core.ISimObject#terminate(boolean)
     */
    public void terminate(boolean restart) {
	      while(timeEvents.size()>0)
	    	  UnPost(timeEvents.first());
	      timeEvents = null;
	      
	}
	
	/** The name. */
	private String name;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#getName()
	 */
	public String getName() {
		if(name!=null && name!="")
			return name;
		else
			return "#" + simObjID;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param value the new name
	 */
	protected void setName(String value) {
		 if (IsActive())
	          Logger.Warning(this,"setName",MessagesSimEngine.Naming, MessagesSimEngine.AttemptToSetName0OnActiveObject, value);
	        else
	          name = value;		
	}


	/** The Obj ID. */
	private static int ObjID;
	
	/**
	 * Created object count.
	 *
	 * @return the int
	 */
	public static int CreatedObjectCount() {	
		return ObjID;
	}

	/** The deleted object count. */
	private static int deletedObjectCount;
	
	/**
	 * Deleted object count.
	 *
	 * @return the int
	 */
	public static int DeletedObjectCount() {
		return deletedObjectCount;
	}


	/** The sim obj ID. */
	private int simObjID;	
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#getSimObjID()
	 */
	public int getSimObjID(){
		return simObjID;
	}
	
	
	/** The time events. */
	private SortedList<ISimEvent> timeEvents;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#Post(enstabretagne.simulation.core.ISimEvent)
	 */
	public void Post (ISimEvent ev)
	{
	      Post(ev, CurrentDate());
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#Post(enstabretagne.simulation.core.ISimEvent, enstabretagne.base.time.LogicalDateTime)
	 */
	public void Post (ISimEvent ev,LogicalDateTime t) {
	      ev.initialize(this, t);
	      Logger.Detail(ev.Owner(),"Post",MessagesSimEngine.PostingAt0, ev.ScheduleDate());
	      timeEvents.add(ev);
	      if (IsActive() && (engine != null))
	        engine.OnEventPosted(ev);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#Post(enstabretagne.simulation.core.ISimEvent, enstabretagne.base.time.LogicalDuration)
	 */
	public void Post (ISimEvent ev,LogicalDuration dt) {
	      Post(ev, CurrentDate().add(dt));
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#UnPost(enstabretagne.simulation.core.ISimEvent)
	 */
	public void UnPost(ISimEvent ev) {
	      if (IsActive() && (engine != null))
	          engine.OnEventUnPosted(ev);
	        Logger.Detail(this,"UnPost",MessagesSimEngine.UnpostingWhileScheduledAt0, ev.ScheduleDate());
	        timeEvents.remove(ev);
	        ev.terminate();		
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#UnPostAllEvents()
	 */
	public void UnPostAllEvents(){
		while(timeEvents.size()>0){
			ISimEvent ev = timeEvents.first();
			UnPost(ev);
		}
	}


	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#CurrentDate()
	 */
	public LogicalDateTime CurrentDate() {
        if (engine == null)
            return null;
          return engine.SimulationDate();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		deletedObjectCount++;
		Logger.Information(this, "finalize", MessagesSimEngine.Deleting, MessagesSimEngine.Id0, simObjID);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}

