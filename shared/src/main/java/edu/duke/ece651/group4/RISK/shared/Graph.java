package edu.duke.ece651.group4.RISK.shared;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

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
        private T data;

        public Vertex (T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    /**
     * A map that has mapping: vertex -> all adjacent vertices
     */
    private Map<Vertex, List<Vertex>> adjVertices;

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
     * Get all the data in the graph as a list.
     */
    public List<T> getAllData() {
        List<T> ans = new ArrayList<>();
        for (Vertex v :getVertices()) {
            ans.add(v.getData());
        }
        return ans;
    }

    /**
     * Get all the vertices adjacent to a certain vertex.
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
     * Add edge between two vertices.
     * Duplicated edge will not be added.
     * @param data1 is the data in one end of the edge.
     * @param data2 is the data in other end the edge.
     */
    public void addEdge(T data1, T data2) {
        Vertex v1 = new Vertex(data1);
        Vertex v2 = new Vertex(data2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }
    
    /**
     * Remove edge between two vertices.
     * @param data1 is the data in one end of the edge.
     * @param data2 is the data in other end the edge.
     */
    public void removeEdge(T data1,T data2) {
        Vertex v1 = new Vertex(data1);
        Vertex v2 = new Vertex(data2);
        List<Vertex> adjs1 = adjVertices.get(v1);
        List<Vertex> adjs2 = adjVertices.get(v2);
        if (adjs1 != null)
            adjs1.remove(v2);
        if (adjs2 != null)
            adjs2.remove(v1);
    }
    
    
    /**
     * Extract the data in a collection of vertices.
     * @param vertices is the vertex to extract data from.
     * @return a list of data.
     */
    /*
    public List<T> toData(List<Vertex> vertices) {
        List<T> ans = new ArrayList<>();
        for (Vertex v : vertices) {
            ans.add(v.getData());
        }
        return ans;
    }
    */

    // TODO: add iterator
}

