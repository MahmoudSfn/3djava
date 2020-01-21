package underwaterExplorer.BasicMovement.SimEntity.Drone;

import java.util.List;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.ISimObject;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.simulation.core.implementation.SimEvent;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.Bouee;
import underwaterExplorer.BasicMovement.SimEntity.Drone.Representation3D.EntityDrone3DRepresentationInterface;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceur;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceur_DummyDrone;

@ToRecord(name="Drone")
public class EntityDrone extends SimEntity implements IMovable,EntityDrone3DRepresentationInterface{

	private EntityMouvementSequenceur_DummyDrone rmv;
	private EntityDroneInit DroneInit;
	private EntityDroneFeature DroneFeature;
	private SimObjectRequest detectionArtifactRequest;
	private static boolean blackBoxFound=false;


	public EntityDrone(String name, SimFeatures features) {
		super(name, features);
		DroneFeature = (EntityDroneFeature) features;
		 detectionArtifactRequest = new SimObjectRequest() {

			@Override
			public boolean filter(ISimObject o) {
				// TODO Auto-generated method stub
				if(Bouee.class.isAssignableFrom(o.getClass())){
					Bouee b = (Bouee)o;

					Point3D pos = rmv.getPosition(getCurrentLogicalDate());
					if(!blackBoxFound && b.getPosition().distance(pos)<= DroneInit.getDetectionRadius() && b.detected()==false)
					{
						b.setDetected();
						if (b.getName().equals("Boite noire")) {
							blackBoxFound = true;
							stopDrones();
//							BeforeDeactivating(rmv, false);
							System.out.println("Stop");
						}
						return true;
					}
					return false;
				}
				return false;
			}
			

		};
	}

	public  void stopDrones() {
		UnPostAllEvents();

		rmv.Post(rmv.new Arret(), rmv.getDurationToReach());
		rmv.UnPostAllEvents();
		BeforeDeactivating(rmv, false);
	}
	
	@Override
	public void onParentSet() {

	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		DroneInit = (EntityDroneInit) getInitParameters();
		
			rmv = (EntityMouvementSequenceur_DummyDrone) createChild(EntityMouvementSequenceur_DummyDrone.class, "monSequenceur", ((EntityDroneFeature) getFeatures()).getSeqFeature());
		rmv.initialize(DroneInit.getMvtSeqInitial());

	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {

	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation de Drone");
		rmv.activate();
		Post(new DetectArtifact());
		Post(new BatteryLife());
	}


	@ToRecord(name="Position")
	@Override
	public Point3D getPosition() {
		return rmv.getPosition(getCurrentLogicalDate());
	}

	@ToRecord(name="Vitesse")
	@Override
	public Point3D getVitesse() {
		return rmv.getVitesse(getCurrentLogicalDate());
	}

	@ToRecord(name="Acceleration")
	@Override
	public Point3D getAcceleration() {
		return rmv.getAcceleration(getCurrentLogicalDate());
	}

	@ToRecord(name="VitesseRotation")
	@Override
	public Point3D getVitesseRotationXYZ() {
		return rmv.getVitesseRotationXYZ(getCurrentLogicalDate());
	}

	@ToRecord(name="AccelerationRotation")
	@Override
	public Point3D getAccelerationRotationXYZ() {
		return rmv.getAccelerationRotationXYZ(getCurrentLogicalDate());
	}

	@ToRecord(name="Rotation")
	@Override
	public Point3D getRotationXYZ() {
		return rmv.getRotationXYZ(getCurrentLogicalDate());
	}


	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		rmv.deactivate();
	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		return DroneFeature.getCouleur();
	}

	@Override
	public double getRayon() {
		return DroneFeature.getRayon();
	}

	@Override
	public double getLongueur() {
		return DroneFeature.getTaille();
	}

	public class DetectArtifact extends SimEvent {

		@Override
		public void Process() {
			Logger.Information(Owner(), "Process DetectArtifact", "detection process");
			LogicalDateTime d = getCurrentLogicalDate();
			List<ISimObject> detectedArtifact = getEngine().requestSimObject(detectionArtifactRequest);
			for(ISimObject o: detectedArtifact){
				Bouee detectedBouee=(Bouee) o;
				System.out.print(DroneFeature.getId() + "\ndetected bouee" + detectedBouee.getName()+ "\t");
				System.out.print(detectedBouee.getPosition().toString()+ "\n");
				if (detectedBouee.getName().equals("Boite noire")) {
					System.out.println("Found black box");
//					BeforeDeactivating(rmv, false);
//					EntityDrone.new stopDrones();
					stopDrones();
//					rmv.Post(new Dive());
					return;
				}
				

			}
			Logger.Information(Owner(), "Process FinStaticPhase1", "Phase mouvement linéaire enclenché");
			Post(new DetectArtifact(), LogicalDuration.ofSeconds(15)); //pour avoir plus de donnees de test sur la "petite" zone;
																		// on repasserait aux 1 minute preconisees dans les dimensions reelles
		}
	}
	

	public class BatteryLife extends SimEvent {


			@Override
			public void Process() {
				double actuallBattery=DroneInit.getBatteryLife()-0.5;
				DroneInit.setBatteryLife(actuallBattery);
				double BatteryDying=5;// informer le bateau et fait la damande de changement de la batterie
				Logger.Information(Owner(), "Process BatteryLife", "Battery consumption");
				System.out.println("Battery Actual percentage = " + actuallBattery +"%");
				if(actuallBattery==BatteryDying){
					System.out.println("!!!!!!!!!!!!!!!!ATTENTION!!!!!!!!!!!!!!!!!!!!!\nBattery Died\n");
					BeforeDeactivating(rmv, false);
				}
			Post(new BatteryLife(), LogicalDuration.ofMinutes(1));
			}
	}
	
}
