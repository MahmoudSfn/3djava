/*
 * 
 */
package enstabretagne.simulation.components.implementation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.messages.MessagesEntity;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.notifications.ActivationNotification;
import enstabretagne.simulation.components.notifications.CreationNotification;
import enstabretagne.simulation.components.notifications.DeactivationNotification;
import enstabretagne.simulation.components.notifications.InitializationNotification;
import enstabretagne.simulation.components.notifications.TerminatingNotification;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.implementation.SimObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SimEntity.
 */
public abstract class SimEntity extends SimObject implements IEntity{
	static {
		OnCreated=new ArrayList<CreationNotification>();
		OnCreating=new ArrayList<CreationNotification>();
	}
	
	/** The features. */
	SimFeatures features;
	
	/**
	 * Gets the features.
	 *
	 * @return the features
	 */
	protected SimFeatures getFeatures(){
		return features;
	}
	
	/** The init parameters. */
	SimInitParameters initParameters;
	
	/**
	 * Gets the inits the parameters.
	 *
	 * @return the inits the parameters
	 */
	protected SimInitParameters getInitParameters(){
		return initParameters;
	}
	
	/**
	 * Gets the sub entities implementing.
	 *
	 * @param result the result
	 * @param i the i
	 * @return the sub entities implementing
	 */
	public void getSubEntitiesImplementing(List<SimEntity> result,Class<?> i)
	{
		for(SimEntity se : getChildren())
		{
			se.getSubEntitiesImplementing(result, i);
		}
		if(i.isAssignableFrom(this.getClass())) result.add(this);
	}
	
	/**
	 * Instantiates a new sim entity.
	 *
	 * @param name the name
	 * @param features the features
	 */
	public SimEntity(String name,SimFeatures features){

		status = EntityStatus.NONE;
		OnActivating=new ArrayList<ActivationNotification>();
		OnActivated=new ArrayList<ActivationNotification>();

		OnDeactivating=new ArrayList<DeactivationNotification>();
		OnDeactivated=new ArrayList<DeactivationNotification>();

		OnInitializing=new ArrayList<InitializationNotification>();
		OnInitialized=new ArrayList<InitializationNotification>();

		OnTerminating= new ArrayList<TerminatingNotification>();
		OnTerminated= new ArrayList<TerminatingNotification>();
		parent=null;
		children=new ArrayList<SimEntity>();
		
		this.setName(name);
		this.features=features;
		
		OnActivating.add(this::BeforeActivating);
		OnActivated.add(this::AfterActivate);
		
		OnDeactivating.add(this::BeforeDeactivating);
		OnDeactivated.add(this::AfterDeactivated);
		
		OnTerminating.add(this::BeforeTerminating);
		OnTerminated.add(this::AfterTerminated);
		
	}
	

	
	/**
	 * The Class EntityStatus.
	 */
	// / <summary>Entity life cycle status</summary>
	protected static final class EntityStatus {
		
		/**
		 * Instantiates a new entity status.
		 *
		 * @param status the status
		 */
		public EntityStatus(int status) {
			entityStatus = status;
		}

		/** The entity status. */
		private int entityStatus;

		/**
		 * Entity status.
		 *
		 * @return the int
		 */
		public int entityStatus() {
			return entityStatus;
		}

		/** The Constant NONE. */
		// /// <summary>unknownStatus</summary>
		public static final EntityStatus NONE = new EntityStatus(0x0000);
		// / <summary>created or recycled but not yet ready to enter in the
		/** The Constant BORN. */
		// simulation</summary>
		public static final EntityStatus BORN = new EntityStatus(0x0001);
		
		/** The Constant IDLE. */
		// / <summary>initialized and ready to be started</summary>
		public static final EntityStatus IDLE = new EntityStatus(0x0002);
		// / <summary>started and active as soon as its manager being
		/** The Constant ACTIVE. */
		// active</summary>
		public static final EntityStatus ACTIVE = new EntityStatus(0x0004);
		
		/** The Constant HELD. */
		public static final EntityStatus HELD = new EntityStatus(0x0008);
		// / <summary>definitively exited of the simulation ready to garbage
		/** The Constant DEAD. */
		// collection</summary>
		public static final EntityStatus DEAD = new EntityStatus(0x00032);

		/**
		 * Checks if is busy.
		 *
		 * @return true, if is busy
		 */
		public boolean isBUSY() {
			return (entityStatus == EntityStatus.ACTIVE.entityStatus())
					| (entityStatus == EntityStatus.HELD.entityStatus());
		}

