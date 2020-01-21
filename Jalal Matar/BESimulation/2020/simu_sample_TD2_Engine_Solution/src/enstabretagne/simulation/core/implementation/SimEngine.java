/**
* Classe SimEngine.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import enstabretagne.base.Settings;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.messages.MessagesSimEngine;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.data.SimScenarioInit;
import enstabretagne.simulation.core.EngineActivity;
import enstabretagne.simulation.core.EngineInterruptReason;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.ISimEvent;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.ISimObjectDictionary;
import enstabretagne.simulation.core.ISimObjectType;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.simulation.core.SortedList;
import enstabretagne.simulation.core.notifications.NotifyLogicalDateChange;
import enstabretagne.simulation.core.notifications.NotifySimEngineEvent;
import enstabretagne.simulation.core.notifications.NotifySimEvent;
import enstabretagne.simulation.core.notifications.NotifySimTimeEvent;
import enstabretagne.simulation.core.notifications.SimEngineEvent;
import enstabretagne.simulation.core.notifications.SimObjectAddedListener;
import enstabretagne.simulation.core.notifications.SimObjectRemovedListener;

// TODO: Auto-generated Javadoc
/**
 * The Class SimEngine.
 */
public class SimEngine implements ISimEngine{
        /// <summary>

        /** The Constant Starting. */
        private static final EngineActivity Starting = new EngineActivity(EngineActivity.PausedWaiting.engineActivityValue() | EngineActivity.Initializing.engineActivityValue(),"Starting");
        
        /** The Constant Running. */
        private static final EngineActivity Running = new EngineActivity(EngineActivity.TimeEvent.engineActivityValue() ,"Running");
        
        /** The Constant Stopping. */
        private static final EngineActivity Stopping = new EngineActivity(EngineActivity.PausedWaiting.engineActivityValue() | EngineActivity.Finalizing.engineActivityValue(),"Stopping");

        /** The Constant AllowReadWrite. */
        private static final EngineActivity AllowReadWrite = new EngineActivity(Starting.engineActivityValue() | Stopping.engineActivityValue() | EngineActivity.TimeEvent.engineActivityValue(),"Allow Read Write");
        
        /** The Constant AllowRead. */
        private static final EngineActivity AllowRead = new EngineActivity(AllowReadWrite.engineActivityValue() ,"Read Only");
        

        /** The is integrity checked. */
        //----------------------- Integrity checking -------------------------------
        private final boolean isIntegrityChecked;
		
		/** The app type. */
		private Class<? extends ISimObject> appType;

		/** The current scenario. */
		private IScenario currentScenario;
		
        /**
         * Gets the current scenario.
         *
         * @return the current scenario
         */
        private IScenario getCurrentScenario() {
			return currentScenario;
		}
        
		/// <summary>
        /// set engine state
        /// </summary>
        /**
		 * Sets the activity.
		 *
		 * @param state the new activity
		 */
		/// <param name="state"></param>
        protected void setActivity(EngineActivity state)
        {
            if (activity == state)
                return;
            EngineActivity old = activity;
            activity = state;
            Logger.Detail(this,"setActivity",MessagesSimEngine.SwitchedFrom0, old);

            if (onStatusChangedListeners.size()>0)
                if (((old.engineActivityValue() == EngineActivity.NotInitialized.engineActivityValue()) && (activity.engineActivityValue() == EngineActivity.Initializing.engineActivityValue()))
                  || ((old.engineActivityValue() & Starting.engineActivityValue()) != 0 && (activity.engineActivityValue() & Running.engineActivityValue()) != 0)
                  || ((old.engineActivityValue() & Running.engineActivityValue()) != 0 && (activity.engineActivityValue() & Stopping.engineActivityValue()) != 0))
                    onStatusChangedListeners.forEach((listener) -> listener.notifyLogicalDateChange(simulationDate));
        }

        /**
         * Check write allowed.
         *
         * @param operation the operation
         */
        void checkWriteAllowed(String operation)
        {
            if (isIntegrityChecked && (activity.engineActivityValue() & AllowReadWrite.engineActivityValue()) == 0)
                Logger.Error(this, "CheckWriteAllowed",MessagesSimEngine.Operation0RequiresWRITEPermissionAvailableWhile1, operation, AllowReadWrite);
        }

