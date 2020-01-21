package underwaterExplorer.BasicMovement.SimEntity.Navire;

import java.util.HashMap;
import java.util.Map;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.monitor.implementation.MovableState;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.core.implementation.SimEvent;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDrone;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneFeature;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneInit;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceur;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurFeature;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceur_Exemple;
import underwaterExplorer.BasicMovement.SimEntity.Navire.Representation3D.EntityNavire3DRepresentationInterface;

@ToRecord(name="Navire")
public class EntityNavire extends SimEntity implements IMovable,EntityNavire3DRepresentationInterface{
	
	private EntityMouvementSequenceur rmv;
	private EntityNavireInit NavireInit;
	private EntityNavireFeature NavireFeature;

	private HashMap<EntityDroneFeature,EntityDroneInit> drones =new HashMap<>();
	
	public EntityNavire(String name, SimFeatures features) {
		super(name, features);
		NavireFeature = (EntityNavireFeature) features;
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		NavireInit = (EntityNavireInit) getInitParameters();

		rmv = (EntityMouvementSequenceur_Exemple) createChild(EntityMouvementSequenceur_Exemple.class, "monSequenceur", ((EntityNavireFeature) getFeatures()).getSeqFeature());
		rmv.initialize(NavireInit.getMvtSeqInitial());
	
	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation de Navire");
		rmv.activate();
		HashMap<String,Point3D> positionsCles = new HashMap<String, Point3D>();
		EntityMouvementSequenceurFeature feat = new EntityMouvementSequenceurFeature("MSF");
		
		int detectionRadius = 50; // omni-direction sonar detection radius
		MovableState mstDrone = new MovableState(new Point3D(0,0,-10), new Point3D(1,1,0), Point3D.ZERO, new Point3D(0,0,45.0), new Point3D(10,5,0.0), Point3D.ZERO);
		EntityMouvementSequenceurInit msiDrone = new EntityMouvementSequenceurInit("MSI", mstDrone, 5, 50,2,8, positionsCles);
		drones.put(new EntityDroneFeature("Drone1",5,3,Color.ORANGE,0,feat), new EntityDroneInit("ADrone 1", msiDrone, detectionRadius));

//		MovableState mstDrone = new MovableState(new Point3D(0,0,-10), new Point3D(1,1,0), Point3D.ZERO, new Point3D(0,0,45.0), new Point3D(10,5,0.0), Point3D.ZERO);
		drones.put(new EntityDroneFeature("Drone2",5,3,Color.BLACK,1,feat), new EntityDroneInit("ADrone 2", msiDrone, detectionRadius));

		for(Map.Entry<EntityDroneFeature, EntityDroneInit> e : drones.entrySet())
		{
			Logger.Detail(this, "afteractivate", "drone à créer = %s , %s", e.getValue(),e.getKey());
			Post(new DroneArrival(e.getValue(),e.getKey()));
		}
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
		return NavireFeature.getCouleur();
	}

	@Override
	public double getRayon() {
		return NavireFeature.getRayon();
	}

	@Override
	public double getLongueur() {
		return NavireFeature.getTaille();
	}
	
	class DroneArrival extends SimEvent
	{
		private EntityDroneInit i;
		private EntityDroneFeature f;
		
		public EntityDroneInit getI() {
			return i;
		}
		
		public EntityDroneFeature getF() {
			return f;
		}
		
		
		public DroneArrival(EntityDroneInit i, EntityDroneFeature f) {
			this.i=i;
			this.f=f;
		}

		@Override
		public void Process() {
			Logger.Detail(this, "DroneArrival.Process", "Création du drone" + i);
			EntityDrone b = (EntityDrone) createChild(EntityDrone.class, i.getName() , f);
			b.initialize(getI());
			b.activate();
		}
		
	}
	


	public HashMap<EntityDroneFeature,EntityDroneInit> getDrones() {
		return drones;
	}

	public void setDrones(HashMap<EntityDroneFeature,EntityDroneInit> drones) {
		this.drones = drones;
	}

}
