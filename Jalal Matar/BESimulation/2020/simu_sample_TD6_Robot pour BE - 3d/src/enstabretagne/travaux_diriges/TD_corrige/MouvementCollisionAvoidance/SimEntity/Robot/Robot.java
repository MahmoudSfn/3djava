package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DEdge;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DVertex;
import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.Expertise.BorderAndPathGenerator;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur.EntityMouvementSequenceur;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur.EntityMouvementSequenceurExemple;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.Representation3D.IRobot3D;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Wall;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Robot extends SimEntity implements IMovable, IRobot3D {
	RobotInit rIni;
	RobotFeatures rFeat;
	Point3D maPosition;

	private EntityMouvementSequenceur rmv;

	Point3D dir;// direction du mouvement
	double speed;
	Rotate r;

	@Override
	public Point3D getPosition() {
		return rmv.getPosition(getCurrentLogicalDate());
	}

	@Override
	public Point3D getVitesse() {
		return rmv.getVitesse(getCurrentLogicalDate());
	}

	@Override
	public Point3D getAcceleration() {
		return rmv.getAcceleration(getCurrentLogicalDate());
	}

	@Override
	public Point3D getRotationXYZ() {
		return rmv.getRotationXYZ(getCurrentLogicalDate());
	}

	@Override
	public Point3D getVitesseRotationXYZ() {
		return rmv.getVitesseRotationXYZ(getCurrentLogicalDate());
	}

	@Override
	public Point3D getAccelerationRotationXYZ() {
		return rmv.getAccelerationRotationXYZ(getCurrentLogicalDate());
	}

	@Override
	public Color getColor() {
		return rIni.getCouleur();
	}

	@Override
	public double getSize() {
		return rFeat.getTaille();
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public boolean isBad() {
		return rIni.isBad();
	}

	public Robot(String name, SimFeatures features) {
		super(name, features);
		rFeat = (RobotFeatures) features;

		speed = rFeat.getVitesseMax();
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		rIni = (RobotInit) getInitParameters();
		maPosition = rIni.getPosInit();

		//le robot méchant ne bouge pas
		if (rIni.isBad())
			rmv = (EntityMouvementSequenceur) createChild(EntityMouvementSequenceur.class, "Mvt", rFeat.getEmsf());
		
		//les robots gentils peuvent se déplacer en utilisant le SequenceurExemple
		else
			rmv = (EntityMouvementSequenceur) createChild(EntityMouvementSequenceurExemple.class, "Mvt",
					rFeat.getEmsf());
		rmv.initialize(rIni.getMvtSeqIni());
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation du robot", "test");

		//seul le robot gentil cherche un autre robot et vérifie s'il peut voir la table
		if (!isBad()) {
			List<ISimObject> l = getEngine().requestSimObject(simo -> (simo instanceof Robot) && (simo != this));
			for (ISimObject o : l)
				Logger.Information(this, "AfterActivate", "Robot trouvé=" + o.getName());

			Logger.Information(this, "AfterActivate", "Can see table ? " + canSeeTable());
		}
		rmv.activate();
	}

	Cylinder lineOfSight;

	public Cylinder getLineOfSight() {
		return lineOfSight;
	}

	//algorithme montrant un exemple pour trouver un objet précis de la simulation et de vérifier si on peut le voir
	//c'est à dire sans murs pour boucher la vue!
	
	public boolean canSeeTable() {
		
		//syntaxe complexe ci dessous: (List<Wall>) (List<?>) est une astuce pour caster les membres d'une liste
		//simo -> (xxxxx) est une lambda expression. C'est une manière très compacte d'exprimer une functionnalinterface
		//au final on cherche uniquement des vrais murs (pas les objets de type table par exemple
		List<Wall> walls = (List<Wall>) (List<?>) getEngine()
				.requestSimObject(simo -> (simo instanceof Wall) && ((Wall) simo).getType() == 2);
		List<Bounds> bounds = new ArrayList();
		for (Wall w : walls) {
			bounds.addAll(w.getBounds());
		}

		//puis on cherche la table par son nom... c'est moche mais c'est pour l'exemple!
		Wall table = null;
		List<Wall> objets = (List<Wall>) (List<?>) getEngine().requestSimObject(simo -> (simo.getName() == "Table"));
		if (objets.size() == 1) {
			table = objets.get(0);
			
			//on crée donc un cylindre entre les deux positions
			//on pourra afficher le cylindre dans la vue 3D
			lineOfSight = BorderAndPathGenerator.generateCylinderBetween(table.getPosition(), getPosition());
			lineOfSight.setMaterial(new PhongMaterial(Color.AQUA));

			//on vérifie qu'on voit ou non la position de la table			
			boolean isVisible = BorderAndPathGenerator.intervisibilityBetween(table.getPosition(), getPosition(),
					bounds);
			return isVisible;
		} else
			return false;

	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		rmv.deactivate();
	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {

	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {

	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
	}

	@Override
	public void onParentSet() {

	}

}
