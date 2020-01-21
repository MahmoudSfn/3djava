package de.vogella.algorithms.dijkstra.model.jfxmodel;

import java.util.ArrayList;
import java.util.List;

import de.vogella.algorithms.dijkstra.model.IVertex;
import de.vogella.algorithms.dijkstra.model.Vertex;
import javafx.geometry.Point3D;
import javafx.scene.shape.Cylinder;

public class Point3DVertex implements IVertex{


	Point3D point;
	public Point3DVertex(Point3D p) {
		point = p;
	}
	
	public Point3DVertex(double x,double y,double z) {
		point = new Point3D(x, y, z);
	}

	@Override
	public String getId() {
		return point.toString();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Point3D getPoint() {
		return point;
	}

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
	    return result;
	  }
	  
	  
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Point3DVertex other = (Point3DVertex) obj;
	    if (getId() == null) {
	      if (other.getId() != null)
	        return false;
	    } else if (!getId().equals(other.getId()))
	      return false;
	    return true;
	  }

	  @Override
	  public String toString() {
	    return point.toString();
	  }
}
