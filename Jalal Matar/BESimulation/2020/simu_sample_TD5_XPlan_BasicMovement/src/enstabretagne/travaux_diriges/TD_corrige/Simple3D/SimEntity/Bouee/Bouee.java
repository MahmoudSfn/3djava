package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import java.util.ArrayList;
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
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Representation3D.IBoueeRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

@ToRecord(name = "Bouee")
public class Bouee extends SimEntity implements IMovable, IBoueeRepresentation3D {

	//Cast des init et des features génériques
	BoueeInit bIni;
	BoueeFeatures bFeat;

	Point3D maPosition;
	Point3D dir;//Direction courante du mouvement de la bouee
	double speed;//vitesse courante de la bouee
	
	@Override
	public int getType() {
		return ((BoueeFeatures) getFeatures()).getType();
	}


	@ToRecord(name = "Temps de détection")
	public double getElapsedTime() {
		return getCurrentLogicalDate().soustract(start).DoubleValue();
	}
	
	@ToRecord(name = "Position")
	@Override
	public Point3D getPosition() {
		return maPosition;
	}

	@ToRecord(name = "Vitesse")
	@Override
	public Point3D getVitesse() {
		return dir.multiply(speed);
	}

	@ToRecord(name = "Acceleration")
	@Override
	public Point3D getAcceleration() {
		return Point3D.ZERO;
	}

	@Override
	public Color getColor() {
		return bIni.getColor();
	}

	@Override
	public double getSize() {
		return bFeat.getTaille();
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
	public Point3D getRotationXYZ() {
		return Point3D.ZERO;
	}

	
	LogicalDateTime start;//on enregistre la date de début afin de pouvoir mesurer à la fin le temps de détection


	public Bouee(String name, SimFeatures features) {
		super(name, features);
		bFeat = (BoueeFeatures) features;

		speed = bFeat.getVitesseMax();

	}


	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		bIni = (BoueeInit) getInitParameters();
		maPosition = bIni.getPosInit();

		computeDir();//on commence tout de suite à calculer la direction à prendre pour le mouvement

	}

	//calcul aléatoire de la direction d'orientation de la bouee
	protected void computeDir() {

		double x = RandomGenerator().nextDouble();
		double y = RandomGenerator().nextDouble();
		double z = 0;
		dir = new Point3D(x - 0.5, y - 0.5, z);
		dir = dir.normalize();
	}


	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation de la bouée %s", "test");
		start = getCurrentLogicalDate();
		if (bFeat.getType() == 1) {
			Post(new Mvt());// événement déclencheur du mouvement
			Post(new Detect());// événement déclencheur de la détection
		}
	}


	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		Logger.Information(this, "BeforeDeactivate", "Sur le point de se désactiver");

	}


	//Evenement permettant régulièrement de chercher la bouee noire et vérifier qu'elle est à distance
	class Detect extends SimEvent {
		
		//permet d'extraire une liste de bouee d'un certain type et différentes de moi-même
		//ici j'ai décidé que les bouees noire n'étaient que du type 0
		public List<ISimObject> listerAutresBouees(int type) {
			List<ISimObject> res;
			res = getEngine().requestSimObject(new SimObjectRequest() {

				@Override
				public boolean filter(ISimObject o) {
					if (o == Owner())
						return false;
					if (o instanceof Bouee) {

						Bouee b = (Bouee) o;

						return (b.getType() == type);
					} else
						return false;
				}
			});
			return res;
		}

		public List<ISimObject> getDetections(List<ISimObject> bouees) {
			List<ISimObject> detect = new ArrayList<>();
			Bouee myBouee = (Bouee) Owner();
			
			for (ISimObject b : bouees) {
				Bouee bouee = (Bouee) b;

				if (bouee.getColor().equals(Color.BLACK)) {//on cherche les bouees noires
					double d = bouee.getPosition().distance(myBouee.getPosition());
					
					//on cherche les bouees noires à portée et si elle est à portée on considère qu'elle a été détectée
					if (bouee.getPosition().distance(myBouee.getPosition()) < myBouee.bFeat.getDistanceOfDetection())
						detect.add(bouee);
				}
			}
			return detect;

		}

		
		public void endUp(List<ISimObject> objs) {
			for(ISimObject obj:objs)
				((Bouee) obj).EndsEveryThing();
			EndsEveryThing();
		}

		@Override
		public void Process() {
			//on cherche les bouees noires détectées
			List<ISimObject> bouee0 = getDetections(listerAutresBouees(0));
			
			if (bouee0.size() > 0) {//si on en a détectée au moins une c'est la fin de la simulation
				Logger.Data(this.Owner());
				endUp(listerAutresBouees(1));
				endUp(listerAutresBouees(0));
			}
			Post(this, LogicalDuration.ofSeconds(1));//on scanne toutes les secondes (codé en dur ici aurait dû être mis en feature...)
		}
	}

	//classe permettant le mouvement de la bouee
	class Mvt extends SimEvent {

		@Override
		public void Process() {
			double dt = 0.1;
			//on attend de voir si la bouee sort d'un rectangle de 50 de côté
			if (maPosition.getX() > 50 || maPosition.getX() < -50 || maPosition.getY() > 50
					|| maPosition.getY() < -50) {
				dir = dir.multiply(-1);//dans ce cas on prend la direction inverse
			} else {//si on n'est pas en dehors du rectangle on change de direction de manière aléatoire (1% du temps)
				double n = RandomGenerator().nextDouble();
				if (n < 0.01)
					computeDir();

			}
			maPosition = maPosition.add(getVitesse().multiply(dt));

			Owner().Post(this, LogicalDuration.ofSeconds(dt));
		}

	}
	
	public void EndsEveryThing() {
		deactivate();//on se désactive sur ordre de la première bouee ayant trouvé la bouee noire.
	}


	
	@Override
	public void onParentSet() {

	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterDeactivated", "Déactivation");
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		Logger.Information(this, "AfterTerminated", "FIN de la bouee");
	}

}
