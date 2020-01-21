package underwaterExplorer.BasicMovement.SimEntity.Bouee.Representation3D;


import enstabretagne.monitor.Contrat3D;
import enstabretagne.monitor.ObjTo3DMappingSettings;
import enstabretagne.monitor.implementation.Representation3D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape;

@Contrat3D(contrat = IBoueeRepresentation3D.class)
public class Bouee3DRepresentation2 extends Representation3D {
	
	public Bouee3DRepresentation2(ObjTo3DMappingSettings settings) {
		super(settings);
	}

	IBoueeRepresentation3D bouee3D;
	Group maBouee;
	@Override
	public void init(Group world, Object obj) {
		bouee3D = (IBoueeRepresentation3D) obj;
		maBouee = new Group();
	    
	    PhongMaterial material = new PhongMaterial(bouee3D.getColor());
	    Shape shape;
	    
	    switch (bouee3D.getType()) {
	    
		    case 1: //spheres rouges
		    	Sphere s = new Sphere(bouee3D.getSize());
			    s.setMaterial(material);
			    maBouee.getChildren().add(s);
		    	break;
		    case 2: //cylindres jaunes
		    	Cylinder cyl = new Cylinder(bouee3D.getSize(),0.5);  //height?
			    cyl.setMaterial(material);
			    maBouee.getChildren().add(cyl);
		    	break;
		    case 3: //boites vertes
		    	double size = bouee3D.getSize();
		    	Box b = new Box(size, size, size);
			    b.setMaterial(material);
			    maBouee.getChildren().add(b);
		    	break;	
		    case 4: //boite noire
		    	Box bNoire = new Box(4,4,4);
			    bNoire.setMaterial(material);
			    maBouee.getChildren().add(bNoire);
		    	break;
		    default:
		    	break;
	    }
	    world.getChildren().add(maBouee);

	}
	
	@Override
	public void update() {
		Point3D p = bouee3D.getPosition();

		maBouee.setTranslateX(p.getX());
		maBouee.setTranslateY(p.getY());
		maBouee.setTranslateZ(p.getZ());
		

	}


}
	