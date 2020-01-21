package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.scenarios;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.RobotFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Robot.RobotInit;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.WallFeatures;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.WallInit;

public class ScenMvtCollisionAvoidanceFeatures  extends SimFeatures{

	private HashMap<RobotInit, RobotFeatures> robots;
	
	public HashMap<RobotInit, RobotFeatures>  getRobots() {
		return robots;
	}
	private HashMap<WallInit,WallFeatures> walls;
	public HashMap<WallInit, WallFeatures> getWalls() {
		return walls;
	}
	
	public ScenMvtCollisionAvoidanceFeatures(String id) {
		super(id);
		robots = new HashMap<>();
		walls = new HashMap<>();
	}	

}
