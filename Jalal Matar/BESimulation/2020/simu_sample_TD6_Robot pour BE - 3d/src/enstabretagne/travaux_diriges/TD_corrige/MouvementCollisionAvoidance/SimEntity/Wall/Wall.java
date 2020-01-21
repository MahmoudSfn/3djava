package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.Expertise.BorderAndPathGenerator;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Representation3D.IWall3D;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Wall extends SimEntity implements IMovable,IWall3D{

	WallInit wIni;
	WallFeatures wFeat;
	
	public Wall(String name, SimFeatures features) {
		super(name, features);
		wFeat = (WallFeatures) features;
		murs = new ArrayList<>();
		borders = new HashMap<>();
	}

	@Override
	public Color getColor() {
		return wFeat.getCouleur();
	}

	@Override
	public int getType() {
		return wFeat.getType();
	}

	@Override
	public double getWidth() {
		return wFeat.getWidth();
	}

	@Override
	public double getHeight() {
		return wFeat.getHeight();
	}

	@Override
	public List<Point3D> getWallShape() {
		return wIni.getPath();
	}

	@Override
	public Point3D getPosition() {
		return wIni.getPosInit();
	}

	@Override
	public Point3D getVitesse() {
		return Point3D.ZERO;
	}

	@Override
	public Point3D getAcceleration() {
		return Point3D.ZERO;
	}

	@Override
	public Point3D getRotationXYZ() {
		return wIni.getRotationXYZInit();
	}

	@Override
	public Point3D getVitesseRotationXYZ() {
		return Point3D.ZERO;
	}

	@Override
	public Point3D getAccelerationRotationXYZ() {
		return Point3D.ZERO;
	}

	@Override
	public void onParentSet() {
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		wIni = (WallInit) getInitParameters();
		generateMurs();
	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation du robot","test");		
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
	}

	HashMap<String,List<Point3D>> borders;
	@Override
	public List<Point3D> getGroundBorder(double d, double detectsize) {
		String id = d+"#"+detectsize;
		if(borders.containsKey(id))
			return borders.get(id);

		List<Point3D> border = BorderAndPathGenerator.getBorder(this,d,detectsize);
		borders.put(id, border);
		return border;
	}

	


	
	List<Node> murs;
	@Override
	public List<Node> getMurs() {
		return murs;
	}
	
	//Le Bounds en Javafx correspond à la boite englobante du modèle 3D
	//C'est ce qu'utilise l'outil d'intersection de JavaFX
	@Override
	public List<Bounds> getBounds(double dSecurity){
		List<Bounds> ns = new ArrayList<>(); 

		for (Node n : generateMursWithDistanceOfSecurity(dSecurity)) {
			ns.add(n.getBoundsInParent());
			}
		return ns;
	}

	@Override
	public List<Bounds> getBounds() {
		
		return getBounds(0);
	}

	//Appel à l'algorithme de génération des murs selon une distance de sécurité autour du mur
	//correspondant à la largeur du robot afin que le robot ne heurte pas les paroies
	@Override
	public List<Node> generateMursWithDistanceOfSecurity(double distanceOfSecurity) {
		List<Node> ns = new ArrayList<>();
		((WallInit) getInitParameters()).generateMurs(getWidth(),getHeight(),distanceOfSecurity,ns);
		for (Node n : ns) {
			Translate t = new Translate(getPosition().getX(), getPosition().getY(), getPosition().getZ());
			
			n.getTransforms().add(0, t);
		}
		return ns;
	}

	//Génération des murs sans distance de sécurité. C'est le mur qu'on affiche
	public void generateMurs() {
		murs.clear();
		murs.addAll(generateMursWithDistanceOfSecurity(0));
	}


}
