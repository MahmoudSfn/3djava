/**
* Classe EndSimulationEvent.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core.implementation;

import enstabretagne.base.time.LogicalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class EndSimulationEvent.
 */
public class EndSimulationEvent extends SimEvent {

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEvent#Process()
	 */
	@Override
	public void Process() {
		Owner().interruptEngineByDate();
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.implementation.SimEvent#resetProcessDate(enstabretagne.base.time.LogicalDateTime)
	 */
	@Override
	public void resetProcessDate(LogicalDateTime simulationDate) {
		// TODO Auto-generated method stub
		
	}

}