        /**
         * Check read allowed.
         *
         * @param operation the operation
         */
        void checkReadAllowed(String operation)
        {
            if (isIntegrityChecked && (activity.engineActivityValue() & AllowRead.engineActivityValue()) == 0)
            	Logger.Error(this,MessagesSimEngine.Operation0RequiresREADPermissionAvailableWhereas1, operation, AllowRead);
        }

        /**
         * Checks if is read allowed.
         *
         * @return true, if is read allowed
         */
        boolean isReadAllowed()
        {
            return !(isIntegrityChecked && (activity.engineActivityValue() & AllowRead.engineActivityValue()) == 0);
        }

        /**
         * Checks if is write allowed.
         *
         * @return true, if is write allowed
         */
        boolean isWriteAllowed()
        {
            return !(isIntegrityChecked && (activity.engineActivityValue() & AllowReadWrite.engineActivityValue()) == 0);
        }

        
		
         //---------------------- Provided Interface --------------------------------
        /// <summary>
        /// Default constructor (for designers)
        /// </summary>
        /// <param name="monitor">Monitor of the engine</param>
        /// <param name="replica">Index of the current replica</param>
        /// <param name="replicaSeed">Seed for the replica.</param>
        /// <param name="appType">Simulation application (derives from or is <see cref="T:DirectSim.Simulation.Components.SimApp">SimApp</see>)</param>
        /// <param name="appName">Name of the simulation application</param>
        /// <param name="scenario">Scenario</param>
        /// <remarks>
        /// The construction of the engine accesses the following configuration variables (private, and
        /// then global to the monitor) :
        /// <list type="bullet">
        /// <item>SimulationEngine.IsEngineIntegrityChecked (default is true)</item>
        /// <item>SimulationEngine.DefaultSynchroOrder (default is 2)</item>
        /// <item>SimulationEngine.UsePortableRandomGenerator (default is false)</item>
        /// <item>SimulationEngine.UseOneRandomGeneratorPerAgent</item>
        /// </list>
        /**
          * Instantiates a new sim engine.
          */
         /// </remarks>
        public SimEngine(){
        	onStatusChangedListeners = new ArrayList<NotifyLogicalDateChange>();
        	onBeforeTimeEventProcessedListeners = new ArrayList<NotifySimEvent>();
        	onDateChanged = new ArrayList<SimEngineEvent>();
        	onDateChangedListeners = new ArrayList<NotifyLogicalDateChange>();
        	onSimEngineEventProcessed = new ArrayList<NotifySimEngineEvent>();
        	onTimeEventProcessed= new ArrayList<NotifySimTimeEvent>();
        	objectDictionary.initialize(this);
        
        	//TODO vérifier ces deux trucs ci dessous
            // Initialise fields
//            this.appType = appType;
//		    this.appName = appName;
 
            
//            this.initialCalendarDate = monitor.InitialCalendarDate();
            this.isIntegrityChecked = Settings.IsEngineIntegrityChecked();
            
        }
        
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#init(enstabretagne.simulation.core.IScenario, enstabretagne.simulation.components.data.SimScenarioInit)
         */
        public void init(IScenario currentScenario, SimScenarioInit initScenario)
        {
        	
        	this.currentScenario=currentScenario;
        	currentScenario.setEngine(this);

            InitialDate=getCurrentScenario().getStartDateTime();
            FinalDate=getCurrentScenario().getEndDateTime();

            this.simulationDate = InitialDate;
            this.allTimeEvents = new SortedList<ISimEvent>();

            // initialize statistic
            initialCreatedObjectCount = SimObject.CreatedObjectCount();
            initialDeletedObjectCount = SimObject.CreatedObjectCount();
            initialMemoryLevel = (int)Runtime.getRuntime().totalMemory();
            
            elapsedRealTime = Duration.ZERO;
            stepCount = eventCount = 0;


            // initialize randomfactory
            this.generator = new MoreRandom((int) getCurrentScenario().getSeed());
            Logger.Information(getCurrentScenario(),"SimEngine - Generator", "Générateur créé avec la graine "+((int) getCurrentScenario().getSeed()));


            OnDateChanged(); // $CS Notify date change due to date (re)initialization
            setActivity(EngineActivity.Initializing);
            
            ((SimScenario) this.currentScenario).initialize(initScenario);
        } // SimEngine() 

