package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot;

import java.util.List;

import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur.EntityMouvementSequenceurFeature;

public class RobotFeatures extends SimFeatures{

	private double vitesseMax;
	private double accelerationMax;
	private double rotationSpeedMax;
	private double taille;
		
	private EntityMouvementSequenceurFeature emsf;
	public EntityMouvementSequenceurFeature getEmsf() {
		return emsf;
	}
	
	public double getVitesseMax() {
		return vitesseMax;
	}
	public double getRotationSpeedMax() {
		return rotationSpeedMax;
	}
	public double getTaille() {
		return taille;
	}
	public RobotFeatures(String id,
			double vitesseMax, 
			double rotationSpeedMax, 
			double taille)
	{
		
		super(id);
		this.vitesseMax = vitesseMax;
		this.rotationSpeedMax = rotationSpeedMax;
		this.taille = taille;
		
		
		
		this.emsf = new EntityMouvementSequenceurFeature(
				"Sequenceur",
				vitesseMax,
				rotationSpeedMax);
				
	}
	
	
	
}
