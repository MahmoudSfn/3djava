package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.scenarios;

import java.util.Map;
import java.util.Map.Entry;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.simulation.components.implementation.SimScenario;
import enstabretagne.simulation.core.implementation.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.Robot;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.RobotFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.RobotInit;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Wall;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.WallFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.WallInit;

public class ScenMvtCollisionAvoidance extends SimScenario{
	public ScenMvtCollisionAvoidance(ScenarioId id, SimFeatures features, LogicalDateTime start, LogicalDateTime end) {
		super(id, features, start, end);
		
	}
	
	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		super.initializeSimEntity(init);
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		super.AfterActivate(sender, starting);
		
		ScenMvtCollisionAvoidanceFeatures feature = (ScenMvtCollisionAvoidanceFeatures) getFeatures();
		
		
		//Important: ordre d'initialisation. Initialiser d'abord l'environnement 
		//car sa complexité peut nécessiter des traitements préalables à l'initialisation
		//Par exemple: génération d'un pathfinding
		Logger.Detail(this, "afteractivate", "taille liste bouees = %s", feature.getRobots().size());
		for(Entry<WallInit, WallFeatures> e : feature.getWalls().entrySet())
		{
			Logger.Detail(this, "afteractivate", "bouee à créer = %s , %s", e.getValue(),e.getKey());
			Post(new WallArrival(e.getKey(),e.getValue()));
		}

		for(Map.Entry<RobotInit,RobotFeatures > e : feature.getRobots().entrySet())
		{
			Logger.Detail(this, "afteractivate", "bouee à créer = %s , %s", e.getValue(),e.getKey());
			Post(new RobotInitialize(e.getKey(),e.getValue()));
		}

	}
	
	class RobotInitialize extends SimEvent
	{
		private RobotInit ri;
		private RobotFeatures rf;
		public RobotInit getRi() {
			return ri;
		}
		public RobotFeatures getRf() {
			return rf;
		}
		public RobotInitialize(RobotInit ri, RobotFeatures rf) {
			super();
			this.ri = ri;
			this.rf = rf;
		}
		
		
		@Override
		public void Process() {
			Logger.Detail(this, "RobotInitialize.Process", "");
			SimEntity b = createChild(Robot.class, ri.getName() , rf);
			b.initialize(getRi());
			Post(new RobotActivate((Robot) b));
		}
	}

	class RobotActivate extends SimEvent
	{
		Robot r;
		public RobotActivate(Robot r) {
			super();
			this.r=r;
		}
		
		
		@Override
		public void Process() {
			Logger.Detail(this, "RobotActivate.Process", "");
			r.activate();
		}
	}

	class WallArrival extends SimEvent
	{
		private WallInit wi;
		private WallFeatures wf;
		public WallInit getWi() {
			return wi;
		}
		public WallFeatures getWf() {
			return wf;
		}
		public WallArrival(WallInit wi, WallFeatures wf) {
			super();
			this.wi = wi;
			this.wf = wf;
		}
		
		
		@Override
		public void Process() {
			Logger.Detail(this, "RobotArrival.Process", "");
			SimEntity b = createChild(Wall.class, wi.getName() , wf);
			b.initialize(getWi());
			b.activate();
		}
	}
}
