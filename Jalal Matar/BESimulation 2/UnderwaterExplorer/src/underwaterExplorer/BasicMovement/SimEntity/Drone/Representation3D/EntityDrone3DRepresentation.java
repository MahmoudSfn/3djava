package underwaterExplorer.BasicMovement.SimEntity.Drone.Representation3D;

import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import enstabretagne.monitor.implementation.XYZRotator2;
//import enstabretagne.monitor.implementation.XYZRotator;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
//import javafx.scene.shape.Sphere;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

@Contrat3D(contrat = EntityDrone3DRepresentationInterface.class)
public class EntityDrone3DRepresentation extends Representation3D {

	public EntityDrone3DRepresentation(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	EntityDrone3DRepresentationInterface Drone3D;
	Group drone;
	int r1=1;
	int h=5;

	//ici mettre les objets3D représentant l'entité
	//Sphere sph;

	@Override
	public void init(Group world, Object obj) {
		Drone3D = (EntityDrone3DRepresentationInterface) obj;
	    drone = new Group();


	    PhongMaterial material = new PhongMaterial(Drone3D.getColor());

	    Cylinder cy = new Cylinder(r1, h+1);
	    cy.setMaterial(material);
	    cy.setRotationAxis(Rotate.Z_AXIS);
	    cy.setRotate(90.0);
	    cy.setTranslateX(-h/2);
	    drone.getChildren().add(cy);

	    Sphere s = new Sphere(r1);
	    s.setMaterial(material);
	    s.setTranslateX(h/2);
	    drone.getChildren().add(s);

	    PhongMaterial mat2 = new PhongMaterial(Color.AQUA);

	    Cylinder cy1 = new Cylinder(r1, h);
	    cy1.setMaterial(mat2);
	    cy1.setRotationAxis(Rotate.Z_AXIS);
	    cy1.setRotate(90.0);
//	    cy1.setTranslateZ(r1*2);
	    cy1.setTranslateY(r1);
	    drone.getChildren().add(cy1);

	    Cylinder cy2 = new Cylinder(r1, h);
	    cy2.setMaterial(mat2);
	    cy2.setRotationAxis(Rotate.Z_AXIS);
	    cy2.setRotate(90.0);
//	    cy2.setTranslateZ(r1*2);
	    cy2.setTranslateY(-r1);
	    drone.getChildren().add(cy2);


	    double c = r1;
	    Box b = new Box(c,c,c);
	    material = new PhongMaterial(Color.ORANGE);
	    b.setMaterial(mat2);
	    b.setTranslateZ(r1);
	    b.setTranslateX(c);
	    drone.getChildren().add(b);

		world.getChildren().add(drone);

	}

	@Override
	public void update() {
		Point3D p = Drone3D.getPosition();

		drone.setTranslateX(p.getX());
		drone.setTranslateY(p.getY());
		drone.setTranslateZ(p.getZ());

		Point3D rot = Drone3D.getRotationXYZ();

		Affine a = XYZRotator2.getTransformByAngle(rot);
		drone.getTransforms().setAll(a);

	}


}
