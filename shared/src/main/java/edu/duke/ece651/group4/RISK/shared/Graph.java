package edu.duke.ece651.group4.RISK.shared;

import java.io.Serializable;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class implements a generic graph data structure.
 * For this particular graph implementation:
 * - edge is not weighted.
 * - only one edge is allowed between 2 vertices.
 */
public class Graph<T> implements Serializable {
    /**
     * All vertices.
     */
    protected List<T> vertices;
    /**
     * boolean matrix that stores adjacency relationship between vertices.
     * true: two vertices are adjacent. 
     */
    protected boolean[][] adjMatrix;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.adjMatrix = new boolean[0][0];
    }

    public Graph(List<T> vertices, boolean[][] adjMatrix) {
        this.vertices = vertices;
        this.adjMatrix = adjMatrix;
    }

    /**
     * Get the number of vertices in graph. 
     * @return number of vertices in graph;
     */
    public int size() {
        return vertices.size();
    }

    /**
     * Get all vertices in the graph. 
     * @return a list of all vertices in the graph.
     */
    public List<T> getVertices() {
        return vertices;
    }

    /**
     * Get all the vertices adjacent to a certain vertex.
     * @param data is the data in the vertex to find adjacents of.
     * @return a list of all adjacent vertices.
     */
    public List<T> getAdjacentVertices(T key) {
        List<T> ans = new ArrayList<>();
        int i = vertices.indexOf(key);
        for (int j = 0; j < adjMatrix[i].length; j++) {
            if (adjMatrix[i][j] == true) {
                ans.add(vertices.get(j));
            }
        }
        return ans;
    }

    /**
     * Add a vertex to graph.
     * @param vertex is the vertex to add.
     */
    public void addVertex(T vertex) {
        // enlarge the adjacency matrix
        int n = vertices.size();
        boolean[][] newAdjMatrix = new boolean[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
        }
        adjMatrix = newAdjMatrix;
        // add to vertices
        vertices.add(vertex);
    }

    /**
     * Remove a vertex from graph. 
     * @param data is the data in the vertex to move.
     */
    
    public void removeVertex(T key) {
        // TODO: Not required in evol1
    }
    
    /**
     * Add edge between two vertices.
     * Duplicated edge will not be added.
     * @param v1 is one end of the edge.
     * @param v2 is the other end the edge.
     */
    public void addEdge(T v1, T v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        adjMatrix[i][j] = true;
        adjMatrix[j][i] = true;
    }
    
    /**
     * Remove edge between two vertices.
     * @param data1 is the data in one end of the edge.
     * @param data2 is the data in other end the edge.
     */
    
    public void removeEdge(T key1,T key2) {
       // TODO: Not required in evol1
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

    /**
     * Check if two vertices in the graph is adjacent to each other. 
     * @param v1 is a vertex
     * @param v2 is the other vertex
     * @return true, if two vertices are adjacent;
     *         false, if not.
     */
    public boolean isAdjacent(T v1, T v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        return adjMatrix[i][j];
    }

    /*
    @Override
    public Iterator<T> iterator() {
        return new GraphIterator();
    }

    public class GraphIterator implements Iterator<T> {
        private int position = 0;
        @Override
        public boolean hasNext() {
            if (position < getVertices().size()) {
                return true;
            } 
            return false;
        }
    
        @Override
        public T next() {
            if(hasNext()) {
                return getVertices().get(position++);
            }
            return null;
        }
    
    }
    */

    // TODO: function that checks if two vertices have a path between them (must be of same owner)
}

