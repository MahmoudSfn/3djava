package underwaterExplorer.BasicMovement;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.jscience.mathematics.function.Variable.Local;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.monitor.implementation.MovableState;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.BoueeFeatures;
import underwaterExplorer.BasicMovement.SimEntity.Bouee.BoueeInit;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneFeature;
import underwaterExplorer.BasicMovement.SimEntity.Drone.EntityDroneInit;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurFeature;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;
import underwaterExplorer.BasicMovement.SimEntity.Navire.EntityNavireFeature;
import underwaterExplorer.BasicMovement.SimEntity.Navire.EntityNavireInit;
import underwaterExplorer.BasicMovement.SimEntity.Ocean.EntityOceanFeature;
import underwaterExplorer.BasicMovement.SimEntity.Ocean.EntityOceanInit;
import underwaterExplorer.TD_corrige.BasicMovement.Scenarios.BasicMvtScenario;
import underwaterExplorer.TD_corrige.BasicMovement.Scenarios.BasicMvtScenarioFeatures;

public class ScenarioInstanceBasicMovement implements IScenarioInstance {

	@Override
	public IScenario getScenarioInstance() {
		BasicMvtScenarioFeatures bsf = new BasicMvtScenarioFeatures("BSF");

		//Création du navire et des points de passage
		HashMap<String,Point3D> positionsCles = new HashMap<String, Point3D>();
		positionsCles.put("start", new Point3D(0,0,0));
		positionsCles.put("PointCible1", new Point3D(10,10,0));
		positionsCles.put("PointCible2", new Point3D(30,-10,0));
		positionsCles.put("PointCible3", new Point3D(20,0,0));
		positionsCles.put("PointDirection", new Point3D(20,20,0));
		positionsCles.put("PointSousEau", new Point3D(0,0,-10));
		positionsCles.put("ObservationMine", new Point3D(20,20,-20));


		MovableState mst = new MovableState(new Point3D(0,0,0), new Point3D(1,1,0), Point3D.ZERO, new Point3D(0,0,45.0), new Point3D(10,5,0.0), Point3D.ZERO);
		EntityMouvementSequenceurInit msi = new EntityMouvementSequenceurInit("MSI", mst, 10, 100,2,8, positionsCles);
		EntityMouvementSequenceurFeature feat = new EntityMouvementSequenceurFeature("MSF");
		bsf.getNavires().put(new EntityNavireFeature("NavireF",5,3,Color.BLACK,feat), new EntityNavireInit("Navire Observation", msi));



		//Création de bouees
		int i=0;
		int N= ThreadLocalRandom.current().nextInt(30,40);
		positionsCles = new HashMap<String, Point3D>();
		for(i=0;i<N+1;i++)
		{
			MovableState mstArtefacts;
			EntityMouvementSequenceurInit msiArtefacts;

			int r=100;
			double x= ThreadLocalRandom.current().nextDouble(-r,r), y= ThreadLocalRandom.current().nextDouble(-r,r), z= -ThreadLocalRandom.current().nextDouble(0,30);
			Color color = Color.BLACK;
			int typeArtefact = 1;
			String artefactName = "B"+i;

			if (i<=0.6*N) {
				color = Color.RED;
				typeArtefact = 1;
			}
			else if (i>0.6*N && i<=0.9*N) {
				color = Color.YELLOW;
				typeArtefact = 2;
			}
			else if (i>0.9*N && i<N) {
				color = Color.GREEN;
				typeArtefact = 3;
			}
			else if (i==N) { //cas particulier de la boite noire
				color = Color.BLACK;
				typeArtefact = 4;
				artefactName = "Boite noire";
				z= -ThreadLocalRandom.current().nextDouble(10,30); //boite noire a au moins 1 km de profondeur
			}

			positionsCles.put(artefactName, new Point3D(x, y, z));
			mstArtefacts = new MovableState(new Point3D(x,y,z), Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO);
			msiArtefacts= new EntityMouvementSequenceurInit("MSI", mstArtefacts, 0, 0,0,0, positionsCles);
			bsf.getBouees().put(new BoueeFeatures("B1",5,1,3.0), new BoueeInit(artefactName,msiArtefacts,color, typeArtefact, new Point3D(x, y, z)));
		}


		//Création de l'ocean
		positionsCles = new HashMap<String, Point3D>();
		MovableState mstOcean = new MovableState(Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO, Point3D.ZERO);
		EntityMouvementSequenceurInit msiOcean = new EntityMouvementSequenceurInit("MSIOCEAN", mstOcean, 0, 0,0,0, positionsCles);
		bsf.getOcean().put(new EntityOceanFeature("O1"), new EntityOceanInit("Atlantique", msiOcean));


		LogicalDateTime start = new LogicalDateTime("05/12/2017 06:00");
		LogicalDateTime end = start.add(LogicalDuration.ofMinutes(2));
		BasicMvtScenario bms = new BasicMvtScenario(new ScenarioId("S1"), bsf, start, end);


//		Point3D pos;

//		        	pos = mstDrone.getPosition();
//		            System.out.println("\n drone pos : " + pos.toString());
//
//
//		HashMap<BoueeFeatures, BoueeInit> test = new HashMap<>();
//		test = bsf.getBouees();
//		for(BoueeFeatures b: test.keySet()){
//			String name = test.get(b).getName();
//			System.out.print("\nBo name is   "+name);
//			System.out.print("\tBo position is   "+test.get(b).getPosition().toString());

//		}


		return bms;
	}


}
