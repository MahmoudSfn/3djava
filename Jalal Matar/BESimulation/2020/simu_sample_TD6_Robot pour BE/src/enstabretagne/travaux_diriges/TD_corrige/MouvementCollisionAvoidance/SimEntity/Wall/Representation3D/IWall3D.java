package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Representation3D;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.monitor.interfaces.IMovable;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;

public interface IWall3D extends IMovable{
	Color getColor();
	int getType();
	
	double getWidth();
	double getHeight();
	
	List<Node> getMurs();
	List<Node> generateMursWithDistanceOfSecurity(double distanceOfSecurity);
	
	List<Point3D> getWallShape();
	List<Point3D> getGroundBorder(double d, double detectsize);


	List<Bounds> getBounds();
	List<Bounds> getBounds(double d);
}
