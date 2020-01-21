package underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur;

import java.util.concurrent.ThreadLocalRandom;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.core.implementation.SimEvent;
import javafx.geometry.Point3D;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.IMover;

@ToRecord(name="MouvementSequenceur")
public class EntityMouvementSequenceur_DummyDrone extends EntityMouvementSequenceur implements IMover{
	
	int sens = genRandomSens();
	
	private int genRandomSens() {
		int i = 0;
		while (i==0) {
			i  = ThreadLocalRandom.current().nextInt(3)-1;
		}
		return i;
	}
	
	public EntityMouvementSequenceur_DummyDrone(String name, SimFeatures features) {
		super(name, features);
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		super.initializeSimEntity(init);
	}


	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation de MouvementSequenceur");
		//attente
		Post(new FinStaticPhase1(),LogicalDuration.ofSeconds(1));
	}
	

	public class FinStaticPhase1 extends SimEvent {

		@Override
		public void Process() {
			Logger.Information(Owner(), "Process FinStaticPhase1", "Fin de la première phase statique");
			LogicalDateTime d = getCurrentLogicalDate();
			
			int x = 60;
			Point3D depart = mv.getPosition(d);
			Point3D arrivee = depart.add(new Point3D(0,sens*x,0));
			rectilinearMover = new RectilinearMover(d, depart, arrivee, ini.getMaxLinearSpeed());
			mv= rectilinearMover;
			Logger.Information(Owner(), "Process FinStaticPhase1", "Phase mouvement linéaire enclenché");
			//Post(new BugCorrection(),mv.getDurationToReach());
			Post(new Flips(), mv.getDurationToReach());
		}
	}
	
	public class Flips extends SimEvent {
		

		@Override
		public void Process() {
			//Logger.Information(Owner(), "Process FinLinearPhase2", "Fin de la deuxième phase");
			LogicalDateTime d = getCurrentLogicalDate();

			int x = 70;
			
			circulrMover = new CircularMover(d, mv.getPosition(d), mv.getVitesse(d).normalize().multiply(ini.getMaxLinearSpeed()), mv.getPosition(d).add(new Point3D(sens*x,0,0))); //ini.getPositionsCles().get("PointCible2"));
			mv= circulrMover;
			Logger.Information(Owner(), "Process Flips", "Phase flips enclenché");
			sens = -sens;
			Post(new FinCircularPhase2(),mv.getDurationToReach());
		}
		
	}

	public class BugCorrection extends SimEvent {

		@Override
		public void Process() {
			//Logger.Information(Owner(), "Process FinCircularPhase3", "Fin de la troisème phase");
			LogicalDateTime d = getCurrentLogicalDate();
			
			int x = 15;
			
			Point3D dir = new Point3D(x,x,0);
			selfRotator = new SelfRotator(d, mv.getPosition(d), mv.getVitesse(d), mv.getPosition(d).add(dir),ini.getMaxSelfRotationSpeed());
			mv= selfRotator;
			Logger.Information(Owner(), "Process BugCorrection", "Phase mouvement circulaire enclenché");
			//Post(new FinLinearPhase2(),mv.getDurationToReach());
			Post(new Flips(), mv.getDurationToReach());
			
		}
	}
	
	public class FinCircularPhase2 extends SimEvent {

		@Override
		public void Process() {
			Logger.Information(Owner(), "Process FinCircularPhase2", "Fin du premier demi tour");
			LogicalDateTime d = getCurrentLogicalDate();
			
			int x = 120;
			Point3D depart = mv.getPosition(d);
			Point3D arrivee = depart.add(new Point3D(0,sens*x,0));
			rectilinearMover = new RectilinearMover(d, depart, arrivee, ini.getMaxLinearSpeed());
			mv= rectilinearMover;
			Logger.Information(Owner(), "Process FinCircularPhase2", "Phase mouvement linéaire enclenché");
			//Post(new BugCorrection(),mv.getDurationToReach());
//			Post(new Flips(), mv.getDurationToReach());
			Post(new Arret(),mv.getDurationToReach());
		}
	}
	
	public class Dive extends SimEvent {

		@Override
		public void Process() {
			Logger.Information(Owner(), "Process Dive", "Dive began");
			LogicalDateTime d = getCurrentLogicalDate();
			rectilinearMover = new RectilinearMover(d, mv.getPosition(d),new Point3D(1,1,1), ini.getPositionsCles().get("PointSousEau"), ini.getMaxPlongeeSpeed());
			mv= rectilinearMover;
//			Logger.Information(Owner(), "Process Dive", "Dive ended");
			Post(new Arret(),mv.getDurationToReach());
		}
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
