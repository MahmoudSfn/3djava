package enstabretagne.travaux_diriges.TD_corrige.HelloWorldSimple;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.travaux_diriges.TD_corrige.EngineSimple.Framework.SimEvent;

public class Bonjour extends SimEvent {

	private String nom;
	
	public Bonjour(LogicalDateTime d,String nom) {
		super(d);
		this.nom = nom;
	}
	
	
	public void process() {
		Logger.Detail(null, "bonjour.Process", "Bonjour de la part de "+nom + " à " +getDateOccurence());
		this.rescheduleAt(getDateOccurence().add(LogicalDuration.ofMinutes(5)));
		entitePorteuseEvenement.Post(this);
	}

}
