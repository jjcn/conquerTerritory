package edu.duke.group4.RISK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class implements a generic graph data structure.
 * For this particular graph implementation:
 * - edge is not weighted.
 * - only one edge is allowed between 2 vertices.
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

        public T getData() {
            return data;
        }
    }

    public Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    public Graph(Map<Vertex, List<Vertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    /**
     * Get the number of vertices in the graph. 
     * @return number of vertices in the graph;
     */
    public int getSize() {
        return adjVertices.size();
    }

    /**
     * Get all vertices in the graph. 
     * @return a list of all vertices in the graph.
     */
    public List<Vertex> getVertices() {
        return adjVertices.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Get all the vertices that are adjacent to a certain vertex.
     * @param data is the data in the vertex to find adjacents of.
     * @return a list of all adjacent vertices.
     */
    public List<Vertex> getAdjacentVertices(T data) {
        Vertex v = new Vertex(data);
        return adjVertices.get(v);
    }

    /**
     * Add a vertex to graph.
     * @param data is the data in the new vertex.
     */
    public void addVertex(T data) {
        adjVertices.putIfAbsent(new Vertex(data), new ArrayList<>());
    }

    /**
     * Remove a vertex from graph.
     * @param data is the data in the vertex to move.
     */
    public void removeVertex(T data) {
        Vertex v = new Vertex(data);
        adjVertices.values().stream().forEach(vertex -> vertex.remove(v)); // remove all connected edges
        adjVertices.remove(v); // remove vertex
    }

    /**
     * add edge between two vertices.
     * @param v1 is one end of the edge.
     * @param v2 is the other end the edge.
     */
    public void addEdge(Vertex v1, Vertex v2) {
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }
    
    /**
     * remove edge between two vertices.
     * @param v1 is one end of the edge.
     * @param v2 is the other end the edge.
     */
    public void removeEdge(Vertex v1, Vertex v2) {
        List<Vertex> adjs1 = adjVertices.get(v1);
        List<Vertex> adjs2 = adjVertices.get(v2);
        if (adjs1 != null)
            adjs1.remove(v2);
        if (adjs2 != null)
            adjs2.remove(v1);
    }
    
    // TODO: add iterator
}