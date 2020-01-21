package underwaterExplorer.BasicMovement.SimEntity.Navire;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimInitParameters;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneFeature;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneInit;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;

public class EntityNavireInit extends SimInitParameters {
	private EntityMouvementSequenceurInit mvtSeqIni;
	private String name;

	
	public EntityNavireInit(String nom,EntityMouvementSequenceurInit mvtSeqIni)
	{
		this.mvtSeqIni = mvtSeqIni;
		this.name = nom;
	}
	
	public String getName() {
		return name;
	}
	
	public EntityMouvementSequenceurInit getMvtSeqInitial() {
		return  mvtSeqIni;
	}

}
