package enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework;

import enstabretagne.base.time.LogicalDateTime;

public abstract class EntiteSimulee {
	private SimuEngine engine;

	public EntiteSimulee(SimuEngine engine) {
		this.engine=engine;
		engine.mesEntitesSimulees.add(this);
	}
	
	public void Post(SimEvent ev) {
		engine.Post(ev);
		ev.entitePorteuseEvenement = this;
	}
	
	public LogicalDateTime Now() {
		return engine.getCurrentDate();
	}
	
	public abstract void Init();
}
