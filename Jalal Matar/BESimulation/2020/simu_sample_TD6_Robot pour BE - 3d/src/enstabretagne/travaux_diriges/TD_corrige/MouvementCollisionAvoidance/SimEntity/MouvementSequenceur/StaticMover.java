package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur;


import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.implementation.MovableState;
import enstabretagne.monitor.implementation.XYZRotator2;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.IMover;
import javafx.geometry.Point3D;

public class StaticMover implements IMover{

	MovableState initState;
	/*
	 * Point3D initPosition: position initiale x y z par rapport au 0,0,0 du monde
	 * Point3D direction: vecteur en x y z qui indique une direction vers laquelle le système doit regarder
	 * 
	 */

	public StaticMover() {
		
	}

	public void init(Point3D initPosition,Point3D rotationXYZ) {
		
//		Point3D rotationXYZ = XYZRotator2.computeRotationXYZ(direction);
		this.initState = new MovableState(initPosition, Point3D.ZERO, Point3D.ZERO, rotationXYZ, Point3D.ZERO, Point3D.ZERO);
		
	}
	
	
	public LogicalDuration getDurationToReach() {
		return LogicalDuration.POSITIVE_INFINITY;
	}
	
	public Point3D getPosition(LogicalDateTime d) {
		return initState.getPosition();
	}

	public Point3D getVitesse(LogicalDateTime d) {
		return initState.getVitesse();
	}
	public Point3D getAcceleration(LogicalDateTime d) {
		return initState.getAcceleration();
	}

	public Point3D getVitesseRotationXYZ(LogicalDateTime currentLogicalDate) {
		return initState.getVitesseRotationXYZ();
	}

	public Point3D getAccelerationRotationXYZ(LogicalDateTime currentLogicalDate) {
		return initState.getAccelerationRotationXYZ();
	}

	public Point3D getRotationXYZ(LogicalDateTime currentLogicalDate) {
		return initState.getRotationXYZ();
	}



	
}
