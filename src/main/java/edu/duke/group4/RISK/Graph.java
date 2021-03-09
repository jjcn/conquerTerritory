package edu.duke.group4.RISK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * This class implements a generic graph data structure.
 */
public class Graph<T> {
    /**
     * This class implements vertex in a graph.
     */
    public class Vertex {
        T data;

        public Vertex (T data) {
            this.data = data;
        }
    }

    public Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    /**
     * Add a vertex to graph.
     * @param vertex
     */
    public void addVertex(Vertex v) {
        adjVertices.putIfAbsent(v, new ArrayList<>());
    }

    /**
     * Remove a vertex from graph.
     * @param vertex
     */
    public void removeVertex(Vertex v) {
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(v);
    }

    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }
    
    public void removeEdge(Vertex v1, Vertex v2) {

    }
    
}