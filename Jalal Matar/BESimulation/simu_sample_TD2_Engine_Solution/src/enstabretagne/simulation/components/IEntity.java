/**
* Classe IEntity.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.components;
import java.util.List;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.components.notifications.ActivationNotification;
import enstabretagne.simulation.components.notifications.CreationNotification;
import enstabretagne.simulation.components.notifications.DeactivationNotification;
import enstabretagne.simulation.components.notifications.InitializationNotification;
import enstabretagne.simulation.components.notifications.TerminatingNotification;
import enstabretagne.simulation.core.ISimEngine;

// TODO: Auto-generated Javadoc
/**
 * Cette interface complète l'interface {@link ISimObject} des fonctions donnant un cycle de vie à l'entité.
 * Elle rajoute de plus la gestion de la filiation d'une entité.
 */
public interface IEntity extends enstabretagne.simulation.core.ISimObject{

		/**
		 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage en phase de création.
		 *
		 * @return the list
		 */
	    List<CreationNotification> OnCreating();

		/**
		 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage à l'état créé.
		 *
		 * @return the list
		 */
	    List<CreationNotification> OnCreated();

	    /**
    	 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage en phase d'initialisation.
    	 *
    	 * @return the list
    	 */
	    List<InitializationNotification> OnInitializing();

		/**
		 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage en phase initialisée.
		 *
		 * @return the list
		 */
	    List<InitializationNotification> OnInitialized();

	    /**
    	 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage en phase d'activation.
    	 *
    	 * @return the list
    	 */
    	List<ActivationNotification> OnActivating();

	    /**
    	 * Liste de callbacks permettant à l'entité de prévenir de 
		 * son passage en phase activé.
    	 *
    	 * @return the list
    	 */
	    List<ActivationNotification> OnActivated();

	    /**
    	 * Liste de callbacks permettant à l'entité de prévenir de 
		 * avant de rentrer dans le processus de terminaison.
    	 *
    	 * @return the list
    	 */
	    List<TerminatingNotification> OnTerminating();

	    /**
    	 * Liste de callbacks permettant à l'entité de prévenir de 
		 * avant de rentrer dans le processus de désactivation.
    	 *
    	 * @return the list
    	 */
	    List<DeactivationNotification> OnDeactivating();

	    /**
    	 *Liste de callbacks permettant à l'entité de prévenir de 
		 * qu'elle est désactivée.
    	 *
    	 * @return the list
    	 */
	    List<DeactivationNotification> OnDeactivated();

	    /**
    	 * Checks if is alive.
    	 *
    	 * @return true, if successful
    	 */
	    boolean IsAlive();

	    /**
    	 * Checks if is active.
    	 *
    	 * @return true, if successful
    	 */
	    boolean IsActive ();


	    /**
    	 * Gets the full name.
    	 *
    	 * @return the full name
    	 */
	    String getFullName();

	    /**
    	 * Permet de récupérer le générateur de nombres aléatoires.
    	 *
    	 * @return the more random
    	 */
	    MoreRandom RandomGenerator();
	    
	    /**
    	 * Cette fonction permet d'activer l'entité. 
    	 * Il est de la responsabilité de l'entité parente d'activer ses filles.
    	 */
    	void activate();
	    
    	/**
    	 * Cette fonction permet d'activer l'entité. 
    	 * Il est de la responsabilité de l'entité parente de désactiver ses filles.
    	 */
    	void deactivate();
	    
    	/**
    	 * Gets the children.
    	 *
    	 * @return the children
    	 */
    	List<SimEntity> getChildren() ;
	    
    	/**
    	 * Gets the parent.
    	 * renvoie Null le cas où il n'y a pas de parents.
    	 *
    	 * @return the parent
    	 */
    	IEntity getParent() ;

		/**
		 * Sets the engine.
		 * Cette méthode n'est pas à utiliser en temps normal.
		 * Plutôt à usage du framework de simulation plus que des applications de simulation
		 *
		 * @param value the new engine
		 */
		void setEngine(ISimEngine value);

}

