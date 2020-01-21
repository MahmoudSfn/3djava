package enstabretagne.travaux_diriges.TD_corrige.HelloWorldSimple;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework.SimuEngine;

public class ENSTA {
	public static void main(String[] args) {
		SimuEngine engine = new SimuEngine();

		LogicalDateTime start = new LogicalDateTime("04/12/2019 14:00");
		LogicalDateTime end = new LogicalDateTime("04/12/2019 15:00");

		new Etudiant(engine, "Olivier");
		new Etudiant(engine, "Luka");

		engine.initSimulation(start, end);
		engine.simulate();

	}
}
