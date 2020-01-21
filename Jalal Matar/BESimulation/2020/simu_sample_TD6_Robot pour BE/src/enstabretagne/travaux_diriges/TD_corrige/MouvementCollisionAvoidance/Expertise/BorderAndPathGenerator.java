package enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.Expertise;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DEdge;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DVertex;
import enstabretagne.base.logger.Logger;
import enstabretagne.monitor.implementation.XYZRotator2;
import enstabretagne.travaux_diriges.TD_corrige.MouvementCollisionAvoidance.SimEntity.Wall.Representation3D.IWall3D;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class BorderAndPathGenerator {

	
	//Algorithme utilisant la nappe de points générée plus bas, l'espace avec lequel la nappe a été définit
	//il génère les liens entre les points de la nappe directement adjacents qui sont en intervisibilité
	public static List<Point3DEdge> generateEdges(List<Point3DVertex> nape, double napeSpace,List<Bounds> murs) {
		List<Point3DEdge> edges = new ArrayList<>();

		for (Point3DVertex p : nape) {

			addNewEdgeIfNecessary(p, Rotate.X_AXIS.multiply(napeSpace), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.X_AXIS.multiply(-napeSpace), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(napeSpace), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(-napeSpace), edges, nape, napeSpace,murs);
			
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(-napeSpace).add(Rotate.X_AXIS.multiply(-napeSpace)), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(-napeSpace).add(Rotate.X_AXIS.multiply(napeSpace)), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(napeSpace).add(Rotate.X_AXIS.multiply(-napeSpace)), edges, nape, napeSpace,murs);
			addNewEdgeIfNecessary(p, Rotate.Y_AXIS.multiply(napeSpace).add(Rotate.X_AXIS.multiply(napeSpace)), edges, nape, napeSpace,murs);
			}

		return edges;
	}

	private static void addNewEdgeIfNecessary(Point3DVertex p, Point3D vector, List<Point3DEdge> edges,
			List<Point3DVertex> nape, double napeSpace,List<Bounds> murs) {
		Point3DVertex ptemp;
		Point3DEdge etemp;
		ptemp = new Point3DVertex(p.getPoint().add(vector));
		boolean isvisible = intervisibilityBetween(p.getPoint(), ptemp.getPoint(), murs);
		if (nape.contains(ptemp) && isvisible) {
			etemp = new Point3DEdge(p, ptemp);
			if (!edges.contains(etemp)) {
				edges.add(etemp);
				etemp = new Point3DEdge(ptemp, p);
			}
		}
	}

	//Algorithme prenant une liste de boites englobantes, prenant la boite englobante de ces boites englobantes
	//générére une nappe de points autour de ces murs sans passer dans les murs
	//notez que le premier argument n'est pas utilisé... c'est pour une version ultérieure de l'algo
	//le deuxième argument permet de définir la résolution de la nappe
	public static List<Point3DVertex> generateNapeOfPath(double distanceOfSecurity, double napeSpace,
			List<Bounds> murs) {

		Double minx = Double.NaN;
		Double miny = Double.NaN;
		Double maxx = Double.NaN;
		Double maxy = Double.NaN;
		for (Bounds b : murs) {
			if (minx.equals(Double.NaN)) {
				minx = b.getMinX();
				miny = b.getMinY();
				maxx = b.getMaxX();
				maxy = b.getMaxY();
			} else {
				if (minx > b.getMinX())
					minx = b.getMinX();
				if (miny > b.getMinY())
					miny = b.getMinY();
				if (maxx < b.getMaxX())
					maxx = b.getMaxX();
				if (maxy < b.getMaxY())
					maxy = b.getMaxY();
			}
		}

		List<Point3DVertex> pointsPourChemin = new ArrayList<>();

		Point3D startP = new Point3D(minx, miny, 0);
		Point3D p = startP;

		while (minx <= p.getX() && p.getX() <= maxx && miny <= p.getY() && p.getY() <= maxy) {
			Point3D temp_p = p;
			while (minx <= p.getX() && p.getX() <= maxx && miny <= p.getY() && p.getY() <= maxy) {

				boolean isNotIn = true;
				for (Bounds b : murs) {
					if (b.contains(p))
						isNotIn = false;
				}

				if (isNotIn)
					pointsPourChemin.add(new Point3DVertex(p));

				p = p.add(Rotate.X_AXIS.multiply(napeSpace));
			}
			p = temp_p.add(Rotate.Y_AXIS.multiply(napeSpace));
		}

		return pointsPourChemin;
	}
	
	//fonction qui renvoie les spheres des coins d'une boite englobante
	//ceci permet de visualiser en 2D les objets Bounds
	public static List<Sphere> getSpheresFromBound(Bounds b) {
		List<Sphere> ss = new ArrayList<>();
		Sphere s;
		PhongMaterial pm = new PhongMaterial(Color.BLUE);

		s = new Sphere(0.5);
		s.setMaterial(pm);
		s.setTranslateX(b.getMinX());
		s.setTranslateY(b.getMinY());
		ss.add(s);
		
		s = new Sphere(0.5);
		s.setMaterial(pm);
		s.setTranslateX(b.getMaxX());
		s.setTranslateY(b.getMinY());
		ss.add(s);

		s = new Sphere(0.5);
		s.setMaterial(pm);
		s.setTranslateX(b.getMaxX());
		s.setTranslateY(b.getMaxY());
		ss.add(s);

		s = new Sphere(0.5);
		s.setMaterial(pm);
		s.setTranslateX(b.getMinX());
		s.setTranslateY(b.getMaxY());
		ss.add(s);
		
		return ss;
	}
	
	public static List<Sphere> getSpheresFromBounds(List<Bounds> bs) {
		List<Sphere> ss = new ArrayList<>();
		for(Bounds b:bs) {
			ss.addAll(getSpheresFromBound(b));
		}
		return ss;
	}

	public static List<Point3DEdge> generatePaths(Point3DVertex newPoint, List<Point3DVertex> points,
			List<Bounds> nodes) {
		List<Point3DEdge> paths = new ArrayList<>();
		for (int i = 0; i < points.size() - 1; i++) {
			Point3DEdge edge = new Point3DEdge(points.get(i), newPoint);
			Point3DEdge edgeReturn = new Point3DEdge(newPoint, points.get(i));

			Cylinder c = BorderAndPathGenerator.generateCylinderBetween(points.get(i).getPoint(), newPoint.getPoint());

			if (!intersect2(c, nodes)) {
				paths.add(edge);
				paths.add(edgeReturn);
			}

		}

		return paths;
	}

	//Algorithme permettant de générer un chemin de points longeant les murs	
	public static List<Point3D> getBorder(IWall3D wall3D, double d, double detectsize) {
		List<Point3D> borderPoints = new ArrayList<>();
		List<Point3D> borderPointsSide1 = new ArrayList<>();
		List<Point3D> borderPointsSide2 = new ArrayList<>();
		double w = wall3D.getWidth() / 2;
		int m = wall3D.getWallShape().size() - 1;

		if (wall3D.getWallShape().size() == 1) {
			Point3D v1 = wall3D.getWallShape().get(0);

			Point3D p1 = Rotate.X_AXIS.multiply(-d - w).add(Rotate.Y_AXIS.multiply(-d));
			Point3D p2 = Rotate.X_AXIS.multiply(d + w).add(Rotate.Y_AXIS.multiply(-d));
			Point3D p3 = Rotate.X_AXIS.multiply(-d - w).add(Rotate.Y_AXIS.multiply(d));
			Point3D p4 = Rotate.X_AXIS.multiply(d + w).add(Rotate.Y_AXIS.multiply(d));

			borderPoints.add(v1.add(p1));
			borderPoints.add(v1.add(p2));
			borderPoints.add(v1.add(p3));
			borderPoints.add(v1.add(p4));

		} else if (wall3D.getWallShape().size() == 2) {
			Point3D v1 = wall3D.getWallShape().get(0);
			Point3D v2 = wall3D.getWallShape().get(1);

			Point3D v = v2.subtract(v1).normalize();

			Point3D n = v.crossProduct(Rotate.Z_AXIS).normalize();

			Point3D p1 = v1.add(n.multiply(-d - w).add(v.multiply(-d)));
			Point3D p2 = v1.add(n.multiply(d + w).add(v.multiply(-d)));
			Point3D p3 = v2.add(n.multiply(-d - w).add(v.multiply(d)));
			Point3D p4 = v2.add(n.multiply(d + w).add(v.multiply(d)));

			borderPoints.add(p1);
			borderPoints.add(p2);
			borderPoints.add(p3);
			borderPoints.add(p4);
		} else {
			for (int i = 0; i < wall3D.getWallShape().size(); i++) {
				if (i == 0) {
					Point3D v1 = wall3D.getWallShape().get(0);
					Point3D v2 = wall3D.getWallShape().get(1);

					Point3D v = v2.subtract(v1).normalize();

					Point3D n = v.crossProduct(Rotate.Z_AXIS).normalize();

					Point3D pf1 = v1.add(n.multiply(-d - w).add(v.multiply(-d)));
					Point3D pf2 = v1.add(n.multiply(d + w).add(v.multiply(-d)));

					borderPoints.add(pf1);
					borderPoints.add(pf2);

					borderPointsSide1.add(pf1);
					borderPointsSide2.add(pf2);
					Logger.Detail(null, "Border 0", "P0=" + pf1.toString());

				} else if (i == m) {
					Point3D v1 = wall3D.getWallShape().get(m - 1);
					Point3D v2 = wall3D.getWallShape().get(m);

					Point3D v = v2.subtract(v1).normalize();

					Point3D n = v.crossProduct(Rotate.Z_AXIS).normalize();

					Point3D pf1 = v2.add(n.multiply(-d - w).add(v.multiply(d)));
					Point3D pf2 = v2.add(n.multiply(d + w).add(v.multiply(d)));
					borderPoints.add(pf1);
					borderPoints.add(pf2);
					borderPointsSide1.add(pf1);
					borderPointsSide2.add(pf2);
//					Logger.Detail(null, "Border 0", "PF="+pf1.toString());

				} else {
					Point3D p1 = wall3D.getWallShape().get(i - 1);
					Point3D p2 = wall3D.getWallShape().get(i);
					Point3D p3 = wall3D.getWallShape().get(i + 1);

					Point3D v1 = p2.subtract(p1).normalize();
					Point3D v2 = p3.subtract(p2).normalize();
					Point3D v = v2.add(v1.multiply(-1)).normalize();

					double norm = v1.crossProduct(v2).dotProduct(Rotate.Z_AXIS);

					double a = v2.angle(v);
					double dis;
					if (a == 0) {
						dis = d + w;
					} else {
						dis = (d + w) / Math.sin(a * Math.PI / 180) * Math.signum(norm);
					}
					Point3D pf1 = p2.add(v.multiply(dis));
					Point3D pf2 = p2.add(v.multiply(-dis));
					borderPointsSide1.add(pf1);
					borderPointsSide2.add(pf2);
					borderPoints.add(pf1);
					borderPoints.add(pf2);
				}
			}
		}

		List<Point3D> intermediatePoints = new ArrayList<>();
		if (borderPointsSide1.size() > 0) {
			for (int i = 0; i < borderPointsSide1.size() - 1; i++) {
				createBetween(borderPointsSide1.get(i), borderPointsSide1.get(i + 1), detectsize, intermediatePoints);
			}
			for (int i = 0; i < borderPointsSide2.size() - 1; i++) {
				createBetween(borderPointsSide2.get(i), borderPointsSide2.get(i + 1), detectsize, intermediatePoints);
			}
			createBetween(borderPointsSide1.get(0), borderPointsSide2.get(0), detectsize, intermediatePoints);
			createBetween(borderPointsSide1.get(borderPointsSide1.size() - 1),
					borderPointsSide2.get(borderPointsSide2.size() - 1), detectsize, intermediatePoints);
		}
		borderPoints.addAll(intermediatePoints);

		return borderPoints;
	}

	public static void createBetween(Point3D p1, Point3D p2, double espace, List<Point3D> l) {
		if (espace <= 0)
			return;
		Point3D v = p2.subtract(p1);
		int n = (int) (v.magnitude() / espace);
		for (int i = 1; i < n; i++) {
			l.add(p1.add(v.normalize().multiply(espace * i)));
		}
	}

	//Algorithme prenant deux points de même altitude et génère un cylindre les reliant
	//ceci permet de visualiser une ligne entre deux points en 3D
	//la ligne 2D ne s'affiche pas bien en 3D en JFX8
	public static Cylinder generateCylinderBetween(Point3D p1, Point3D p2) {
		// Logger.Detail(null, "generateCylinder", "p1="+p1.toString()+"
		// p2="+p2.toString());
		Point3D v = p2.subtract(p1);

		Cylinder c = new Cylinder();
		c.setRadius(0.05);
		c.setHeight(v.magnitude());

		Rotate rc = new Rotate();
		rc.setAxis(Rotate.Z_AXIS);
		rc.setAngle(-90);
		c.getTransforms().add(rc);

		Affine a = XYZRotator2.getTransformByDirection(v);
		c.getTransforms().add(a);

		Translate t = new Translate();
		t.setY((c.getHeight() / 2));
		c.getTransforms().add(t);

		c.setTranslateX(p1.getX());
		c.setTranslateY(p1.getY());
		c.setTranslateZ(p1.getZ());

		return c;
	}

	//Permet de vérifier s'il y a intersection entre un cylindre et une liste de boites englobantes
	public static boolean intersect2(Cylinder l, List<Bounds> nodes) {

		for (Bounds n : nodes) {

			boolean intersect1 = l.getBoundsInParent().intersects(n);
			if (intersect1) {
				return true;
			}
		}
		return false;

	}

	//Permet de vérifier l'intervisibilité entre deux points selon un ensemble de boites englobantes
	public static boolean intervisibilityBetween(Point3D p1, Point3D p2, List<Bounds> walls) {
		Cylinder c = generateCylinderBetween(p1, p2);
		return !intersect2(c, walls);
	}
}
