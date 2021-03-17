package edu.duke.ece651.group4.RISK.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

//import edu.duke.ece651.group4.RISK.shared.Graph.GraphIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GraphTest {
    String[] names = 
        "Narnia, Midkemia, Oz, Gondor, Mordor, Hogwarts, Scadrial, Elantris, Roshar".split(", ");

    /**
     * Creates a test graph. Same as the one on Evolution 1 requirements.
     */
    public Graph<String> createGraphFantasy() {
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

    @Test
    public void testCreation() {
        Graph<Integer> intGraph = new Graph<>();
        Graph<Territory> terrGraph = new Graph<>();

        List<Character> vertices = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
        boolean adjMatrix[][] = {{false, true, true}, {true, false, true}, {true, true, false}};
        Graph<Character> charGraph = new Graph<>(vertices, adjMatrix);
    }

    @Test
    public void testArrayCopyOf() {
        int size = 2;
        boolean[][] mat = {{true, false}, {true, true}};
        boolean[][] copy = Arrays.copyOf(mat, size);

        assertNotEquals(mat, copy);
    }

    @Test
    public void testArrayCopyConstructor() {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2));
        List<Integer> copy = new ArrayList<>(list);
        list.set(0, 5);

        assertEquals(0, copy.get(0));
    }

    /**
     * Helper function that prints out adjacent matrix as 0's and 1's.
     * @param matrix is a boolean adjacent matrix
     */
    public void print2dArray(boolean[][] matrix) {
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print((matrix[i][j] == true ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    public boolean isDiagonalSymmetric(boolean[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != mat[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testAddRandomEdges() {
        Graph<Integer> intGraph = new Graph<>();
        intGraph.addRandomEdges(intGraph.size(), new Random(0));
        for (int i = 1; i <= 9; i++) {
            intGraph.addVertex(i);
        }
        intGraph.addRandomEdges(intGraph.size(), new Random(0));
        print2dArray(intGraph.adjMatrix);
        assertTrue(isDiagonalSymmetric(intGraph.adjMatrix));
    }

    @Test
    public void testGetSize() {
        Graph<Integer> graph = new Graph<>();
        assertEquals(0, graph.size());
        graph.addVertex(1);
        assertEquals(1, graph.size());
        graph.addVertex(2);
        assertEquals(2, graph.size());
        graph.addVertex(3);
        assertEquals(3, graph.size());
        /*
        graph.removeVertex(3);
        assertEquals(2, graph.size());
        graph.removeVertex(2);
        assertEquals(1, graph.size());
        graph.removeVertex(0);
        assertEquals(1, graph.size());
        */
    }

    @Test
    public void testGetVertices() {
        Graph<String> graph = createGraphFantasy();
        
        List<String> expected = new ArrayList<>();
        for (String name: names) {
            expected.add(name);
        }
        
        assertTrue(graph.getVertices().containsAll(expected));
    }

    @Test
    public void testGetAdjacentVertices() {
        Graph<String> graph = createGraphFantasy();

        List<String> adjsScadrial = graph.getAdjacentVertices("Scadrial");
        List<String> expectedScadrial = new ArrayList<>();
        String[] adjNamesScadrial =
            "Midkemia, Oz, Mordor, Hogwarts, Elantris, Roshar".split(", ");
        for (String name: adjNamesScadrial) {
            expectedScadrial.add(name);
        }
        assertTrue(adjsScadrial.containsAll(expectedScadrial));

        List<String> adjsGondor = graph.getAdjacentVertices("Gondor");
        List<String> expectedGondor = new ArrayList<>();
        String[] adjNamesGondor = "Oz, Mordor".split(", ");
        for (String name: adjNamesGondor) {
            expectedGondor.add(name);
        }
        assertTrue(adjsGondor.containsAll(expectedGondor));
    }

    @Test
    public void testAddVertex() {
        Graph<Integer> graph = new Graph<>();
        List<Integer> expected = new ArrayList<>();
        
        graph.addVertex(1);
        expected.add(1);
        assertTrue(graph.getVertices().containsAll(expected));

        graph.addVertex(-1);
        expected.add(-1);
        assertTrue(graph.getVertices().containsAll(expected));
    }
    
    @Test
    public void testRemoveVertex() {
        // TODO
    }

    /**
     * Helper function that tests if adjacents of a vertex is as expected. 
     * @param graph is the graph
     * @param data is the vertex to find adjacents
     * @param expectedAdjs is the expected adjacents
     */
    public void assertEqualAdjacents(Graph<Integer> graph, int key, List<Integer> expectedAdjs) {
        assertTrue(graph.getAdjacentVertices(key).containsAll(expectedAdjs));
    }

    @Test
    public void testAddEdge() {
        Graph<Integer> graph = new Graph<>();
        for (int i = 1; i <= 4; i++) {
            graph.addVertex(i);
        }
        ArrayList<Integer> emptyList = new ArrayList<>();
        
        assertEqualAdjacents(graph, 1, emptyList);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(Arrays.asList(2, 3)));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(Arrays.asList(1)));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(Arrays.asList(1)));
        assertEqualAdjacents(graph, 4, emptyList);

        graph.addEdge(2, 3);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(Arrays.asList(2, 3)));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(Arrays.asList(1, 3)));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(Arrays.asList(1, 2)));
        assertEqualAdjacents(graph, 4, emptyList);

        graph.addEdge(3, 4);
        assertEqualAdjacents(graph, 1, new ArrayList<Integer>(Arrays.asList(2, 3)));
        assertEqualAdjacents(graph, 2, new ArrayList<Integer>(Arrays.asList(1, 3)));
        assertEqualAdjacents(graph, 3, new ArrayList<Integer>(Arrays.asList(1, 2)));
        assertEqualAdjacents(graph, 4, new ArrayList<Integer>(3));
    }

    @Test
    public void testRemoveEdge() {
        // TODO
    }

    /*
    @Test
    public void testIterator() {
        Graph<String> graph = createTestGraph1();
        GraphIterator gi = graph.iterator();
        while (gi.hasNext()) {
            System.out.println(gi.next());
        }
    }
    */

    @Test
    public void testIsValid() {
        // empty graph
        Graph<String> graphEmpty = new Graph<>();
        assertTrue(graphEmpty.isValid());

        // one vertex
        Graph<Integer> graphOneVertex = new Graph<>();
        graphOneVertex.addVertex(1);
        assertFalse(graphOneVertex.isValid());

        // fantasy 
        Graph<String> graphFantasy = createGraphFantasy();
        assertTrue(graphFantasy.isValid());

        // random using addRandomEdges()
        Graph<Integer> graphRandom = new Graph<>();
        for (int i = 1; i <= 9; i++) {
            graphRandom.addVertex(i);
        }
        graphRandom.addRandomEdges(graphRandom.size(), new Random());
        assertTrue(graphRandom.isValid());

        // not fully connected
        Graph<Integer> graphNotFullyConnected = new Graph<>();
        for (int i = 1; i <= 4; i++) {
            graphNotFullyConnected.addVertex(i);
        }
        graphNotFullyConnected.addEdge(1, 2);
        graphNotFullyConnected.addEdge(3, 4);
        assertFalse(graphNotFullyConnected.isValid());
    }

    @Test
    public void testHasPath() {
        // one vertex
        Graph<Integer> graphOneVertex = new Graph<>();
        graphOneVertex.addVertex(1);
        assertTrue(graphOneVertex.hasPath(1, 1));

        // fantasy 
        Graph<String> graphFantasy = createGraphFantasy();
        assertTrue(graphFantasy.hasPath("Mordor", "Mordor"));
        assertTrue(graphFantasy.hasPath("Mordor", "Gondor"));
        assertTrue(graphFantasy.hasPath("Oz", "Scadrial"));

        // not fully connected
        Graph<Integer> graphNotFullyConnected = new Graph<>();
        for (int i = 1; i <= 4; i++) {
            graphNotFullyConnected.addVertex(i);
        }
        graphNotFullyConnected.addEdge(1, 2);
        graphNotFullyConnected.addEdge(1, 3);
        assertTrue(graphNotFullyConnected.hasPath(2, 3));
        assertFalse(graphNotFullyConnected.hasPath(2, 4));
    }
}