        /**
         * Activity.
         *
         * @return the engine activity
         */
        /// <summary>Activity of the engine (from an external point of view)</summary>
        public EngineActivity Activity() { return activity;  }

        /**
         * Checks if is initialized.
         *
         * @return true, if successful
         */
        /// <summary>Is the engine initialized ?</summary>
        public boolean IsInitialized() { return (activity.engineActivityValue() & EngineActivity.NotInitialized.engineActivityValue()) == 0; }

        /**
         * Checks if is running.
         *
         * @return true, if successful
         */
        /// <summary>Is the engine running ?</summary>
        public boolean IsRunning() { return (activity.engineActivityValue() & Running.engineActivityValue()) != 0; }

        /**
         * Clean up.
         *
         * @param restart the restart
         */
        private void CleanUp(boolean restart)
        {
            setActivity(EngineActivity.Finalizing);
            try
            {
            	getCurrentScenario().terminate(restart);
            }
            catch (Exception ex)
            {
            	Logger.Detail(this,"CleanUp",MessagesSimEngine.UnhandledException0);// App.Error("CleanUp"ex);
            }


            int n;
            n = eventListFinalize();
            if (n > 0)
                Logger.Error(this,"CleanUp",MessagesSimEngine.ZombiEventsWhenFinalizing, n);


            n = objectDictionary.terminate(restart);
            if (n > 0)
            	Logger.Error(this,"CleanUp",MessagesSimEngine.ZombiObjectsWhenFinalizing, n);

            int memoryLevelInReplica = (int) Runtime.getRuntime().totalMemory() - initialMemoryLevel;
            Runtime.getRuntime().gc();

            int createdObjectInReplica = SimObject.CreatedObjectCount() - initialCreatedObjectCount;
            int deletedObjectInReplica = SimObject.DeletedObjectCount() - initialDeletedObjectCount;

            Logger.Information(this,"CleanUp",MessagesSimEngine.CreatedObjects0Remain1, createdObjectInReplica, createdObjectInReplica - deletedObjectInReplica);
            Logger.Information(this,"CleanUp",MessagesSimEngine.MemoryBeforeGC0AfterGC1, memoryLevelInReplica, Runtime.getRuntime().totalMemory() - initialMemoryLevel);
            
            setActivity(EngineActivity.NotInitialized);
        }

        /**
         * Event list finalize.
         *
         * @return the int
         */
        private int eventListFinalize()
        {
            if (allTimeEvents == null)
                return 0;
            int n = allTimeEvents.size();
            if (n > 0)
            {
                for(ISimEvent ev : allTimeEvents)
                	Logger.Information(ev,"enventListFinalize",MessagesSimEngine.ZombiEventFrom0, ev.Owner().getClass().getName());
                allTimeEvents.clear();
            }
            return n;
        }


        /// <summary>
        /// Random number generator
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.IEngine#getRandomGenerator()
         */
        /// </summary>
        public MoreRandom getRandomGenerator()
        {
                //return randomfactory.Generator;
                return generator;
        }

        /// <summary>
        /// Simulation time : any SimObject is at most at that time in the simulation.
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.IEngine#SimulationDate()
         */
        /// </summary>
        public LogicalDateTime SimulationDate()
        {
                return simulationDate;
        }

        /** The Initial date. */
        /// <summary>Initial date</summary>
        public static LogicalDateTime InitialDate ;

        /** The Final date. */
        /// <summary>Initial date</summary>
        public static LogicalDateTime FinalDate = LogicalDateTime.MaxValue;

        /// <summary>
        /// Event fired when the simulation is started.
        /** The on status changed listeners. */
        /// </summary>
        private List<NotifyLogicalDateChange> onStatusChangedListeners;

        /// <summary>
        /// Event fired each time the simulation date has changed
        /** The on date changed listeners. */
        /// </summary>
        @SuppressWarnings("unused")
        private List<NotifyLogicalDateChange> onDateChangedListeners;

        /// <summary>
        /// Event fired before a strong Time Event is being processed
        /** The on before time event processed listeners. */
        /// </summary>
        private List<NotifySimEvent> onBeforeTimeEventProcessedListeners;
        
