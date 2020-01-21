package enstabretagne.travaux_diriges.TD_corrige.HelloWorldSimple;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework.EntiteSimulee;
import enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework.SimuEngine;

public class Etudiant extends EntiteSimulee{
	private String nom;

	public Etudiant(SimuEngine engine,String nom) {
		super(engine);
		this.nom = nom;
	}
	
	@Override
	public void Init() {
		Post(new Bonjour(Now().add(LogicalDuration.ofMinutes(15)), nom));
	}
}
