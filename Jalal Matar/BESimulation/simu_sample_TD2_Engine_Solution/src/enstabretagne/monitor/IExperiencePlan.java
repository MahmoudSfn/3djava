/*
 * 
 */
package enstabretagne.monitor;

import java.util.List;

import enstabretagne.simulation.core.IScenario;

// TODO: Auto-generated Javadoc
/**
 * The Interface IExperiencePlan.
 */
public interface IExperiencePlan {
	
	/**
	 * Gets the nombre repliques.
	 *
	 * @return the nombre repliques
	 */
	long getNombreRepliques();
	
	/**
	 * Gets the liste scenarios.
	 *
	 * @return the liste scenarios
	 */
	List<IScenario> getListeScenarios();
	
	/**
	 * Gets the initial seed.
	 *
	 * @return the initial seed
	 */
	long getInitialSeed();
	
}