        /// <summary>
        /// Event fired each time a strong Time Event has been processed
        /** The on sim engine event processed. */
        /// </summary>
        @SuppressWarnings("unused")
        private List<NotifySimEngineEvent> onSimEngineEventProcessed;        
        
        /** The on time event processed. */
        private List<NotifySimTimeEvent> onTimeEventProcessed;
        /// <summary>
        /// Label for the engine
        /// </summary>
        /**
         * To string.
         *
         * @return the string
         */
        /// <returns></returns>
        public String ToString()
        {
            return getCurrentScenario() == null ? this.getClass().getName()  : getCurrentScenario().getName();
        }
        
        //------------------------ private members ---------------------------------


        /** The interrupter. */
        private EngineInterruptReason interrupter;
        
        /** The event count. */
        @SuppressWarnings("unused")
       private int stepCount, eventCount;

        /** The initial deleted object count. */
        private int initialCreatedObjectCount, initialDeletedObjectCount;
        
        /** The initial memory level. */
        private int initialMemoryLevel;
        
        /** The elapsed real time. */
        private Duration elapsedRealTime;
        
        /** The last start date. */
        private Temporal lastStartDate;

        /**
         * Last interrupt reason.
         *
         * @return the engine interrupt reason
         */
        @SuppressWarnings("unused")
		private EngineInterruptReason LastInterruptReason() { return interrupter; }

        /**
         * Event count.
         *
         * @return the int
         */
        @SuppressWarnings("unused")
        private int EventCount() { return eventCount; }
        
        /**
         * Elapsed time.
         *
         * @return the logical duration
         */
        @SuppressWarnings("unused")
        private LogicalDuration ElapsedTime() { return LogicalDuration.soustract(simulationDate, InitialDate); }


        /**
         * Event rate.
         *
         * @return the double
         */
        @SuppressWarnings("unused")
        private double EventRate()
        {
                double elapsed = LogicalDuration.soustract(simulationDate , InitialDate).DoubleValue();
                if (elapsed > 0)
                    return eventCount / elapsed;
                else
                    return 0;
        }


        /**
         * Pending events count.
         *
         * @return the int
         */
        @SuppressWarnings("unused")
        private int PendingEventsCount() { return allTimeEvents.size(); }

        /**
         * Allocated memory.
         *
         * @return the int
         */
        @SuppressWarnings("unused")
        private int AllocatedMemory()
        {
                int memoryLevelInReplica = (int)Runtime.getRuntime().totalMemory() - initialMemoryLevel;
                return memoryLevelInReplica;
        }

        /**
         * Elapsed real time.
         *
         * @return the double
         */
        @SuppressWarnings("unused")
        private double ElapsedRealTime()
        {
                Duration result = elapsedRealTime;
                if (IsRunning())
                {
                	result=result.plus(Duration.between(Instant.now(),lastStartDate));
                }
                return result.getSeconds();
        }


        /** The generator. */
        /// <summary>Random number generator</summary>
        private MoreRandom generator;
        
        /** The burn count. */
        @SuppressWarnings("unused")
		private int burnCount = 0;
        
        /** The use portable random generator. */
        @SuppressWarnings("unused")
		private final boolean usePortableRandomGenerator = Settings.UsePortableRandomGenerator();
        
        /** The is generator shared. */
        @SuppressWarnings("unused")
		private final boolean isGeneratorShared = !Settings.UseOneRandomGeneratorPerAgent();
        
        /** The use binary tree for event list. */
        @SuppressWarnings("unused")
        private final boolean useBinaryTreeForEventList = Settings.UseBinaryTreeForEventList();

     

        /**
         * Request all sim object.
         *
         * @return the list
         */
        public List<ISimObject> requestAllSimObject() {
        	return objectDictionary.getKnownSimObjects();
        }
        
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.IEngine#requestSimObject(enstabretagne.simulation.core.SimObjectRequest)
         */
        public List<ISimObject> requestSimObject(SimObjectRequest r){
        	return objectDictionary.requestSimObject(r);
        }
        
        
        /// <summary>
        /// current activity of the engine which constraints application permission
        /** The activity. */
        /// </summary>
        private EngineActivity activity = EngineActivity.NotInitialized;

