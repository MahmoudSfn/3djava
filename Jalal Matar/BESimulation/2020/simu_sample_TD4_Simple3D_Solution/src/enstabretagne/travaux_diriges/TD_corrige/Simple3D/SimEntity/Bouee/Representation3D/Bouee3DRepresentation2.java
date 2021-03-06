package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Representation3D;


import org.opengis.referencing.cs.CylindricalCS;

import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.XYZRotator2;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

@Contrat3D(contrat = IBoueeRepresentation3D.class)
public class Bouee3DRepresentation2 extends Representation3D {

	public Bouee3DRepresentation2(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	IBoueeRepresentation3D bouee3D;
	Group maBouee;

	/*@Override
	public void init(Group world, Object obj) {
		bouee3D = (IBoueeRepresentation3D) obj;
		maBouee = new Group();

		PhongMaterial material;
		material = new PhongMaterial(bouee3D.getColor());

		Sphere s = new Sphere(bouee3D.getSize()/3);
		//Cylinder s = new Cylinder(5, 2);
		//Box s = new Box(2, 2, 2);
		s.setMaterial(material);
		maBouee.getChildren().add(s);

		if (bouee3D.getType() >= 1) {

			material = new PhongMaterial(Color.DARKGREEN);

			Sphere s1 = new Sphere(bouee3D.getSize() / 3);
			s1.setMaterial(material);
			maBouee.getChildren().add(s1);
			s1.setTranslateX(bouee3D.getSize());

			material = new PhongMaterial(Color.BROWN);
			
			Sphere s2 = new Sphere(bouee3D.getSize()/3);
			s2.setMaterial(material);
			maBouee.getChildren().add(s2);
			s2.setTranslateX(-bouee3D.getSize());

			Sphere s3 = new Sphere(bouee3D.getSize() / 3);
			s3.setMaterial(material);
			maBouee.getChildren().add(s3);
			s3.setTranslateY(bouee3D.getSize());

			Sphere s4 = new Sphere(bouee3D.getSize()/3);
			s4.setMaterial(material);
			maBouee.getChildren().add(s4);
			s4.setTranslateY(-bouee3D.getSize());

			material = new PhongMaterial(Color.BLUEVIOLET);

			Cylinder cy = new Cylinder(bouee3D.getSize()/2, bouee3D.getSize() / 2 * 4);
			cy.setMaterial(material);
			cy.setRotationAxis(Rotate.X_AXIS);
			cy.setRotate(180);
			maBouee.getChildren().add(cy);
			cy.setTranslateZ(bouee3D.getSize() * 3 / 2);
		}

		world.getChildren().add(maBouee);

	}*/
	@Override
	public void init(Group world, Object obj) {
		bouee3D = (IBoueeRepresentation3D) obj;
		maBouee = new Group();

		PhongMaterial material;
		material = new PhongMaterial(bouee3D.getColor());

		Cylinder c = new Cylinder(0.7, 3);
		c.setMaterial(new PhongMaterial(Color.DARKCYAN));
		c.setRotationAxis(Rotate.X_AXIS);
		c.setRotate(90);
		maBouee.getChildren().add(c);

		if (bouee3D.getType() >= 1) {

			//material = new PhongMaterial(Color.WHITE);

			Sphere SHL = new Sphere(bouee3D.getSize() / 5);
			SHL.setMaterial(new PhongMaterial(Color.TOMATO));
			maBouee.getChildren().add(SHL);
			SHL.setTranslateX(bouee3D.getSize()/5);
			
			Sphere SHR = new Sphere(bouee3D.getSize() / 5);
			SHR.setMaterial(new PhongMaterial(Color.TOMATO));
			maBouee.getChildren().add(SHR);
			SHR.setTranslateX(-bouee3D.getSize()/5);
			
			Sphere SFL = new Sphere(bouee3D.getSize() / 5);
			SFL.setMaterial(new PhongMaterial(Color.DARKBLUE));
			maBouee.getChildren().add(SFL);
			SFL.setTranslateX(bouee3D.getSize()/5);
			SFL.setTranslateZ(-bouee3D.getSize()/2);
			
			Sphere SFR = new Sphere(bouee3D.getSize() / 5);
			SFR.setMaterial(new PhongMaterial(Color.DARKBLUE));
			maBouee.getChildren().add(SFR);
			SFR.setTranslateX(-bouee3D.getSize()/5);
			SFR.setTranslateZ(-bouee3D.getSize()/2);
/*
			Sphere s2 = new Sphere(bouee3D.getSize()/5);
			s2.setMaterial(material);
			maBouee.getChildren().add(s2);
			s2.setTranslateX(-bouee3D.getSize()/5);
			s2.setTranslateZ(bouee3D.getSize()/3);
			
			material = new PhongMaterial(Color.WHEAT);
			
			Sphere s3 = new Sphere(bouee3D.getSize() / 5);
			s3.setMaterial(material);
			maBouee.getChildren().add(s3);
			s3.setTranslateX(bouee3D.getSize()/5);

			Sphere s4 = new Sphere(bouee3D.getSize()/5);
			s4.setMaterial(material);
			maBouee.getChildren().add(s4);
			s4.setTranslateX(-bouee3D.getSize()/5);
			s4.setTranslateZ(-bouee3D.getSize()/3);
*/
			material = new PhongMaterial(Color.CHOCOLATE);
			
			Sphere s = new Sphere(bouee3D.getSize()*2/5);
			s.setMaterial(material);
			maBouee.getChildren().add(s);
			s.setTranslateZ(bouee3D.getSize() * 1 / 2);
			
		}

		world.getChildren().add(maBouee);

	}

	public void setRotation(Rotate r, double angle, Point3D pivot, Point3D axe) {
		r.setAngle(angle);
		r.setAxis(axe);
		r.setPivotX(pivot.getX());
		r.setPivotY(pivot.getY());
		r.setPivotZ(pivot.getZ());
	}

	int i = 0;

	@Override
	public void update() {
		Point3D p = bouee3D.getPosition();

		maBouee.setTranslateX(p.getX());
		maBouee.setTranslateY(p.getY());
		maBouee.setTranslateZ(p.getZ());
		
		Point3D xyz = XYZRotator2.computeRotationXYZ(new Point3D(1,1,1));
		xyz = new Point3D(0, -10, 45);
		if(bouee3D.getType()==2)
		{
			XYZRotator2.rotate(maBouee, xyz);
		}
		xyz = xyz.add(0,0,45);
		if(bouee3D.getType()==3)
		{
			XYZRotator2.rotate(maBouee, xyz);
		}

	}

}
