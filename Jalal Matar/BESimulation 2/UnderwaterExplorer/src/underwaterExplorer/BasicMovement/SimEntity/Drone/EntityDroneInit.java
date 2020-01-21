package underwaterExplorer.BasicMovement.SimEntity.Drone;

import enstabretagne.simulation.components.data.SimInitParameters;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;

public class EntityDroneInit extends SimInitParameters {
	private EntityMouvementSequenceurInit mvtSeqIni;
	private String name;
	private int detectionRadius;
	private double batteryLife;

	public EntityDroneInit(String nom,EntityMouvementSequenceurInit mvtSeqIni, int detectionRadius)//, double batteryLife)
	{
		this.mvtSeqIni = mvtSeqIni;
		this.name = nom;
		this.detectionRadius = detectionRadius;
		this.batteryLife = 100;
	}

	public String getName() {
		return name;
	}

	public EntityMouvementSequenceurInit getMvtSeqInitial() {
		return  mvtSeqIni;
	}

	public int getDetectionRadius(){
		return detectionRadius;
	}

	public double getBatteryLife() {
		return batteryLife;
	}

	public void setBatteryLife(double batteryLife) {
		this.batteryLife = batteryLife;
	}

}
