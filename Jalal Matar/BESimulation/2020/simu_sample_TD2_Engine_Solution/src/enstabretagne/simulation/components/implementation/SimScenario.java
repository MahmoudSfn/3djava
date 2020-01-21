/**
* Classe SimScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components.implementation;

import enstabretagne.base.logger.IRecordable;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.IScenarioIdProvider;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.data.SimScenarioInit;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.implementation.EndSimulationEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SimScenario.
 */
public class SimScenario extends SimEntity implements IScenario,IScenarioIdProvider,IRecordable{
	
	/** The start date time. */
	LogicalDateTime startDateTime;
	
	/** The end date time. */
	LogicalDateTime endDateTime;
	
	/** The seed. */
	long seed;
	
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.IScenario#getSeed()
	 */
	public long getSeed() {
		return seed;
	}


	/** The entity to follow. */
	SimEntity entityToFollow;
	
	/**
	 * Gets the entity to follow.
	 *
	 * @return the entity to follow
	 */
	public SimEntity getEntityToFollow() {
		return entityToFollow;
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.IScenario#getStartDateTime()
	 */
	public LogicalDateTime getStartDateTime() {
		return startDateTime;
	}


	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.IScenario#getEndDateTime()
	 */
	public LogicalDateTime getEndDateTime() {
		return endDateTime;
	}


	/** The scenario id. */
	private ScenarioId scenarioId;
	
	/**
	 * Instantiates a new sim scenario.
	 *
	 * @param id the id
	 * @param features the features
	 * @param start the start
	 * @param end the end
	 */
	public SimScenario(ScenarioId id, SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(id.getScenarioId(), features);
		scenarioId=id;
		setStatus(EntityStatus.BORN);
		startDateTime=start;
		endDateTime=end;
		InterruptAt(end);
	}



	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#initializeSimEntity(enstabretagne.simulation.components.data.SimInitParameters)
	 */
	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		seed = ((SimScenarioInit) init).getSeed();
		InterruptAt(this.endDateTime);
		getScenarioId().setRepliqueNumber(((SimScenarioInit) init).getRepliqueNum());

	}

	/**
	 * Interrupt at.
	 *
	 * @param date the date
	 */
	public void InterruptAt(LogicalDateTime date){
		Post(new EndSimulationEvent(),date);
	}

	/**
	 * Interrupt in.
	 *
	 * @param duration the duration
	 */
	public void InterruptIn(LogicalDuration duration){
		Post(new EndSimulationEvent(),duration);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterActivate(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Data(this);
		for(IEntity e:children)
			((SimEntity) e).activate();
			
	}

	

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#onParentSet()
	 */
	@Override
	public void onParentSet() {
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.IScenario#getScenarioId()
	 */
	@Override
	public ScenarioId getScenarioId() {
		return scenarioId;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.base.logger.IRecordable#getTitles()
	 */
	@Override
	public String[] getTitles() {
		String[] titles={"Nom Scenario","Numéro réplique","Germe"};

		return titles;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.base.logger.IRecordable#getRecords()
	 */
	@Override
	public String[] getRecords() {
		String[] rec;
			rec=new String[]{getScenarioId().getScenarioId(),Long.toString(getScenarioId().getRepliqueNumber()),Long.toString(getSeed())};

		return rec;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.base.logger.IRecordable#getClassement()
	 */
	@Override
	public String getClassement() {
		return "Scenario";
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#BeforeDeactivating(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		for(IEntity e:children)
			((SimEntity) e).deactivate();		
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterTerminated(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		if(restart)
		{
			reinitSimObject();
		}
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#BeforeActivating(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.implementation.SimEntity#AfterDeactivated(enstabretagne.simulation.components.IEntity, boolean)
	 */
	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}	

}

