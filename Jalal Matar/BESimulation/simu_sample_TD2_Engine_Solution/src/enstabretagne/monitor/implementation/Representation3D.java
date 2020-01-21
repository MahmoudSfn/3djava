package enstabretagne.monitor.implementation;

import enstabretagne.monitor.IDrawAction;
import enstabretagne.monitor.ObjTo3DMappingSettings;

public abstract class Representation3D implements IDrawAction {

	ObjTo3DMappingSettings general3DSettings;
	
//	XYZRotator rotator;

	public Representation3D(ObjTo3DMappingSettings settings) {
		general3DSettings = settings;
//		rotator = new XYZRotator();
	}
	
	@Override
	public ObjTo3DMappingSettings getGeneral3DSettings() {
		return general3DSettings;
	}

//	protected void addRotate(Group g,Point3D rotationXYZ)
//	{
////		rotator.rotate(g, rotationXYZ);
//	}

}
