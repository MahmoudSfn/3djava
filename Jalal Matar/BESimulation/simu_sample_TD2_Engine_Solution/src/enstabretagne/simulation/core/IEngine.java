/*
 * 
 */
package enstabretagne.simulation.core;

import java.util.List;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.notifications.SimObjectAddedListener;

// TODO: Auto-generated Javadoc
/**
 * The Interface IEngine. Cette interface est à usage des applications. 
 * Elle contient les services essentiels dont peut avoir besoin une entité  
 */
public interface IEngine {
	
	/**
	 * Permet de s'abonner à la création d'un nouvel SimObject<br>
	 * <b>ATTENTION: l'objet renvoyé est "brut" à l'état BORN.</b>
	 * Ne pas utiliser l'objet "as is". Attendre son passage en état Activated.
	 * On peut utiliser à cet effet {@link IEntity#OnActivated()} pour s'abonner ensuite à l'activation 
	 *	
	 *Noter que {@link SimObjectAddedListener} est une interface fonctionnelle.<br>
	 *Peut s'utiliser ainsi: <code>AddSimObjectAddedListener(this::monlistener);</code> où "<code>monlistener</code>" est une méthode implémentant l'interface SimObjectAddedListener
	 *
	 * @param listener Noter que {@link SimObjectAddedListener} est une interface fonctionnelle.<br>
	 *Peut s'utiliser ainsi: <code>AddSimObjectAddedListener(this::monlistener);</c
	 */
	void AddSimObjectAddedListener(SimObjectAddedListener listener);
	
	/**
	 * Permet de se désabonner de l'événement de création d'un nouveau SimObject
	 *
	 * @param listener the listener
	 */
	void RemoveSimObjectAddedListener(SimObjectAddedListener listener);
	
	/**
	 * Permet de requêter un objet connu du moteur.
	 * 
	 *
	 * @param objectToBeFound est une fonction dont la signature correspond à l'interface fonctionnelle SimObjectRequest
	 * @return the list
	 */
	List<ISimObject> requestSimObject(SimObjectRequest objectToBeFound);
	
	/**
	 * Gets the random generator.
	 *
	 * @return the random generator
	 */
	MoreRandom getRandomGenerator();
	
	/**
	 * Simulation date.
	 *
	 * @return the logical date time
	 */
	LogicalDateTime SimulationDate();	


}
