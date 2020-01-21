/**
* Classe SimObjectType.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.messages.MessagesSimEngine;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.ISimObjectType;

// TODO: Auto-generated Javadoc
/**
 * The Class SimObjectType.
 */
public class SimObjectType implements ISimObjectType {

	/** The c. */
	Class<? extends ISimObject> c;
	
	/** The object instances. */
	List<ISimObject> objectInstances;
	
	
	/**
	 * Instantiates a new sim object type.
	 *
	 * @param c the c
	 */
	public SimObjectType(Class<? extends ISimObject> c) {
		this.c=c;	
		objectInstances = new ArrayList<ISimObject>();
	}
	
	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectType#terminate(boolean)
	 */
	public int terminate(boolean restart)
	{
		if (objectInstances == null)
			return 0;
	  int n = objectInstances.size();
	  if (n == 0)
	    return 0;
	
	  for(ISimObject o : objectInstances) {
	    Logger.Warning(this,"terminate",MessagesSimEngine.Finalizing, MessagesSimEngine.ZombiObjectFrom0, o.getClass().getName());
	    o.terminate(restart);
	  }
	  objectInstances.clear();;
	  return n;
	
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimObjectType#getObjectInstances()
	 */
	@Override
	public List<ISimObject> getObjectInstances() {
		return objectInstances;
	}
}

