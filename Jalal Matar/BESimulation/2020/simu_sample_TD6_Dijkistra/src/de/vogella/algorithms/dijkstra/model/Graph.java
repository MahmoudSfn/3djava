package de.vogella.algorithms.dijkstra.model;

import java.util.List;


public class Graph {
  private final List<IVertex> vertexes;
  private final List<IEdge> edges;

  public Graph(List<IVertex> vertexes, List<IEdge> edges) {
    this.vertexes = vertexes;
    this.edges = edges;
  }

  public List<IVertex> getVertexes() {
    return vertexes;
  }

  public List<IEdge> getEdges() {
    return edges;
  }
  
  
  
}