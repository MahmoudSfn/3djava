package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.simulation.components.data.SimInitParameters;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class WallInit extends SimInitParameters {
	private String name;
	private Point3D posInit;
	private Point3D rotationXYZInit;
	private List<Point3D> path; 
	
	public List<Point3D> getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
	public Point3D getPosInit() {
		return posInit;
	}
	public Point3D getRotationXYZInit() {
		return rotationXYZInit;
	}
	public WallInit(String name, Point3D posInit, Point3D rotationXYZInit, List<Point3D> path) {
		super();
		this.name = name;
		this.posInit = posInit;
		this.rotationXYZInit = rotationXYZInit;
		this.path = path;
	}
	

	
	
	public void generateMurs(double largeur, double hauteur,double securityDistance, List<Node> listeMurs) {
		double len;
		Translate lastT = new Translate(); 
		Point3D lastV= Point3D.ZERO;
		largeur = largeur +securityDistance;
		
		for(int i=0;i<getPath().size()-1;i++) {
			Point3D p1=getPath().get(i);
			if(i==0) lastV = p1;
			Point3D p2=getPath().get(i+1);
			Point3D xwVector = p2.subtract(p1);
			len=xwVector.magnitude();
			
			Point3D ywVector = Rotate.Z_AXIS.crossProduct(xwVector).normalize().multiply(largeur); 
			Point3D zwVector = Rotate.Z_AXIS.multiply(hauteur); 

			Point3D centerCorrection = Point3D.ZERO;
			centerCorrection = centerCorrection.add(xwVector.multiply(0.5));
			centerCorrection = centerCorrection.add(zwVector.multiply(0.5));
			
			
			Box b = new Box(len, largeur,hauteur);
			listeMurs.add(b);
			

			Translate t = new Translate();
			t.setX(centerCorrection.getX()+lastV.getX());
			t.setY(centerCorrection.getY()+lastV.getY());
			t.setZ(centerCorrection.getZ()+lastV.getZ());
			
			b.getTransforms().add(t);	
			
			lastV =lastV.add(xwVector); 
			
			Rotate r = new Rotate();
			r.setAxis(Rotate.Z_AXIS);
			double angle = (new Point3D(1,0,0)).angle(xwVector);
			r.setAngle(angle);

			b.getTransforms().add(r);

		}

		for(int i=0;i<getPath().size();i++) {
			Cylinder c = new Cylinder(largeur/2,hauteur);
			listeMurs.add(c);
			
			c.setTranslateX(getPath().get(i).getX());
			c.setTranslateY(getPath().get(i).getY());
			c.setTranslateZ(getPath().get(i).getZ()+hauteur/2);
						

			Rotate rc = new Rotate();
			rc.setAxis(Rotate.X_AXIS);
			rc.setAngle(90);
			c.getTransforms().add(rc);
			
		}

	}
	

}
