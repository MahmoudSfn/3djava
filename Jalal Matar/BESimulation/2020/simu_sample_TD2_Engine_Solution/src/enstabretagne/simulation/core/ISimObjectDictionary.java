/*
 * 
 */
package enstabretagne.simulation.core;

import java.io.PrintWriter;
import java.util.List;

import enstabretagne.simulation.core.notifications.SimObjectAddedListener;
import enstabretagne.simulation.core.notifications.SimObjectRemovedListener;


// TODO: Auto-generated Javadoc
/**
 * The Interface ISimObjectDictionary.
 */
public interface ISimObjectDictionary {

	/**
	 * Adds the sim object added listener.
	 *
	 * @param listener the listener
	 */
	void AddSimObjectAddedListener(SimObjectAddedListener listener);
	
	/**
	 * Removes the sim object added listener.
	 *
	 * @param listener the listener
	 */
	void RemoveSimObjectAddedListener(SimObjectAddedListener listener);
	
	/**
	 * Adds the sim object removed listener.
	 *
	 * @param listener the listener
	 */
	void AddSimObjectRemovedListener(SimObjectRemovedListener listener);
	
	/**
	 * Removes the sim object removed listener.
	 *
	 * @param listener the listener
	 */
	void RemoveSimObjectRemovedListener(SimObjectRemovedListener listener);

	/**
	 * Adds the.
	 *
	 * @param o the o
	 */
	void Add(ISimObject o);
	
	/**
	 * Removes the.
	 *
	 * @param o the o
	 */
	void Remove(ISimObject o);
	
	/**
	 * Initialize.
	 *
	 * @param simEngine the sim engine
	 */
	void initialize(ISimEngine simEngine);
	
	/**
	 * Gets the known sim objects.
	 *
	 * @return the known sim objects
	 */
	List<ISimObject> getKnownSimObjects();

	/**
	 * Terminate.
	 *
	 * @param restart the restart
	 * @return the int
	 */
	int terminate(boolean restart);
	
	/**
	 * Request sim object.
	 *
	 * @param r the r
	 * @return the list
	 */
	List<ISimObject> requestSimObject(SimObjectRequest r);
	
	/**
	 * Gets the sim object type from.
	 *
	 * @param appType the app type
	 * @return the sim object type from
	 */
	ISimObjectType getSimObjectTypeFrom(Class<? extends ISimObject> appType);
	
	/**
	 * Write object type dictionary.
	 *
	 * @param out the out
	 */
	void WriteObjectTypeDictionary(PrintWriter out);
	
	/**
	 * Retreive sim object.
	 *
	 * @param liveId the live id
	 * @return the i sim object
	 */
	ISimObject RetreiveSimObject(int liveId);

}
