package enstabretagne.travaux_diriges.TD_corrige.HelloWorld;

import java.util.HashMap;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.Scenarios.StudentScenario;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.Scenarios.StudentScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentFeatures;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.SimEntity.StudentInit;
import enstabretagne.travaux_diriges.TD_corrige.HelloWorld.expertise.Film;

public class HelloWorldScenarioInstance implements IScenarioInstance {

	@Override
	public IScenario getScenarioInstance(long seed) {
		//pour l'exemple on se force ici à mettre un feature à ce niveau.
		//l'idée est que le scénario aie toujours toutes les données pour créer le scénario.
		//cette façon de procéder où dès le début on dispose de l'init et du feature est typique de simulations fermées.
		HashMap<StudentInit,StudentFeatures> h = new HashMap<>();
		h.put(new StudentInit("John","La douleur n'est qu'une information",Film.rambo,Film.sissi),StudentFeatures.defaultFeatures);
		h.put( new StudentInit("Yoda","La peur mène au côté obscur de la Force",Film.starwars),StudentFeatures.defaultFeatures);
		h.put( new StudentInit("Anakin","Je suis ton père",Film.starwars),StudentFeatures.defaultFeatures);
		h.put(new StudentInit("Luc","Non ce n'est pas vrai, ce n'est pas possible",Film.starwars),StudentFeatures.defaultFeatures);
		h.put(new StudentInit("Leia","Autant embrasser un Wookie!",Film.sissi),StudentFeatures.defaultFeatures);
		
		StudentScenarioFeatures ssf = new StudentScenarioFeatures("Simple Scénario", h);
		
		LogicalDateTime start = new LogicalDateTime("05/12/2019 06:00");
		LogicalDateTime end = start.add(LogicalDuration.ofMinutes(2));
		return new StudentScenario(new ScenarioId("S1")	, ssf, start, end);
	}

}
