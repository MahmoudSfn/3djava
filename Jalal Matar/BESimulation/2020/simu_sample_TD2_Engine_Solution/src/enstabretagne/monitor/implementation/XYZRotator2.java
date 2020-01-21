package enstabretagne.monitor.implementation;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class XYZRotator2 {
	public static void rotate(Node n,Point3D xyz) {
		Affine a = new Affine();
		
		Rotate rz = new Rotate();
		rz.setAxis(Rotate.Z_AXIS);
		rz.setAngle(xyz.getZ());
				
		Rotate ry = new Rotate();
		ry.setAxis(rz.transform(Rotate.Y_AXIS));
//		ry.setAxis((Rotate.Y_AXIS));
		ry.setAngle(xyz.getY());

		a.prepend(rz);		
		a.prepend(ry);
		
		System.out.println("z="+a.transform(Rotate.Z_AXIS));
		System.out.println("y="+a.transform(Rotate.Y_AXIS));
		
		n.getTransforms().setAll(rz,ry);
		
	}
	
	public static Point3D getDirectionFromAngle(Point3D xyz) {
		Affine a = getTransformByAngle(xyz);
		return a.transform(Rotate.X_AXIS);
	}
	
	public static Affine getTransformByAngle(Point3D xyz) {
		Affine a = new Affine();
		
		Rotate rz = new Rotate();
		rz.setAxis(Rotate.Z_AXIS);
		rz.setAngle(xyz.getZ());
				
		Rotate ry = new Rotate();
		ry.setAxis(rz.transform(Rotate.Y_AXIS));
		ry.setAngle(xyz.getY());

		a.prepend(rz);		
		a.prepend(ry);
		
		
		return a;
	}

	public static Affine getTransformByDirection(Point3D xyz) {
		
		xyz = computeRotationXYZ(xyz);
		return getTransformByAngle(xyz);
	}
	

	public static Point3D computeRotationXYZ(Point3D dir) {
		Point3D zHor;
		Rotate r = new Rotate();
		double angleZ, angleY;
		angleY=0;
		angleZ=0;
		zHor = new Point3D(dir.getX(), dir.getY(), 0);

		if (!zHor.equals(Point3D.ZERO)) {
			angleZ = Rotate.X_AXIS.angle(zHor);
			double sgn = Math.signum((Rotate.X_AXIS.crossProduct(zHor)).dotProduct(Rotate.Z_AXIS));
			if(sgn==0) sgn=1;
			angleZ = angleZ * sgn;
		}
		else
			angleZ=0;

		r.setAngle(angleZ);
		r.setAxis(Rotate.Z_AXIS);
		Point3D rotatedYAxis = r.transform(Rotate.Y_AXIS);
		
		if (!dir.equals(Point3D.ZERO)) {
			if(zHor.equals(Point3D.ZERO)) {
				if(Math.signum(zHor.dotProduct(dir))>0) angleY=0;
				else angleY = 180;
						
			}
			else {
				angleY = dir.angle(zHor)
						* Math.signum((zHor.crossProduct(dir)).dotProduct(rotatedYAxis));
			}
		}

		Point3D rotationXYZCible = new Point3D(0, angleY, angleZ);
		return rotationXYZCible;

	}
}
