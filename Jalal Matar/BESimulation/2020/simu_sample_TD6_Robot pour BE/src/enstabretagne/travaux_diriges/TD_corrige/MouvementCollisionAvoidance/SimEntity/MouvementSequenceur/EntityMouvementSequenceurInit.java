package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur;


import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.data.SimInitParameters;
import javafx.geometry.Point3D;

public class EntityMouvementSequenceurInit extends SimInitParameters {

	private IMovable etatInitial;
	private Point3D target;
	private String name;
	
	public EntityMouvementSequenceurInit(String nom,IMovable etatInitial,Point3D target)
	{
		this.etatInitial = etatInitial;
		this.name = nom;
		this.target = target;
	}
	
	public Point3D getTarget() {
		return target;
	}
	public String getName() {
		return name;
	}
	
	public IMovable getEtatInitial() {
		return etatInitial;
	}

}
