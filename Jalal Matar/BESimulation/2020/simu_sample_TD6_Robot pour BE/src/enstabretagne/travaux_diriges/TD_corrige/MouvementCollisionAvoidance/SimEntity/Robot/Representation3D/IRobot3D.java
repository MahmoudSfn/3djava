package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.Representation3D;

import java.util.List;

import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DEdge;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DVertex;
import enstabretagne.monitor.interfaces.IMovable;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;

public interface IRobot3D extends IMovable{
	Color getColor();
	double getSize();
	int getType();
	boolean isBad();
	
	Cylinder getLineOfSight();
}
