package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import enstabretagne.simulation.components.data.SimFeatures;

public class BoueeFeatures extends SimFeatures {

	private double vitesseMax;
	private double accelerationMax;
	private double taille;
	private double distanceOfDetection;
	public double getDistanceOfDetection() {
		return distanceOfDetection;
	}
	private int type;
	public int getType() {
		return type;
	}
	
	public double getVitesseMax() {
		return vitesseMax;
	}
	
	public double getAccelerationMax() {
		return accelerationMax;
	}
	
	public BoueeFeatures(String id,double vitesseMax,double accelerationMax,double taille,int type,double distanceOfDetection) {
		super(id);
		this.vitesseMax = vitesseMax;
		this.accelerationMax = accelerationMax;
		this.taille = taille;
		this.type=type;
		this.distanceOfDetection=distanceOfDetection;
	}

	public double getTaille() {
		return taille;
	}

}
