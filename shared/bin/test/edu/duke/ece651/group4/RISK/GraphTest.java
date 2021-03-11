package edu.duke.group4.RISK;

import static org.junit.Assert.*;

import java.util.List;

import org.graalvm.compiler.graph.Graph;
import org.junit.Test;

import edu.duke.group4.RISK.Graph.Vertex;

public class GraphTest {

    String[] names = 
        "Narnia, Midkemia, Oz, Gondor, Mordor, Roshar, Scadrial, Elantris, Roshar".split(", ");

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
        assertEquals(0, graph.getSize);
        graph.addVertex(1);
        assertEquals(1, graph.getSize);
        graph.addVertex(2);
        assertEquals(2, graph.getSize);
        graph.addVertex(3);
        assertEquals(3, graph.getSize);
        graph.removeVertex(3);
        assertEquals(2, graph.getSize);
        graph.removeVertex(2);
        assertEquals(1, graph.getSize);
        graph.removeVertex(0);
        assertEquals(1, graph.getSize);
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
        List<Vertex> adjs = graph.getAdjacentVertices("Scadrial");
        List<Vertex> expected = new ArrayList<>();
        String[] adjNamesScadrial =
            "Midkemia, Oz, Mordor, Roshar, Scadrial, Elantris, Roshar".split(", ");
        for (String name: adjNamesScadrial) {
            expected.add(new Vertex(name));
        }
        assertTrue(vertices.containsAll(expected));
    }

    @Test
    public void testAddVertex() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(1);
        graph.addVertex("2");
    }
    
    @Test
    public void testRemoveVertex() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
    }

    @Test
    public void testAddEdge() {

    }

    @Test
    public void testRemoveEdge() {

    }
    
}