        /// <summary>
        /// Collection of all time events, sorted by Date
        /** The all time events. */
        /// </summary>
        private SortedList<ISimEvent> allTimeEvents;


        /// <summary>
        /// Current Simulation date
        /** The simulation date. */
        /// </summary>
        private LogicalDateTime simulationDate;
		
		/** The on date changed. */
		private List<SimEngineEvent> onDateChanged;

        /// <summary>
        /// Method called at each end of quantum
        /**
         * On date changed.
         */
        /// </summary>
        private void OnDateChanged()
        {
            if (onDateChanged.size()>0)
                onDateChanged.forEach((see)->see.simEngineEvent(simulationDate));
        }

        /// <summary>
        /// Method called when a SimObject controled by this engine has an event posted
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#OnEventPosted(enstabretagne.simulation.core.ISimEvent)
         */
        /// </summary>
        public void OnEventPosted(ISimEvent ev)
        {
            if (ev.ScheduleDate().compareTo(simulationDate)<0){
                Logger.Error(this,"OnEventPosted",MessagesSimEngine.ReScheduledAtNowFrom0, ev.ScheduleDate());
                ev.resetProcessDate(simulationDate);
            }
            allTimeEvents.add(ev);
        } // OnEventPosted() 

        /// <summary>
        /// Method called when a SimObject controled by this engine has an event unposted
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#OnEventUnPosted(enstabretagne.simulation.core.ISimEvent)
         */
        /// </summary>
        public void OnEventUnPosted(ISimEvent ev)
        {
            checkWriteAllowed("UnpostStrongTimeEvent");
            allTimeEvents.remove(ev);
        } // OnEventUnPosted() 


        /// <summary>
        /// Find next time event to be processed (if any). Returns null if no more to process.
        /// </summary>
        /**
         * Find next time event.
         *
         * @return the i sim event
         */
        /// <returns></returns>
        private ISimEvent FindNextTimeEvent()
        {
        	if(allTimeEvents.size()>0)
        		return allTimeEvents.first();
        	else
        		return null;
        }


        /// <summary>
        /// Processes all events already posted at call time, at the same date than the next event
        /// </summary>
        /// <param name="upTo">Maximum search date</param>
        /**
         * Process next time events packet.
         *
         * @param upTo the up to
         * @return the int
         */
        /// <returns>The number of actually processed events</returns>
        private int processNextTimeEventsPacket(LogicalDateTime upTo)
        {
            ISimEvent firstEvent = FindNextTimeEvent();
            if (firstEvent == null) return 0;
            if (firstEvent.ScheduleDate().compareTo(upTo)>0) return 0;
            LogicalDateTime packetDate = firstEvent.ScheduleDate();

            SortedList<ISimEvent> evtsToProcess = new SortedList<ISimEvent>();

            for(ISimEvent ev :allTimeEvents)
            {
                if (ev.ScheduleDate().compareTo(packetDate)>0) break;
                evtsToProcess.add(ev);
            }

            setActivity(EngineActivity.TimeEvent);
            for (ISimEvent ev :evtsToProcess)
            {
                if (ev.ScheduleDate().compareTo(packetDate)>0) break;
                // Check whether event is still scheduled and at same date
                // (event may has been unposted or resceduled, or owner may be dead, due to previous events processing)
                if (!allTimeEvents.contains(ev) || !ev.ScheduleDate().equals(packetDate)) continue;
                //if (!ev.owner.IsActive) continue;
                // Remove the event from event list
                ev.Owner().UnPost(ev);
                // journalize event processing
                //
                // and then process event
                if (onBeforeTimeEventProcessedListeners.size()>0)
                	onBeforeTimeEventProcessedListeners.forEach((notif)->notif.notifySimEvent(ev));
                try
                {
                    ev.Process();
                }
                catch (Exception ex)
                {
                    Logger.Error(ev,"SimEngine.processNextTimeEventsPacket",MessagesSimEngine.UnhandledException0, ex);
                }
                // update event counter and notify strong event processing
                eventCount++;
                if (onTimeEventProcessed.size() > 0)
                    onTimeEventProcessed.forEach((timeEventListener)->timeEventListener.notifySimTimeEvent(simulationDate));
            }
            return evtsToProcess.size();
        }

