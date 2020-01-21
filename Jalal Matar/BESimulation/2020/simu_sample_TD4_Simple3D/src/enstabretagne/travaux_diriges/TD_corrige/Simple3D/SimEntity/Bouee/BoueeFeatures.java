package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import enstabretagne.simulation.components.data.SimFeatures;
import javafx.geometry.Point3D;

public class BoueeFeatures extends SimFeatures {

	private double vitesseMax;
	private double accelerationMax;
	private double taille;
	
	public double getVitesseMax() {
		return vitesseMax;
	}
	
	public double getAccelerationMax() {
		return accelerationMax;
	}
	
	public BoueeFeatures(String id,double vitesseMax,double accelerationMax,double taille) {
		super(id);
		this.vitesseMax = vitesseMax;
		this.accelerationMax = accelerationMax;
		this.taille = taille;
	}

	public double getTaille() {
		return taille;
	}

}
