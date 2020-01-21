package underwaterExplorer.BasicMovement.SimEntity.Ocean;

import enstabretagne.simulation.components.data.SimFeatures;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurFeature;

public class EntityOceanFeature extends SimFeatures {

	
	public EntityOceanFeature(String id) {
		super(id);
	}
	
	public EntityMouvementSequenceurFeature getSeqFeature() {
		return null;
	}


}