		/**
		 * Process events until current date.
		 */
		private void processEventsUntilCurrentDate()
        {
            int count = this.processNextTimeEventsPacket(simulationDate);
            eventCount += count;
        }
		
		/* (non-Javadoc)
		 * @see enstabretagne.simulation.core.ISimEngine#activateSimulation()
		 */
		@Override
		public void activateSimulation() {
            if(currentScenario!=null)
            	currentScenario.activate();
            else{
            	Logger.Fatal(this, "activateSimulation", MessagesSimEngine.ScenarioNotSet);
            	System.exit(1);
            }
		
		}

        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#simulate()
         */
        public void simulate()
        {
            ISimEvent ev;
            LogicalDateTime upTo;
            interrupter = EngineInterruptReason.ByNone;
            //int count;
            lastStartDate = Instant.now();

            
            // process events at current date
            processEventsUntilCurrentDate();

            while (true)
            {
                // Find local target date
                ev = FindNextTimeEvent();
                if(ev!=null){
                	upTo=ev.ScheduleDate();
                }
                else {
                	upTo = LogicalDateTime.MaxValue;
                }

                // Update current date
                if (simulationDate != upTo)
                {
                	simulationDate = upTo;
                    OnDateChanged();                    
                }
                if(simulationDate == LogicalDateTime.MaxValue)
                	interrupt(EngineInterruptReason.ByMaxDate);

                // Process time events up to current date 
                processEventsUntilCurrentDate();

                // Look at any user interruption request pending from Console
                if (ShouldInterrupt())
                    interrupt(EngineInterruptReason.ByUser);

                // check if any interruption is set
                if (interrupter != EngineInterruptReason.ByNone)
                {
                    setActivity(EngineActivity.PausedWaiting);
                    break;
                }
            }
            
            // exiting engine execution
            elapsedRealTime= elapsedRealTime.plus(Duration.between(Instant.now(),lastStartDate));
            Logger.Information(this,"simulate",MessagesSimEngine.InterruptReason0, interrupter);
        }
        
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#deactivateSimulation()
         */
        @Override
        public void deactivateSimulation() {
            if(currentScenario!=null)
            	currentScenario.deactivate();
            else{
            	Logger.Fatal(this, "activateSimulation", MessagesSimEngine.ScenarioNotSet);
            	System.exit(1);
            }
        
        }
        
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#terminate(boolean)
         */
        @Override
        public void terminate(boolean restart) {
        	CleanUp(restart);    		
        
        }

        /// <summary>
        /// Should we interrupt the simulation
        /// </summary>
        /**
         * Should interrupt.
         *
         * @return true, if successful
         */
        /// <returns></returns>
        private boolean ShouldInterrupt()
        {
            //return monitor.ShouldInterrupt;
        	return false;
        }

        /// <summary>
        /// Interrupts the engine
        /// </summary>
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#interrupt(enstabretagne.simulation.core.EngineInterruptReason)
         */
        /// <param name="interrupter"></param>
        public void interrupt(EngineInterruptReason interrupter)
        {
            this.interrupter = interrupter;
        }
        

        //--------------------- Object class database ------------------------------
        //private final SimObjectType RootOfUsedObjectTypeHierarchy;
        //private final SimObjectType RootOfDerivedObjectTypeHierarchy;

        /**
         * Root of used object type hierarchy.
         *
         * @return the i sim object type
         */
        @SuppressWarnings("unused")
        private ISimObjectType RootOfUsedObjectTypeHierarchy()
        {
            return objectDictionary.getSimObjectTypeFrom(appType);
        }

        /**
         * Root of derived object type hierarchy.
         *
         * @return the i sim object type
         */
        @SuppressWarnings("unused")
       private ISimObjectType RootOfDerivedObjectTypeHierarchy()
        {
            return objectDictionary.getSimObjectTypeFrom(SimObject.class);
        }


        /**
         * Retreive sim object.
         *
         * @param liveId the live id
         * @return the i sim object
         */
        @SuppressWarnings("unused")
       private ISimObject RetreiveSimObject(int liveId)
        {
            return objectDictionary.RetreiveSimObject(liveId);
        }

