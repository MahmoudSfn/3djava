package enstabretagne.monitor.implementation;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.monitor.interfaces.IMovable;
import javafx.geometry.Point3D;

public class MovableState implements IMovable {

	LogicalDateTime t;
	
	Point3D position;
	Point3D vitesse;
	Point3D acceleration;

	Point3D rotationXYZ;
	Point3D vitesseRotationXYZ;
	Point3D accelerationRotationXYZ;
	

	public void setT(LogicalDateTime t) {
		this.t = t;
	}

	public void setPosition(Point3D position) {
		this.position = position;
	}

	public void setVitesse(Point3D vitesse) {
		this.vitesse = vitesse;
	}

	public void setAcceleration(Point3D acceleration) {
		this.acceleration = acceleration;
	}

	public MovableState(Point3D position, Point3D vitesse, Point3D acceleration,Point3D rotationXYZ, Point3D vitesseRotationXYZ, Point3D accelerationXYZ) {//Rotate rotation,Rotate vitesseRotation, Rotate accelerationRotation) {
		super();
		this.t=LogicalDateTime.UNDEFINED;
		
		this.position = position;
		this.vitesse = vitesse;
		this.acceleration = acceleration;
		
		this.accelerationRotationXYZ=accelerationXYZ;
		this.vitesseRotationXYZ=vitesseRotationXYZ;
		this.rotationXYZ=rotationXYZ;
	}

	public LogicalDateTime getT() {
		return t;
	}

	@Override
	public Point3D getPosition() {
		return position;
	}

	@Override
	public Point3D getVitesse() {
		return vitesse;
	}

	@Override
	public Point3D getAcceleration() {
		return acceleration;
	}

	public static final MovableState ZERO = new MovableState(Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO,Point3D.ZERO,Point3D.ZERO);


	@Override
	public Point3D getRotationXYZ() {
		return rotationXYZ;
	}

	@Override
	public Point3D getVitesseRotationXYZ() {
		return vitesseRotationXYZ;
	}

	@Override
	public Point3D getAccelerationRotationXYZ() {
		return accelerationRotationXYZ;
	}
}
