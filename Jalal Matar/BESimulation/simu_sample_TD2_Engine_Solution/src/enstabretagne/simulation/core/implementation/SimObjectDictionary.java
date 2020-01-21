/**
* Classe SimObjectDictionary.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.messages.MessagesDictionnary;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.ISimObjectDictionary;
import enstabretagne.simulation.core.ISimObjectType;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.simulation.core.notifications.SimObjectAddedListener;
import enstabretagne.simulation.core.notifications.SimObjectRemovedListener;

// TODO: Auto-generated Javadoc
/**
 * The Class SimObjectDictionary.
 */
public class SimObjectDictionary implements ISimObjectDictionary{

	/** The object types dictionnary. */
	HashMap<Class<?>, ISimObjectType> objectTypesDictionnary;
	
	/** The sim object added listener. */
	List<SimObjectAddedListener> simObjectAddedListener; 
	
	/** The sim object removed listener. */
	List<SimObjectRemovedListener> simObjectRemovedListener; 
	
	/** The engine. */
	ISimEngine engine;
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#Add(enstabretagne.simulation.core.ISimObject)
	 */
	public void Add(ISimObject o) {
		
		Class<?> oc = o.getClass();
		if(SimObject.class.isAssignableFrom(oc))
		{
			
			SimObjectType simObjectType;
			if(!objectTypesDictionnary.containsKey(o.getClass())) {
				simObjectType = new SimObjectType(o.getClass());
			
				objectTypesDictionnary.put(oc, simObjectType);
			      Logger.Information(this, "Add", MessagesDictionnary.NewClassObjectAdded, oc.getName());
			}
			else
				simObjectType= (SimObjectType) objectTypesDictionnary.get(o.getClass());
		      
			simObjectType.objectInstances.add(o);
		    simObjectAddedListener.forEach(l -> l.SimObjectAdded(o));  
		      Logger.Information(this, "Add", MessagesDictionnary.NewObjectAdded, o.getName());
			
		}
		
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#Remove(enstabretagne.simulation.core.ISimObject)
	 */
	public void Remove(ISimObject o) {
		
		SimObjectType simObjectType;
		simObjectType= (SimObjectType) objectTypesDictionnary.get(o.getClass());
		if(simObjectType!=null) {
	      simObjectType.objectInstances.remove(o);
		    simObjectRemovedListener.forEach(l -> l.SimObjectRemoved(o));  
		}
		else
			Logger.Error(this, "Remove", MessagesDictionnary.ObjectTypeNotReferenced, o.getClass().getName());
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#getKnownSimObjects()
	 */
	public List<ISimObject> getKnownSimObjects() {
    	List<ISimObject> requestedObjects = new ArrayList<ISimObject>();    	
    	for(ISimObjectType ts:objectTypesDictionnary.values()){
    		for(ISimObject o:ts.getObjectInstances())
    		{
    			requestedObjects.add(o);
    		}
		}
    	return requestedObjects;
   	}
		
    /* (non-Javadoc)
     * @see enstabretagne.simulation.core.ISimObjectDictionary#requestSimObject(enstabretagne.simulation.core.SimObjectRequest)
     */
    public List<ISimObject> requestSimObject(SimObjectRequest r){
    	List<ISimObject> requestedObjects = new ArrayList<ISimObject>();    	
    	for(ISimObjectType ts:objectTypesDictionnary.values())
    		for(ISimObject o:ts.getObjectInstances())
    		{
    			if(r.filter(o))
    				requestedObjects.add(o);
    		}
    	return requestedObjects;
    }
    
	

    
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#getSimObjectTypeFrom(java.lang.Class)
	 */
	public ISimObjectType getSimObjectTypeFrom(Class<? extends ISimObject> c) {
		
		return objectTypesDictionnary.get(c);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#RetreiveSimObject(int)
	 */
	public ISimObject RetreiveSimObject(int liveId) {
		for(ISimObjectType sot : objectTypesDictionnary.values())
		{
			for(ISimObject so : sot.getObjectInstances()){
				if(so.getSimObjID()==liveId) return so;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#terminate(boolean)
	 */
	public int terminate(boolean restart) {
		int n = 0;
	      if (objectTypesDictionnary == null)
	        return 0;

	      for(ISimObjectType ot : objectTypesDictionnary.values())
	        n += ot.terminate(restart);
	      return n;
		
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#initialize(enstabretagne.simulation.core.ISimEngine)
	 */
	public void initialize(ISimEngine simEngine) {
		objectTypesDictionnary = new HashMap<>();
		engine = simEngine;
		simObjectAddedListener=new ArrayList<SimObjectAddedListener>(); 
		simObjectRemovedListener=new ArrayList<SimObjectRemovedListener>(); 
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#AddSimObjectAddedListener(enstabretagne.simulation.core.notifications.SimObjectAddedListener)
	 */
	public void AddSimObjectAddedListener(SimObjectAddedListener listener){
		simObjectAddedListener.add(listener);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#RemoveSimObjectAddedListener(enstabretagne.simulation.core.notifications.SimObjectAddedListener)
	 */
	public void RemoveSimObjectAddedListener(SimObjectAddedListener listener){
		simObjectAddedListener.remove(listener);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#AddSimObjectRemovedListener(enstabretagne.simulation.core.notifications.SimObjectRemovedListener)
	 */
	public void AddSimObjectRemovedListener(SimObjectRemovedListener listener){
		simObjectRemovedListener.add(listener);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#RemoveSimObjectRemovedListener(enstabretagne.simulation.core.notifications.SimObjectRemovedListener)
	 */
	public void RemoveSimObjectRemovedListener(SimObjectRemovedListener listener){
		simObjectRemovedListener.remove(listener);
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectDictionary#WriteObjectTypeDictionary(java.io.PrintWriter)
	 */
	public void WriteObjectTypeDictionary(PrintWriter out) {
		
		
	}


}

