/*
 * 
 */
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

// TODO: Auto-generated Javadoc
/**
 * Cette interface caractérise l'objet de base manipulé par le moteur.
 * Elle contient les fonctions essentielles pour poster/déposter des événements
 * La seule fonction liée à un cycle de vie est Terminate().<br>
 * Les autres fonctions liées au cycle de vie d'une entité sont portées par {@link IEntity}
 * 
 * La fonction <code>getName()</code> est essentielle. Elle donne l'élément qui identifie l'objet sans ambiguité.
 */
public interface ISimObject {
	
	/**
	 * Current date.
	 *
	 * @return the logical date time
	 */
	LogicalDateTime CurrentDate();
	
	/**
	 * Cette fonction permet à un objet de demander l'arrêt de la simulation.
	 * Fonction plutôt à usage du framework plus que des applications de simulation.
	 */
	void interruptEngineByDate();
	
	/**
	 * Gets the sim obj ID.
	 *
	 * @return the sim obj ID
	 */
	int getSimObjID();
	
	/**
	 * Post.
	 *
	 * @param ev the ev
	 */
	public void Post (ISimEvent ev);
	
	/**
	 * Post.
	 *
	 * @param ev the ev
	 * @param t the t
	 */
	public void Post (ISimEvent ev,LogicalDateTime t);
	
	/**
	 * Post.
	 *
	 * @param ev the ev
	 * @param dt the dt
	 */
	public void Post (ISimEvent ev,LogicalDuration dt);
	
	/**
	 * Un post.
	 *
	 * @param ev the ev
	 */
	public void UnPost(ISimEvent ev);
	
	/**
	 * Un post all events.
	 */
	public void UnPostAllEvents();
	
	/**
	 * Terminate.
	 *
	 * @param restart the restart
	 */
	public void terminate(boolean restart);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
}
