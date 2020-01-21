package enstabretagne.monitor.interfaces;

import javafx.geometry.Point3D;

public interface IMovable {
		public Point3D getPosition();
		public Point3D getVitesse();
		public Point3D getAcceleration();


		public Point3D getRotationXYZ();
		public Point3D getVitesseRotationXYZ();
		public Point3D getAccelerationRotationXYZ();
}
