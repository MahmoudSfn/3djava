/**
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.monitor;

import enstabretagne.simulation.core.IScenario;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMonitor.
 */
public interface IMonitor {

	/**
	 * Load experience plan from settings.
	 *
	 */
	void loadExperiencePlanFromSettings();	

	/**
	 * Load existing experience plan.
	 *
	 * @param experiencePlan the experience plan
	 */
	void loadExperiencePlan(IExperiencePlan experiencePlan);	

	/**
	 * Select next scenario.
	 *
	 * @return the i scenario
	 */
	IScenario selectNextScenario();
	
	/**
	 * Select scenario.
	 *
	 * @param sc the sc
	 */
	void selectScenario(IScenario sc);
	
	/**
	 * Gets the logical time speed.
	 *
	 * @return the logical time speed
	 */
	double getLogicalTimeSpeed();
	
	/**
	 * Sets the logical time speed.
	 *
	 * @param rateLogicalTimeOverRealTime the new logical time speed
	 */
	void setLogicalTimeSpeed(double rateLogicalTimeOverRealTime);
	
	/**
	 * Run.
	 *
	 * @param restart the restart
	 */
	void run(boolean restart);
	
	/**
	 * Run plan.
	 */
	void runPlan();
	
	/**
	 * Pause.
	 */
	void pause();
	
	/**
	 * Stop.
	 */
	void stop();
	
	/**
	 * Exit.
	 */
	void exit();
	
}

