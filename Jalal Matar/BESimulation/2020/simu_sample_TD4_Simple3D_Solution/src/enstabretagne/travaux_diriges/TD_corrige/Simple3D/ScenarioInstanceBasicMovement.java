package enstabretagne.travaux_diriges.TD_corrige.Simple3D;

import java.util.HashMap;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.core.IScenario;
import enstabretagne.simulation.core.IScenarioInstance;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenario;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.Scenarios.BasicMvtScenarioFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.BoueeInit;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class ScenarioInstanceBasicMovement implements IScenarioInstance {

	@Override
	public IScenario getScenarioInstance(long seed) {
		BasicMvtScenarioFeatures bsf = new BasicMvtScenarioFeatures("BSF");
		
		//Cr�ation de bouees
		
		int i=0;
		MoreRandom mr = new MoreRandom(seed);
		
		int N=5;
		double size=20;

		for(i=0;i<N;i++) {
			double x= mr.nextDouble() * size;
			double y = mr.nextDouble() * size;
			
			//bsf.getBouees().put(new BoueeFeatures("B"+i,5,1,3.0,1), new BoueeInit("B"+i,new Point3D(x-size/2,y-size/2,0),Color.RED));
			bsf.getBouees().put(new BoueeFeatures("C"+i,5,1,3.0,1), new BoueeInit("C"+i,new Point3D(x-size/2,y-size/2,0),Color.BLACK));
		}

		//Zone destin�e � des tests
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,5), new BoueeInit("B"+2,new Point3D(30,10,20),Color.BROWN));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,4), new BoueeInit("B"+i,new Point3D(30,10,20),Color.RED));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,3), new BoueeInit("B"+3,new Point3D(30,10,20),Color.BLACK));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,2), new BoueeInit("B"+4,new Point3D(30,10,20),Color.BEIGE));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,1), new BoueeInit("B"+5,new Point3D(30,10,20),Color.BISQUE));

//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("O",new Point3D(0,0,0),Color.BLACK));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("X",new Point3D(10,0,0),Color.BLUEVIOLET));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Y",new Point3D(0,10,0),Color.GREEN));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Z",new Point3D(0,0,10),Color.BLUE));
		
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Z",new Point3D(10,10,10),Color.DARKGOLDENROD));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Z",new Point3D(10,10,-10),Color.RED));
//		bsf.getBouees().put(new BoueeFeatures("B1"+i,0,1,3.0,0), new BoueeInit("Z",new Point3D(10,0,10),Color.BROWN));

		LogicalDateTime start = new LogicalDateTime("05/12/2018 06:00");
		LogicalDateTime end = start.add(LogicalDuration.ofMinutes(2));
		BasicMvtScenario bms = new BasicMvtScenario(new ScenarioId("S1"), bsf, start, end);
		
		return bms;
	}

}
