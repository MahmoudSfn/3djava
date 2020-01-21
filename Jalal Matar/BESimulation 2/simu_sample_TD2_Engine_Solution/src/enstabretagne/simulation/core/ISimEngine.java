/*
 * 
 */
package enstabretagne.simulation.core;

import enstabretagne.simulation.components.IScenarioIdProvider;
import enstabretagne.simulation.components.data.SimScenarioInit;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISimEngine. Cette interface est plutôt pour utilisation du framework de simulation.
 * Elle étend l'interface IEngine. 
 */
public interface ISimEngine extends IEngine,ISimulationDateProvider,IScenarioIdProvider{



	/**
	 * Cette méthode permet simplement au moteur de supprimer l'objet de sa liste d'objets sous sa gestion.
	 * Sans doute à renommer...
	 *
	 * @param simObject the sim object
	 */
	void OnSimObjectRemoved(ISimObject simObject);
	
	/**
	 * Cette méthode permet simplement au moteur d'ajouter l'objet dans sa liste d'objet sous sa gestion.
	 * Sans doute à renommer...
	 *
	 * @param simObject the sim object
	 */
	void OnSimObjectAdded(ISimObject simObject);
	
	/**
	 * On event posted.
	 *
	 * @param ev the ev
	 */
	void OnEventPosted(ISimEvent ev);
	
	/**
	 * On event un posted.
	 *
	 * @param ev the ev
	 */
	void OnEventUnPosted(ISimEvent ev);
	
	/**
	 * Cette fonction va initialiser la simulation, provoquer l’instanciation des différentes entités puis leur initialisation.
	 *
	 * à améliorer...
	 * 
	 * @param currentScenario the current scenario
	 * @param initScenario the init scenario
	 */
	void init(IScenario currentScenario,SimScenarioInit initScenario);
	
	/**
	 * Après l’initialisation il s’agit d’activer la simulation en propageant l’activation à toutes les entités de la simulation
	 */
	void activateSimulation();
	
	/**
	 * Cette fonction permet à un objet extérieur de jalonner des interruptions du moteur.
	 *
	 * @param bydate the bydate
	 */
	void interrupt(EngineInterruptReason bydate);
	
	/**
	 * La fonction ci-dessous est le cœur du moteur. 
	 * Elle va boucler sur les événements postés. C’est par l’activation provoquée de chaque entité que événements initiaux vont être créés. Et ce sont ces événements qui produisent une réaction en chaine jusqu’à épuisement des 
	 * événements ou l’atteinte de la fin de la durée demandée de la simulation...
	 * 
	 */
	void simulate();
	
	/**
	 * Deactivate simulation.
	 */
	void deactivateSimulation();	
	
	/**
	 * Terminate.
	 *
	 * @param restart the restart
	 */
	void terminate(boolean restart);

}
