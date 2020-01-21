/**
* Classe IScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.ISimEntity;
import enstabretagne.simulation.components.ScenarioId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IScenario.
 */
public interface IScenario extends ISimEntity{

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#setEngine(enstabretagne.simulation.core.ISimEngine)
	 */	
	void setEngine(ISimEngine simEngine);

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.components.IEntity#getName()
	 */
	String getName();
	
	/**
	 * Gets the seed.
	 *
	 * @return the seed
	 */
	long getSeed();
	
	/**
	 * Gets the scenario id.
	 *
	 * @return the scenario id
	 */
	ScenarioId getScenarioId();

	/**
	 * Gets the start date time.
	 *
	 * @return the start date time
	 */
	LogicalDateTime getStartDateTime();
	
	/**
	 * Gets the end date time.
	 *
	 * @return the end date time
	 */
	LogicalDateTime getEndDateTime();

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObject#terminate(boolean)
	 */
	void terminate(boolean restart);
	
	/**
	 * Cette fonction est essentielle pour le moniteur de simulation temps réel.
	 * Elle permet de jalonner les pauses du moteur au rythme du temps réel jusqu'à l'événement de fin terminal
	 *
	 * @param duration the duration
	 */
	void InterruptIn(LogicalDuration duration);
	
	/**
	 * Cette fonction est essentielle pour poster les arrêts du moteur à la fin du scénario.
	 *
	 * @param date the date
	 */
	void InterruptAt(LogicalDateTime date);
	
}

