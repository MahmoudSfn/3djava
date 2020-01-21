package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur;

import enstabretagne.simulation.components.data.SimFeatures;

public class EntityMouvementSequenceurFeature extends SimFeatures {

	private double maxLinearSpeed;
	private double maxSelfRotationSpeed;
	
	public double getMaxLinearSpeed() {
		return maxLinearSpeed;
	}
	public double getMaxSelfRotationSpeed() {
		return maxSelfRotationSpeed;
	}
	public EntityMouvementSequenceurFeature(String id, double maxLinearSpeed, double maxSelfRotationSpeed) {
		super(id);
		this.maxLinearSpeed = maxLinearSpeed;
		this.maxSelfRotationSpeed = maxSelfRotationSpeed;
	}

}
