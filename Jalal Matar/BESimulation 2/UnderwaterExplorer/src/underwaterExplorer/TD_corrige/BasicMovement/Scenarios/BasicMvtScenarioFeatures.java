package underwaterExplorer.TD_corrige.BasicMovement.Scenarios;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimFeatures;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.BoueeFeatures;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.BoueeInit;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneFeature;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneInit;
import underwaterExplorer.BasicMovement.SimEntity.Navire.EntityNavireFeature;
import underwaterExplorer.BasicMovement.SimEntity.Navire.EntityNavireInit;
import underwaterExplorer.BasicMovement.SimEntity.Ocean.EntityOceanFeature;
import underwaterExplorer.BasicMovement.SimEntity.Ocean.EntityOceanInit;

public class BasicMvtScenarioFeatures extends SimFeatures {

	private HashMap<BoueeFeatures, BoueeInit> bouees;
	private HashMap<EntityNavireFeature, EntityNavireInit> navires;
	private HashMap<EntityOceanFeature,EntityOceanInit> ocean;
	private HashMap<EntityDroneFeature,EntityDroneInit> drones;
	
	public HashMap<EntityOceanFeature, EntityOceanInit> getOcean() {
		return ocean;
	}
	public HashMap<EntityNavireFeature, EntityNavireInit> getNavires() {
		return navires;
	}
	
	public HashMap<BoueeFeatures, BoueeInit> getBouees() {
		return bouees;
	}
	
	public HashMap<EntityDroneFeature,EntityDroneInit> getDrones() {
		return drones;
	}
	
	public BasicMvtScenarioFeatures(String id) {
		super(id);
		bouees = new HashMap<>();
		navires = new HashMap<>();
		ocean = new HashMap<>();
		drones = new HashMap<>();
	}	

}
