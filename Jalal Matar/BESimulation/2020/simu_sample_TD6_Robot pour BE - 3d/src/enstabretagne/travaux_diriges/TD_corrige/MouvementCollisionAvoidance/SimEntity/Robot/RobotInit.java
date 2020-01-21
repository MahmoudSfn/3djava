package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot;

import enstabretagne.monitor.implementation.MovableState;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class RobotInit extends SimInitParameters{
	private String name;
	private Color couleur;
	private boolean isBad;
	
	private EntityMouvementSequenceurInit mvtSeqIni;
	public EntityMouvementSequenceurInit getMvtSeqIni() {
		return mvtSeqIni;
	}

	
	public String getName() {
		return name;
	}
	public Color getCouleur() {
		return couleur;
	}
	public Point3D getPosInit() {
		return mvtSeqIni.getEtatInitial().getPosition();
	}
	public boolean isBad() {
		return isBad;
	}
	
	public RobotInit(String name, 
			Color couleur, 
			Point3D posInit,
			Point3D orientationXYZInit,
			boolean isBad,
			Point3D target) {
		super();
		this.name = name;
		this.couleur = couleur;
		
		MovableState mst = new MovableState(
				posInit, 
				Point3D.ZERO, 
				Point3D.ZERO, 
				orientationXYZInit, 
				Point3D.ZERO, 
				Point3D.ZERO);
		
		this.mvtSeqIni = new EntityMouvementSequenceurInit(name + "Detector", 
				mst,target);
		this.isBad = isBad;
	}
	
	
}
