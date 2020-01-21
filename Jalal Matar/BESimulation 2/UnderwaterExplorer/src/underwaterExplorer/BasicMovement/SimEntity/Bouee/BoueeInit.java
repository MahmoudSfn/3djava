package underwaterExplorer.BasicMovement.SimEntity.Bouee;

import java.util.HashMap;

import enstabretagne.simulation.components.data.SimInitParameters;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import underwaterExplorer.BasicMovement.SimEntity.MouvementSequenceur.EntityMouvementSequenceurInit;

public class BoueeInit extends SimInitParameters {
	private String name;
	private int type;
	private Color color;
	private Point3D position;
	private boolean detected;


	private EntityMouvementSequenceurInit mvtSeqInit;


	public BoueeInit(String nom,EntityMouvementSequenceurInit mvtSeqInit,Color color,int type, Point3D p)
	{
		this.name = nom;
		this.type=type;
		this.color = color;
		this.mvtSeqInit = mvtSeqInit;
		this.position=p;
		this.detected=false;

	}

	public EntityMouvementSequenceurInit getMvtSeqInit() {
		return mvtSeqInit;
	}

	public boolean getDetected() {
		return detected;
	}

	public void setDetected(boolean detected) {
		this.detected = detected;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public Point3D getPosition() {
		return position;
	}




}