        /// <summary>
        /// Write object type dictionary (for debugging or analysis purposes)
        /**
         * Write object type dictionary.
         *
         * @param Out the out
         */
        /// </summary>
        public void WriteObjectTypeDictionary(PrintWriter Out)
        {
            objectDictionary.WriteObjectTypeDictionary(Out);
        }


        /// <summary>
        /// Writes filtered pending time event list
        /// </summary>
        /// <param name="Out">Text stream to be used</param>
        /// <param name="maxCount">maximum printed time events</param>
        /**
         * Write time event list.
         *
         * @param Out the out
         * @param maxCount the max count
         * @param upTo the up to
         */
        /// <param name="upTo">maximum occurence date of printed time events</param>
        public void WriteTimeEventList(PrintWriter Out, int maxCount, LogicalDateTime upTo)
        {
            int r = 0;
            for(ISimEvent tev : allTimeEvents)
            {
                if (++r > maxCount || tev.ScheduleDate().compareTo(upTo)>0)
                    break;
                Out.println(tev.TimeEventLine(r));
            }
        }

        /// <summary>
        /// Get SimObjectType associated with Reflection type
        /// </summary>
        /// <param name="t">MetaType</param>
        /**
         * Gets the sim object type.
         *
         * @param c the c
         * @return the i sim object type
         */
        /// <returns>SimObjectType</returns>
        @SuppressWarnings("unused")
        private ISimObjectType GetSimObjectType(Class<? extends ISimObject> c)
        {
            return objectDictionary.getSimObjectTypeFrom( c);
        } 
        

        //-------------------- Object instance database ----------------------------
        /// <summary>
        /// simulated objects managed by the simulation engine.
        /** The object dictionary. */
        /// </summary>
        private ISimObjectDictionary objectDictionary = new SimObjectDictionary();
        
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.IEngine#AddSimObjectAddedListener(enstabretagne.simulation.core.notifications.SimObjectAddedListener)
         */
        public void AddSimObjectAddedListener(SimObjectAddedListener listener) {
			objectDictionary.AddSimObjectAddedListener(listener);
		}

		/* (non-Javadoc)
		 * @see enstabretagne.simulation.core.IEngine#RemoveSimObjectAddedListener(enstabretagne.simulation.core.notifications.SimObjectAddedListener)
		 */
		public void RemoveSimObjectAddedListener(SimObjectAddedListener listener) {
			objectDictionary.RemoveSimObjectAddedListener(listener);
		}

		/**
		 * Adds the sim object removed listener.
		 *
		 * @param listener the listener
		 */
		public void AddSimObjectRemovedListener(
				SimObjectRemovedListener listener) {
			objectDictionary.AddSimObjectRemovedListener(listener);
		}

		/**
		 * Removes the sim object removed listener.
		 *
		 * @param listener the listener
		 */
		public void RemoveSimObjectRemovedListener(
				SimObjectRemovedListener listener) {
			objectDictionary.RemoveSimObjectRemovedListener(listener);
		}

        /**
         * Object dictionary.
         *
         * @return the i sim object dictionary
         */
        @SuppressWarnings("unused")
		private ISimObjectDictionary ObjectDictionary()
        {
                return objectDictionary;
        }
        
        

        /// <summary>
        /// Called by SimObject when it is added to engine
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#OnSimObjectAdded(enstabretagne.simulation.core.ISimObject)
         */
        /// </summary>
        public void OnSimObjectAdded(ISimObject o)
        {
            // Add the object to the collection of simulated objects that are managed by the engine
            objectDictionary.Add(o);
        }

        /// <summary>
        /// Called by SimObject when it is removed from engine
        /* (non-Javadoc)
         * @see enstabretagne.simulation.core.ISimEngine#OnSimObjectRemoved(enstabretagne.simulation.core.ISimObject)
         */
        /// </summary>
        public void OnSimObjectRemoved(ISimObject o)
        {
            // Remove the object to the collection of simulated objects that are managed by the engine
            objectDictionary.Remove(o);
        }
       

		/* (non-Javadoc)
		 * @see enstabretagne.simulation.components.IScenarioIdProvider#getScenarioId()
		 */
		@Override
		public ScenarioId getScenarioId() {
			if(getCurrentScenario()!=null)
				return getCurrentScenario().getScenarioId();
			else
				return ScenarioId.ScenarioID_NULL;
		}


}

