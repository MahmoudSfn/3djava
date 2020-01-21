package underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.implementation.SimEvent;
import javafx.geometry.Point3D;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.IMover;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;

@ToRecord(name="MouvementSequenceur")
public class EntityMouvementSequenceur extends SimEntity implements IMover{
	
	protected StaticMover staticMover;
	protected RectilinearMover rectilinearMover;
	protected CircularMover circulrMover;
	protected SelfRotator selfRotator;
	
	protected IMover mv;

	protected EntityMouvementSequenceurInit ini;
	
	public EntityMouvementSequenceur(String name, SimFeatures features) {
		super(name, features);
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		ini = ((EntityMouvementSequenceurInit) getInitParameters());
		
		staticMover =new StaticMover(ini.getEtatInitial().getPosition(), ini.getEtatInitial().getVitesse());
		
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
	
	public class Arret extends SimEvent {

		@Override
		public void Process() {
			Logger.Information(Owner(), "Process Arret", "Fin de la phase");
			LogicalDateTime d = getCurrentLogicalDate();
			staticMover =new StaticMover(mv.getPosition(d), mv.getVitesse(d));			
			Logger.Information(Owner(), "Process Arret", "Mode arrêt : %s", mv.getPosition(d));
			mv = staticMover;
		}
		
	}

}
