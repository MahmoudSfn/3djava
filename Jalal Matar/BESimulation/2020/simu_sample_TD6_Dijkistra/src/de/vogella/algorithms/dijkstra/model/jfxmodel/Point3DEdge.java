package de.vogella.algorithms.dijkstra.model.jfxmodel;

import java.util.ArrayList;
import java.util.List;

import de.vogella.algorithms.dijkstra.model.IEdge;
import de.vogella.algorithms.dijkstra.model.IVertex;
import javafx.scene.shape.Cylinder;

public class Point3DEdge implements IEdge{
	private final Point3DVertex source;
	private final Point3DVertex target;
	private final double d;
	
	public Point3DEdge(Point3DVertex source,Point3DVertex target) {
		this.source=source;
		this.target=target;
		d = source.getPoint().subtract(target.getPoint()).magnitude();
	}

	@Override
	public String getId() {
		return source.getId() +">>"+target.getId();
	}

	@Override
	public IVertex getDestination() {
		return target;
	}

	@Override
	public IVertex getSource() {
		return source;
	}

	@Override
	public double getWeight() {
		return d;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return source.toString()+" > "+target.toString();
	}

}