		/**
		 * Checks if is alive.
		 *
		 * @return true, if is alive
		 */
		public boolean isALIVE() {
			return (entityStatus == EntityStatus.IDLE.entityStatus() | isBUSY());
		}

		/**
		 * Exist.
		 *
		 * @return true, if successful
		 */
		public boolean exist() {
			return (isALIVE() | (entityStatus == EntityStatus.BORN
					.entityStatus()));
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object arg0) {
			if (EntityStatus.class.isAssignableFrom(arg0.getClass()))
				return entityStatus == ((EntityStatus) arg0).entityStatus;
			else
				return false;
		}
	}

	/** The status. */
	private EntityStatus status;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public EntityStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param s the new status
	 */
	protected void setStatus(EntityStatus s) {
		status = s;
	}

	/**
	 * The Class EntityTransition.
	 */
	private static final class EntityTransition {
		
		/**
		 * Instantiates a new entity transition.
		 *
		 * @param transition the transition
		 */
		public EntityTransition(int transition) {
			entityTransition = transition;
		}

		/** The entity transition. */
		private int entityTransition;

        /**
         * Entity transition.
         *
         * @return the int
         */
        @SuppressWarnings("unused")
		public int entityTransition() {
			return entityTransition;
		}

		/** The Constant NONE. */
		// / <summary>No transition in progress</summary>
		public static final EntityTransition NONE = new EntityTransition(0x0000);
		
		/** The Constant CREATE. */
		// / <summary>Initialize (from NONE to BORN)</summary>
		public static final EntityTransition CREATE = new EntityTransition(0x0001);
		
		/** The Constant INIT. */
		// / <summary>Initialize (from BORN to ALIVE_IDLE)</summary>
		public static final EntityTransition INIT = new EntityTransition(0x0002);
		
		/** The Constant ACTIVATE. */
		// / <summary>Start from ALIVE_IDLE to ALIVE_BUSY)</summary>
		public static final EntityTransition ACTIVATE = new EntityTransition(
				0x0004);
		
		/** The Constant PAUSE. */
		// / <summary>Pause (from ALIVE_BUSY to ALIVE_IDLE)</summary>
        @SuppressWarnings("unused")
		public static final EntityTransition PAUSE = new EntityTransition(
				0x0008);
		
		/** The Constant DEACTIVATE. */
		// / <summary>Stop (from ALIVE to BORN)</summary>
		public static final EntityTransition DEACTIVATE = new EntityTransition(0x0016);
		
		/** The Constant TERMINATE. */
		// / <summary>CleanUp (from BORN to DEAD)</summary>
		public static final EntityTransition TERMINATE = new EntityTransition(0x0032);
	}

	/** The current transition. */
	private EntityTransition currentTransition;

	/**
	 * Gets the current transition.
	 *
	 * @return the current transition
	 */
	public EntityTransition getCurrentTransition() {
		return currentTransition;
	}

	/**
	 * Sets the current transition.
	 *
	 * @param t the new current transition
	 */
	protected void setCurrentTransition(EntityTransition t) {
		currentTransition = t;
	}

