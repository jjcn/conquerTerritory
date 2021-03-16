package edu.duke.ece651.group4.RISK.shared;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import java.io.Serializable;

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
     * Creates a spanning tree, and add several random connections to it
     * @param numNewEdges is the number of new connections introduced to the spanning tree.
     * @param rand is the Random object.
     */
    public void addRandomEdges(int numNewEdges, Random rand) {
        if (size() == 0 || size() == 1) {
            return;
        }
        for (int i = 0; i < size() - 1; i++) {
            adjMatrix[i][i + 1] = true;
            adjMatrix[i + 1][i] = true;
        }
        // add random connections to it
        while (numNewEdges > 0) {
            adjMatrix[rand.nextInt(size())][rand.nextInt(size())] = true;
            numNewEdges--;
        }
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
     * @param key is the data in the vertex to find adjacents of.
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
     * @param key is the data in the vertex to move.
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
     * @param key1 is the data in one end of the edge.
     * @param key2 is the data in other end the edge.
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

    /**
     * Checks if a graph obeys certain rules. The rules are
     * - Each vertex be adjacent to one or more other vertices.
       - The vertices must form a connected graph (all vertices must be reachable from any
         other vertex).
     * @return true, if the graph obeys the rules;
     *         false, if not.
     */
    public boolean isValid() {
        return allHasAdjacents() && isConnectedGraph();
    }

    /**
     * Checks if a graph obeys the following rule:
     * - Each vertex is adjacent to one or more other vertices.
     * @return true, if the graph obeys the rules;
     *         false, if not.
     */
    private boolean allHasAdjacents() {
        for (int i = 0; i < size(); i++) {
            boolean hasAdjacent = false;
            for (int j = 0; j < size(); j++) { // iterate over a line of adjacency matrix
                hasAdjacent = hasAdjacent || adjMatrix[i][j];
            }
            if (hasAdjacent == false) { // false if no adjacents found with this vertex
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a graph obeys the following rule:
       - The vertices must form a connected graph (all vertices must be reachable from any
         other vertex).
     * @return true, if the graph obeys the rules;
     *         false, if not.
     */
    private boolean isConnectedGraph() {
        if (size() == 0) {
            return true;
        }
        List<T> vertices = getVertices();
        for (int i = 0; i < size(); i++) {
            if (!hasPath(vertices.get(0), vertices.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if v1 can reach v2.
     * @param start is the starting vertex.
     * @param end is the target vertex.
     * @return true, if v2 is reachable from v1;
     *         false, if not.
     */
    public boolean hasPath(T start, T end) {
        if (start.equals(end)) {
            return true;
        }
        
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        
        queue.add(start);
        visited.add(start);
        while (queue.size() != 0) {
            T key = queue.poll();
            List<T> adjacents = getAdjacentVertices(key);
            for (T adjacent : adjacents) {
                if (adjacent.equals(end)) {
                    return true;
                }
                if (!visited.contains(adjacent)) {
                    visited.add(adjacent);
                    queue.add(adjacent);
                }
            }
        }
        return false;
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

}

