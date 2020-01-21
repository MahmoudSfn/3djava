/*
 * 
 */
package enstabretagne.monitor.implementation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.monitor.IExperiencePlan;
import enstabretagne.monitor.IMonitor;
import enstabretagne.simulation.components.implementation.ScenariiSettings;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.components.messages.ScenariiMessages;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import enstabretagne.simulation.core.ISimEngine;
import enstabretagne.simulation.core.implementation.SimEngine;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMonitor.
 */
public abstract class AbstractMonitor implements IMonitor {

	/** The xp plan. */
	private ExperiencePlan xpPlan;

	/**
	 * Gets the xp plan.
	 *
	 * @return the xp plan
	 */
	public ExperiencePlan getXpPlan() {
		return xpPlan;
	}

	/** The current scenario. */
	private SimScenario currentScenario;

	/** The scenario index. */
	private int scenarioIndex;

	/** The current seed. */
	private long currentSeed;

	/** The current replique number. */
	protected long currentRepliqueNumber;

	/**
	 * Gets the current replique number.
	 *
	 * @return the current replique number
	 */
	public long getCurrentRepliqueNumber() {
		return currentRepliqueNumber;
	}

	/**
	 * Gets the current seed.
	 *
	 * @return the current seed
	 */
	public long getCurrentSeed() {
		return currentSeed;
	}

	/**
	 * Sets the current seed.
	 *
	 * @param currentSeed the new current seed
	 */
	protected void setCurrentSeed(long currentSeed) {
		this.currentSeed = currentSeed;
	}

	/** The engine. */
	ISimEngine engine;

	/**
	 * Gets the current scenario.
	 *
	 * @return the current scenario
	 */
	public SimScenario getCurrentScenario() {
		return currentScenario;
	}

	/**
	 * Gets the engine.
	 *
	 * @return the engine
	 */
	public ISimEngine getEngine() {
		return engine;
	}

	/**
	 * Instantiates a new abstract monitor.
	 */
	public AbstractMonitor() {
		engine = new SimEngine();
		Logger.setDateProvider(engine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#loadExperiencePlan(enstabretagne.monitor.
	 * IExperiencePlan)
	 */

	@Override
	public void loadExperiencePlanFromSettings() {
		List<IScenario> scens = new ArrayList<>();
		try {
			for (String s : ScenariiSettings.settings.scenarioInstanceClassNames) {
				Class<?> c = Class.forName(s);
				if(IScenarioInstance.class.isAssignableFrom(c))
				{
					IScenarioInstance scenI = (IScenarioInstance) c.getConstructor().newInstance();
					IScenario scen = scenI.getScenarioInstance();
					Logger.Detail(null, "loadExperiencePlanFromSettings", "Seed trouvée : "+scen.getName());
					scens.add(scen);
				}
				else
				{
					Logger.Fatal(null, "loadExperiencePlanFromSettings", ScenariiMessages.ScenariiNotAScenario);
				}
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExperiencePlan xp = new ExperiencePlan(ScenariiSettings.settings.nbRepliques,ScenariiSettings.settings.germeInitial, scens);
		loadExperiencePlan(xp);
	}

	@Override
	public void loadExperiencePlan(IExperiencePlan experiencePlan) {
		xpPlan = (ExperiencePlan) experiencePlan;
		scenarioIndex = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see enstabretagne.monitor.IMonitor#selectNextScenario()
	 */
	@Override
	public IScenario selectNextScenario() {
		if (xpPlan.getListeScenarios().size() > 0 && scenarioIndex + 1 < xpPlan.getListeScenarios().size()) {
			scenarioIndex++;
			return selectScenario();
		}
		return null;
	}

	/**
	 * Select scenario.
	 *
	 * @return the i scenario
	 */
	private IScenario selectScenario() {
		currentScenario = (SimScenario) xpPlan.getListeScenarios().get(scenarioIndex);
		return currentScenario;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * enstabretagne.monitor.IMonitor#selectScenario(enstabretagne.simulation.core.
	 * IScenario)
	 */
	@Override
	public void selectScenario(IScenario sc) {
		int index = xpPlan.getListeScenarios().indexOf(sc);
		if (index >= 0) {
			scenarioIndex = index;
			selectScenario();
		}
	}

}
