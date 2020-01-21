package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall;

import enstabretagne.simulation.components.data.SimFeatures;
import javafx.scene.paint.Color;

public class WallFeatures extends SimFeatures{
	public WallFeatures(String id,Color couleur, int type, double width, double height) {
		super(id);
		this.couleur = couleur;
		this.type = type;
		this.width = width;
		this.height = height;	
	}
	
	private Color couleur;
	private int type;
	private double width;
	private double height;

	

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Color getCouleur() {
		return couleur;
	}
	
	public int getType()
	{
		return type;
	}

	
}
