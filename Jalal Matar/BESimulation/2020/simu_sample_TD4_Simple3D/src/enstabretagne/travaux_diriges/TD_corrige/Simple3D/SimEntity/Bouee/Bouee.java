package enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee;

import enstabretagne.base.logger.Logger;
import enstabretagne.base.logger.ToRecord;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.monitor.implementation.MovableState;
import enstabretagne.monitor.interfaces.IMovable;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.data.SimFeatures;
import enstabretagne.simulation.components.data.SimInitParameters;
import enstabretagne.simulation.components.implementation.SimEntity;
import enstabretagne.travaux_diriges.TD_corrige.Simple3D.SimEntity.Bouee.Representation3D.IBoueeRepresentation3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

@ToRecord(name="Bouee")
public class Bouee extends SimEntity implements IMovable,IBoueeRepresentation3D{
	
	BoueeInit bIni;
	BoueeFeatures bFeat;
	public Bouee(String name, SimFeatures features) {
		super(name, features);
		bFeat = (BoueeFeatures) features;
	}

	@Override
	public void onParentSet() {
		
	}

	@Override
	protected void initializeSimEntity(SimInitParameters init) {
		bIni = (BoueeInit) getInitParameters();
		String na = bIni.getName();
		
	}

	@Override
	protected void BeforeActivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Detail(this, "AfterActivate", "Activation de la bouée %s","test");
	}
	

	@ToRecord(name="Position")
	@Override
	public Point3D getPosition() {
		return bIni.getPosInit();
	}

	@ToRecord(name="Vitesse")
	@Override
	public Point3D getVitesse() {
		return Point3D.ZERO;
	}

	@ToRecord(name="Acceleration")
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
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		Logger.Information(this, "BeforeDeactivate", "Sur le point de se désactiver");		
	}

	@Override
	protected void AfterDeactivated(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterDeactivated", "Déactivation");		
	}

	@Override
	protected void AfterTerminated(IEntity sender, boolean restart) {
		Logger.Information(this, "AfterTerminated", "FIN de la bouee");				
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

}
