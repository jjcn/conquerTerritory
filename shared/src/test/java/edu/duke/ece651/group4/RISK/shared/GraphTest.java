package edu.duke.ece651.group4.RISK.shared;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FIXIT: Cannot create instance of Vertex.
 */
public class GraphTest {
    String[] names = 
        "Narnia, Midkemia, Oz, Gondor, Mordor, Roshar, Scadrial, Elantris, Roshar".split(", ");

    /**
     * Creates a test graph. Same as the one on Evolution 1 requirements.
     */
    public Graph<String> createTestGraph1() {
        Graph<String> graph = new Graph<>();
        for (String name: names) {
            graph.addVertex(name);
        }
        graph.addEdge("Narnia", "Midkemia");
        graph.addEdge("Narnia", "Elantris");
        graph.addEdge("Midkemia", "Elantris");
        graph.addEdge("Midkemia", "Scadrial");
        graph.addEdge("Midkemia", "Oz");
        graph.addEdge("Oz", "Scadrial");
        graph.addEdge("Oz", "Mordor");
        graph.addEdge("Oz", "Gondor");
        graph.addEdge("Gondor", "Mordor");
        graph.addEdge("Elantris", "Scadrial");
        graph.addEdge("Elantris", "Roshar");
        graph.addEdge("Scadrial", "Roshar");
        graph.addEdge("Scadrial", "Hogwarts");
        graph.addEdge("Scadrial", "Mordor");
        graph.addEdge("Mordor", "Hogwarts");

        return graph;
    }

    /**
     * Helper function that tests if adjacents of a vertex is as expected. 
     * @param graph is the graph
     * @param data is the vertex to find adjacents
     * @param expectedAdjs is the expected adjacents
     */
    public void assertEqualAdjacents(Graph<T> graph, T data, List<T> expectedAdjs) {
        List<Vertex> expected = new ArrayList<>();
        for (T adj: expectedAdjs) {
            expected.append(new Vertex(adj));
        }
        assertTrue(graph.getAdjacentVertices(1).containsAll(expected));
    }

    @Test
    public void testCreation() {
        Graph<Integer> intGraph = new Graph<>();
        Graph<String> stringGraph = new Graph<>();
        Graph<Territory> terrGraph = new Graph<>();
    }

    @Test
    public void testGetSize() {
        Graph<Integer> graph = new Graph<>();
        assertEquals(0, graph.getSize());
        graph.addVertex(1);
        assertEquals(1, graph.getSize());
        graph.addVertex(2);
        assertEquals(2, graph.getSize());
        graph.addVertex(3);
        assertEquals(3, graph.getSize());
        graph.removeVertex(3);
        assertEquals(2, graph.getSize());
        graph.removeVertex(2);
        assertEquals(1, graph.getSize());
        graph.removeVertex(0);
        assertEquals(1, graph.getSize());
    }

    @Test
    public void testGetVertices() {
        Graph<String> graph = createTestGraph1();
        List<Vertex> vertices = graph.getVertices();
        List<Vertex> expected = new ArrayList<>();
        for (String name: names) {
            expected.add(new Vertex(name));
        }
        assertTrue(vertices.containsAll(expected));
    }

    @Test
    public void testGetAdjacentVertices() {
        Graph<String> graph = createTestGraph1();

        List<Vertex> adjsScadrial = graph.getAdjacentVertices("Scadrial");
        List<Vertex> expectedScadrial = new ArrayList<>();
        String[] adjNamesScadrial =
            "Midkemia, Oz, Mordor, Roshar, Scadrial, Elantris, Roshar".split(", ");
        for (String name: adjNamesScadrial) {
            expectedScadrial.add(new Vertex(name));
        }
        assertTrue(adjsScadrial.containsAll(expectedScadrial));

        List<Vertex> adjsGondor = graph.getAdjacentVertices("Gondor");
        List<Vertex> expectedGondor = new ArrayList<>();
        String[] adjNamesGondor = "Oz, Mordor".split(", ");
        for (String name: adjNamesGondor) {
            expectedGondor.add(new Vertex(name));
        }
        assertTrue(adjsGondor.containsAll(expectedGondor));
    }

    @Test
    public void testAddVertex() {
        Graph<Integer> graph = new Graph<>();
        Map<Vertex, List<Vertex>> adjs;
        graph.addVertex(1);
        adjs.putIfAbsent(new Vertex(1), new ArrayList<>());
        assertEquals(graph, adjs);

        graph.addVertex(-1);
        adjs.putIfAbsent(new Vertex(-1), new ArrayList<>());
        assertEquals(graph, adjs);
    }
    
    @Test
    public void testRemoveVertex() {
        // TODO
    }

    @Test
    public void testAddEdge() {
        Graph<Integer> graph = new Graph<>();
        ArrayList<Integer> emptyList = new ArrayList<>();
        Vertex[] vertices = new Vertex[4];
        for (int i = 0; i < 4; i++) {
            vertices[i] = new Vertex(i + 1);
            graph.addVertex(i + 1);
        }
        assertEqualAdjacents(graph, 1, emptyList);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(2, 3));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(1));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(1));
        assertEqualAdjacents(graph, 4, emptyList);

        graph.addEdge(2, 3);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(2, 3));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(1, 3));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(1, 2));
        assertEqualAdjacents(graph, 4, emptyList);

        graph.addEdge(3, 4);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(2, 3));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(1, 3));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(1, 2));
        assertEqualAdjacents(graph, 4, new ArrayList<Integer>(3));
    }

    @Test
    public void testRemoveEdge() {
        // TODO
    }


}
