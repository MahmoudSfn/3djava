package de.vogella.algorithms.dijkstra.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import de.vogella.algorithms.dijkstra.engine.DijkstraAlgorithm;
import de.vogella.algorithms.dijkstra.model.Edge;
import de.vogella.algorithms.dijkstra.model.Graph;
import de.vogella.algorithms.dijkstra.model.IEdge;
import de.vogella.algorithms.dijkstra.model.IVertex;
import de.vogella.algorithms.dijkstra.model.Vertex;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DEdge;
import de.vogella.algorithms.dijkstra.model.jfxmodel.Point3DVertex;
import javafx.geometry.Point3D;


public class TestDijkstraAlgorithm_Point3D {

  private List<IVertex> nodes;
  private List<IEdge> edges;
  
  public static void main(String[] args) {
	TestDijkstraAlgorithm_Point3D tda = new TestDijkstraAlgorithm_Point3D();
	tda.testExcute();
}

  public void testExcute() {
    nodes = new ArrayList<IVertex>();
    edges = new ArrayList<IEdge>();

    addLane(new Point3DVertex(0,0,0), new Point3DVertex(0,1,0));
    addLane(new Point3DVertex(0,1,0), new Point3DVertex(1,1,0));
    addLane(new Point3DVertex(1,1,0), new Point3DVertex(1,2,0));
    addLane(new Point3DVertex(1,2,0), new Point3DVertex(2,2,0));
    addLane(new Point3DVertex(2,2,0), new Point3DVertex(4,4,0));

    addLane(new Point3DVertex(0,0,0), new Point3DVertex(4,4,0));

    // Lets check from location Loc_1 to Loc_10
    Graph graph = new Graph(nodes, edges);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    dijkstra.execute(new Point3DVertex(0,0,0));
    LinkedList<IVertex> path = dijkstra.getPath(new Point3DVertex(4,4,0));
        
    for (IVertex vertex : path) {
      System.out.println(vertex);
    }
    
  }

  private void addLane(Point3DVertex s,Point3DVertex t) {
	  	if(!nodes.contains(s)) nodes.add(s);
	  	if(!nodes.contains(t)) nodes.add(t);

	  	Point3DEdge e1 = new Point3DEdge(s,t); 
	  	Point3DEdge e2 = new Point3DEdge(t,s); 
	  	
	    edges.add(e1);
	    edges.add(e2);

  }
}