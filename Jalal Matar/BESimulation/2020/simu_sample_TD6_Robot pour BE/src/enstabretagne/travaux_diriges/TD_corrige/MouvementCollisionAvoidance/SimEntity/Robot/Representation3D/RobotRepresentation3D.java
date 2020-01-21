package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.Representation3D;

import java.util.ArrayList;
import java.util.List;

import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DEdge;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DVertex;
import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.XYZRotator2;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.Expertise.BorderAndPathGenerator;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

@Contrat3D(contrat = IRobot3D.class)
public class RobotRepresentation3D extends Representation3D{
	public RobotRepresentation3D(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	IRobot3D robot3D;
	Group monRobot;
	Group myworld;
	@Override
	public void init(Group world, Object obj) {
		
		myworld = world;
		robot3D = (IRobot3D) obj;
		monRobot = new Group();
		
		
		
		PhongMaterial material;
		material = new PhongMaterial(robot3D.getColor());

		Sphere s;
		s= new Sphere(robot3D.getSize());
		s.setMaterial(new PhongMaterial(Color.BLACK));
		s.setDrawMode(DrawMode.LINE);
		
		s= new Sphere(robot3D.getSize());
		s.setMaterial(material);
		monRobot.getChildren().add(s);

		Sphere sx = new Sphere(robot3D.getSize()/5);
		sx.setTranslateX(robot3D.getSize());
		sx.setMaterial(new PhongMaterial(Color.BLUE));
		Sphere sy = new Sphere(robot3D.getSize()/5);
		sy.setTranslateY(robot3D.getSize());
		sy.setMaterial(new PhongMaterial(Color.RED));
		Sphere sz = new Sphere(robot3D.getSize()/5);
		sz.setTranslateZ(robot3D.getSize());
		sz.setMaterial(new PhongMaterial(Color.GREEN));
		
		
		monRobot.getChildren().add(sx);
		monRobot.getChildren().add(sy);
		monRobot.getChildren().add(sz);
		
		
		world.getChildren().add(monRobot);
	}
	
	@Override
	public void update() {
		
		Point3D p = robot3D.getPosition();

		monRobot.setTranslateX(p.getX());
		monRobot.setTranslateY(p.getY());
		monRobot.setTranslateZ(p.getZ());
		
		Point3D rot = robot3D.getRotationXYZ();
		
		Affine a = XYZRotator2.getTransformByAngle(rot);
		monRobot.getTransforms().setAll(a);
		
		Cylinder c=robot3D.getLineOfSight();
		if(c!=null)
			if(!myworld.getChildren().contains(c))
					myworld.getChildren().add(c);
		
	}
	
	
}