	// ----------------------------------------------------------
	// From/To| INIT | START | PAUSE | STOP | CLEAN
	// ----------------------------------------------------------
	// INIT | Error StartAt PauseAt StopAt Error
	// START | Error Warning PauseAt StopAt Error
	// PAUSE | Error StartAt Warning StopAt Error
	// STOP | Error Error Error Warning Error
	// CLEAN | Error Error Error Error Warning
	// ----------------------------------------------------------


	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#setName(java.lang.String)
	 */
	@Override
	public void setName(String value) {
		if (getStatus().equals(EntityStatus.NONE))
			super.setName(value);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#getFullName()
	 */
	@Override
	public String getFullName() {
		return super.getName();
	}
	
	/** The parent. */
	IEntity parent;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#getParent()
	 */
	public IEntity getParent(){
		return parent;
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param e the new parent
	 */
	public void setParent(IEntity e){
		parent =e;
		onParentSet();
	}
	
	/**
	 * On parent set.
	 */
	abstract public void onParentSet();
	
	/** The children. */
	List<SimEntity> children;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#getChildren()
	 */
	public List<SimEntity> getChildren(){
		return children;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#RandomGenerator()
	 */
	@Override
	public MoreRandom RandomGenerator() {
		return getEngine().getRandomGenerator();
	}

	/** The On creating. */
	static List<CreationNotification> OnCreating; 
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnCreating()
	 */
	@Override
	public List<CreationNotification> OnCreating() {
		return OnCreating;
	}

	/** The On created. */
	static List<CreationNotification> OnCreated; 
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnCreated()
	 */
	@Override
	public List<CreationNotification> OnCreated() {
		return OnCreated;
	}

	/** The On initializing. */
	private List<InitializationNotification> OnInitializing;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnInitializing()
	 */
	@Override
	public List<InitializationNotification> OnInitializing() {
		return OnInitializing;
	}

	/** The On initialized. */
	private List<InitializationNotification> OnInitialized;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnInitialized()
	 */
	@Override
	public List<InitializationNotification> OnInitialized() {
		return OnInitialized;
	}

	/** The On activating. */
	// / <summary>Event fired when the entity is beeing activated</summary>
	private List<ActivationNotification> OnActivating;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnActivating()
	 */
	@Override
	public List<ActivationNotification> OnActivating() {
		return OnActivating;
	}

	/** The On activated. */
	// / <summary>Event fired when the entity has been activated</summary>
	private List<ActivationNotification> OnActivated;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnActivated()
	 */
	@Override
	public List<ActivationNotification> OnActivated() {
		return OnActivated;

	}

	// / <summary>Event fired when the entity has completed its
	/** The On terminating. */
	// mission</summary>
	private List<TerminatingNotification> OnTerminating;
	
	/** The On terminated. */
	private List<TerminatingNotification> OnTerminated;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnTerminating()
	 */
	@Override
	public List<TerminatingNotification> OnTerminating() {
		return OnTerminating;
	}

	/** The On deactivating. */
	// / <summary> Event fired when the entity is beeing desactivated</summary>
	private List<DeactivationNotification> OnDeactivating;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnDeactivating()
	 */
	@Override
	public List<DeactivationNotification> OnDeactivating() {
		return OnDeactivating;
	}

	/** The On deactivated. */
	// / <summary>Event fired when the entity has been desactivated</summary>
	private List<DeactivationNotification> OnDeactivated;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#OnDeactivated()
	 */
	@Override
	public List<DeactivationNotification> OnDeactivated() {
		return OnDeactivated;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#IsActive()
	 */
	@Override
	public boolean IsActive() {
		return status.equals(EntityStatus.ACTIVE);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#IsAlive()
	 */
	@Override
	public boolean IsAlive() {
		return status.isALIVE();
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#setEngine(enstabretagne.simulation.core.ISimEngine)
	 */
	@Override
	public void setEngine(ISimEngine value) {
		super.setEngine(value);
		for(IEntity e:children)
			e.setEngine(value);
	}
	
	/**
	 * Creates the child.
	 *
	 * @param type the type
	 * @param name the name
	 * @param features the features
	 * @return the sim entity
	 */
	protected final SimEntity createChild(Class<? extends SimEntity> type, String name, SimFeatures features) {
	 
		
		if (OnCreating.size() > 0)
			OnCreating.forEach((creationListener) -> creationListener
					.NotifyCreation(this,name,features));
		
		SimEntity e=null;
		
		try {
			Constructor<? extends SimEntity> c = type.getConstructor(String.class,SimFeatures.class);
			e=c.newInstance(name,features);
			e.setEngine((ISimEngine)getEngine());
			
			e.setCurrentTransition(EntityTransition.CREATE);
			e.setParent(this);
			children.add(e);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			Logger.Error(this, "createChild", MessagesEntity.InstanceCreationError, ex);
			return null;
		}
		
				
		e.setCurrentTransition(EntityTransition.NONE);
		e.setStatus(EntityStatus.BORN);
		for(CreationNotification creationListener:OnCreated)
			creationListener.NotifyCreation(e,name,features);
		
		
		return e;
	}

	/**
	 * Initialize sim entity.
	 *
	 * @param init the init
	 */
	protected abstract void initializeSimEntity(SimInitParameters init);
	
	// / <summary>
	// / Activation of the object (pending events in past are fired)
	/**
	 * Initialize.
	 *
	 * @param init the init
	 */
	// / </summary>
	public final void initialize(SimInitParameters init) {
		if(getStatus().equals(EntityStatus.IDLE)) return;
		
		if (!getStatus().equals(EntityStatus.BORN))
		{
			Logger.Error(this, "initialize", MessagesEntity.InitializeWhenNotBORN);
			return;
		}

		// Activation might be differed (if engine is not yet known)
		if (getEngine() == null)
			return;

		setCurrentTransition(EntityTransition.INIT);
		if (OnInitializing.size() > 0)
			OnInitializing.forEach((initializationListener) -> initializationListener
					.NotifyInitialization(this, init));
		initParameters = init;
		initializeSimEntity(init);
		
		setCurrentTransition(EntityTransition.NONE);
		setStatus(EntityStatus.IDLE);
		if (OnInitialized.size() > 0)
			OnInitialized.forEach((initializationListener) -> initializationListener
					.NotifyInitialization(this, init));
	}

	// / <summary>
	// / Activation of the object (pending events in past are fired)
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#activate()
	 */
	// / </summary>
	@Override
	public final void activate() {
		if(getStatus().equals(EntityStatus.ACTIVE)) return;
		
		if (!getStatus().equals(EntityStatus.IDLE))
		{
			Logger.Error(this, "activate", MessagesEntity.ActivateWhenNotIDLE);
			return;
		}

		// Activation might be differed (if engine is not yet known)
		if (getEngine() == null)
			return;

		setCurrentTransition(EntityTransition.ACTIVATE);
		if (OnActivating.size() > 0)
			OnActivating.forEach((activationListener) -> activationListener
					.NotifyActivation(this, true));
		
		super.activate();
		
		setCurrentTransition(EntityTransition.NONE);
		setStatus(EntityStatus.ACTIVE);
		if (OnActivated.size() > 0)
			OnActivated.forEach((activationListener) -> activationListener
					.NotifyActivation(this, true));
	}
	
	/**
	 * Before activating.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	protected abstract void BeforeActivating(IEntity sender, boolean starting) ;
	
	/**
	 * After activate.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	protected abstract void AfterActivate(IEntity sender, boolean starting) ;

	// / <summary>
	// / Desactivation of the object
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#deactivate()
	 */
	// / </summary>
	@Override
	public final void deactivate() {
		
		if (getStatus().equals(EntityStatus.BORN)) return;

		if (!IsAlive())
		{
			IsAlive();
			Logger.Error(this, "deactivate", MessagesEntity.DeactivateWhenNotAlive);
			return;
		}

		// deactivation might be ignored
		if (getEngine() == null)
			return;

		setCurrentTransition(EntityTransition.DEACTIVATE);
		if (OnDeactivating.size() > 0)
			OnDeactivating.forEach((activationListener) -> activationListener
					.NotifyDeactivation(this, true));
		
		UnPostAllEvents();
		super.deactivate();

		setCurrentTransition(EntityTransition.NONE);
		setStatus(EntityStatus.BORN);
		if (OnDeactivated.size() > 0)
			OnDeactivated.forEach((activationListener) -> activationListener
					.NotifyDeactivation(this, true));
	}

	/**
	 * Before deactivating.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	protected abstract void BeforeDeactivating(IEntity sender, boolean starting) ;
	
	/**
	 * After deactivated.
	 *
	 * @param sender the sender
	 * @param starting the starting
	 */
	protected abstract void AfterDeactivated(IEntity sender, boolean starting) ;
	
	/**
	 * Before terminating.
	 *
	 * @param sender the sender
	 * @param restart the restart
	 */
	protected  void BeforeTerminating(IEntity sender,boolean restart) {
		for(SimEntity e:children)
		{
			e.BeforeTerminating(this,restart);			
		}
		parent=null;
		children.clear();
	}
	
	/**
	 * After terminated.
	 *
	 * @param sender the sender
	 * @param restart the restart
	 */
	protected abstract void AfterTerminated(IEntity sender,boolean restart);
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimObject#terminate(boolean)
	 */
	@Override
	public final void terminate(boolean restart) {
		if (getStatus().equals(EntityStatus.DEAD)) return;
        if (!getStatus().equals(EntityStatus.BORN))
        {
			Logger.Error(this, "terminate", MessagesEntity.TerminateWhenNotBorn);
			return;
        }

		setCurrentTransition(EntityTransition.TERMINATE);
        if (OnTerminating.size()>0)
            OnTerminating.forEach((terminationListener)->terminationListener.NotifyTerminating(this,restart));

        super.terminate(restart);
		setCurrentTransition(EntityTransition.NONE);

        if (OnTerminated.size()>0)
            OnTerminated.forEach((terminationListener)->terminationListener.NotifyTerminating(this,restart));

		if(restart)
			setStatus(EntityStatus.BORN);
		else
			setStatus(EntityStatus.DEAD);
		
	}
}
