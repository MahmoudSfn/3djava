/*
 * 
 */
package enstabretagne.monitor.implementation;

import enstabretagne.base.logger.Logger;
import enstabretagne.monitor.IMonitor;
import enstabretagne.simulation.components.data.SimScenarioInit;

// TODO: Auto-generated Javadoc
/**
 * The Class UniversalMonitor.
 */
public class  UniversalMonitor extends AbstractMonitor implements IMonitor {


/**
 * Instantiates a new universal monitor.
 */
public UniversalMonitor()
{
	super();

}



/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#exit()
 */
@Override
	public void exit() {
		Logger.Terminate();
	}


/** The rate logical time over real time. */
private double rateLogicalTimeOverRealTime;

/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#getLogicalTimeSpeed()
 */
@Override
	public double getLogicalTimeSpeed() {
		return rateLogicalTimeOverRealTime;
	}

/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#setLogicalTimeSpeed(double)
 */
@Override
public void setLogicalTimeSpeed(double rateLogicalTimeOverRealTime) {
	if(rateLogicalTimeOverRealTime == Double.MAX_VALUE){
		this.rateLogicalTimeOverRealTime = rateLogicalTimeOverRealTime;
	}
}



/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#run(boolean)
 */
@Override
public void run(boolean restart) {
	if(getCurrentScenario()!=null)
	{
		engine.init(getCurrentScenario(), new SimScenarioInit(getCurrentSeed(),currentRepliqueNumber));

		engine.activateSimulation();
		engine.simulate();				
		engine.deactivateSimulation();
		engine.terminate(restart);			
	}
	
}



/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#runPlan()
 */
@Override
public void runPlan() {
	
	while(selectNextScenario()!=null) {
		for(currentRepliqueNumber=0;currentRepliqueNumber<getXpPlan().getNombreRepliques();currentRepliqueNumber++) {
			
			setCurrentSeed(getXpPlan().getInitialSeed()+currentRepliqueNumber);//on prend le numéro de réplique comme germe initial de la réplique
			Logger.Detail(null, "runPlan", "run");
			run(true);
			
		}
	}
	
}



/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#pause()
 */
@Override
public void pause() {
	// TODO Auto-generated method stub
	
}



/* (non-Javadoc)
 * @see enstabretagne.monitor.IMonitor#stop()
 */
@Override
public void stop() {
	// TODO Auto-generated method stub
	
}
}
