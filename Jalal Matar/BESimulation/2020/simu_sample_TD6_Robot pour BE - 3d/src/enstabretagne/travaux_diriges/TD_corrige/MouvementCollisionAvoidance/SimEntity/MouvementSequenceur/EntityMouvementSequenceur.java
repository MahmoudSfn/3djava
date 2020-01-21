package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.MouvementSequenceur;


import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.IMover;
import javafx.geometry.Point3D;


/*
 * La classe ci dessous constitue l'astuce principale utilisée pour donner 
 * l'illusion du mouvement continue en simulation événementielle.
 * 
 * On fournit 4 comportements d'extrapolation: les movers
 * - le statique qui se contente d'avoir une position et une orientation
 * - le rectilinéaire qui peut à partir d'une position initiale, d'une vistesse initiale extrapoler une position dans le temps
 * - le circulaire qui peut à partir d'une position initiale, d'une orientation initiale, d'une vitesse de se déplacer selon un cercle dpour atteindre une position
 * - la rotation sur soit qui permet à partir d'une position et d'une orientation initiale, d'atteindre un angle de rotation déterminé 
 */
@ToRecord(name="MouvementSequenceur")
public class EntityMouvementSequenceur extends SimEntity implements IMover{
	
	protected StaticMover staticMover;
	protected RectilinearMover rectilinearMover;
	protected CircularMover circulrMover;
	protected SelfRotator selfRotator;
	
	protected IMover mv;

	protected EntityMouvementSequenceurInit ini;
	

	@Override
	public LogicalDuration getDurationToReach() {
		return mv.getDurationToReach();
	}

	@Override
	public Point3D getPosition(LogicalDateTime d) {
		return mv.getPosition(d);
	}

	@Override
	public Point3D getVitesse(LogicalDateTime d) {
		return mv.getVitesse(d);
	}

	@Override
	public Point3D getAcceleration(LogicalDateTime d) {
		return mv.getAcceleration(d);
	}

	@Override
	public Point3D getRotationXYZ(LogicalDateTime d) {
		return mv.getRotationXYZ(d);
	}

	@Override
	public Point3D getVitesseRotationXYZ(LogicalDateTime d) {
		return mv.getVitesseRotationXYZ(d);
	}

	@Override
	public Point3D getAccelerationRotationXYZ(LogicalDateTime d) {
		return mv.getAccelerationRotationXYZ(d);
	}

	public EntityMouvementSequenceur(String name, SimFeatures features) {
		super(name, features);
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		ini = ((EntityMouvementSequenceurInit) init);
		
		staticMover =new StaticMover();
		staticMover.init(ini.getEtatInitial().getPosition(), ini.getEtatInitial().getRotationXYZ());
		
		rectilinearMover = new RectilinearMover();
		circulrMover = new CircularMover();
		selfRotator = new SelfRotator();

		mv=staticMover;
		
	}

	

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
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


	

}
