package enstabretagne.travaux_diriges.TD_corrige.Simple3D;

import java.time.Instant;
import java.util.HashMap;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.implementation.ExperiencePlan;
import enstabretagne.monitor.implementation.FX3DMonitor2;
import enstabretagne.monitor.implementation.UniversalMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenario;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;


public class MainApp {

	public static void main(String[] args) {
		
		FX3DMonitor2.launch(FX3DMonitor2.class,args);;
		
	}
	
}
