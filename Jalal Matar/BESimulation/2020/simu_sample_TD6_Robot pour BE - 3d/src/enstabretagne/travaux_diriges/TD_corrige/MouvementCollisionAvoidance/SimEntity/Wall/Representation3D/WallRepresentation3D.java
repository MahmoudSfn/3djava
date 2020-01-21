package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Representation3D;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.XYZRotator2;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.Expertise.BorderAndPathGenerator;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.Representation3D.IRobot3D;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

@Contrat3D(contrat = IWall3D.class)
public class WallRepresentation3D extends Representation3D{

	IWall3D wall3D;
	Group monMur;
	
	
	public WallRepresentation3D(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	@Override
	public void init(Group world, Object obj) {
		wall3D = (IWall3D) obj;
		monMur = new Group();

		PhongMaterial material;
		material = new PhongMaterial(wall3D.getColor());
		
		List<Node> murs = wall3D.getMurs();
		
		for(Node b:murs) {
			if(b instanceof Shape3D)
			((Shape3D) b).setMaterial(material);
			((Shape3D) b).setDrawMode(DrawMode.FILL);
			

			monMur.getChildren().add(b);
			
			
		}
		
		
//		monMur.setTranslateX(wall3D.getPosition().getX());
//		monMur.setTranslateY(wall3D.getPosition().getY());
//		monMur.setTranslateZ(wall3D.getPosition().getZ());
		
		world.getChildren().add(monMur);

		
	}

	@Override
	public void update() {
		
		
	}
	
	

}
