package org.insa.dijkstra;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest 
{

    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;
    
    // Distancier Bellman-Ford
    private static Path[][] pathBF = new Path[graph.size()][graph.size()];
    
    // Distancier Dijkstra
    private static Path[][] pathD = new Path[graph.size()][graph.size()];
    
    @BeforeClass
    public static void initAll() throws IOException 
    {
        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) 
        {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 7, null, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 8, null, null);
        b2d = Node.linkNodes(nodes[1], nodes[3], 4, null, null);
        	b2e = Node.linkNodes(nodes[1], nodes[4], 1, null, null);
        	b2f = Node.linkNodes(nodes[1], nodes[5], 5, null, null);
        	c2a = Node.linkNodes(nodes[2], nodes[0], 7, null, null);
        	c2b = Node.linkNodes(nodes[2], nodes[1], 2, null, null);
        	c2f = Node.linkNodes(nodes[2], nodes[5], 2, null, null);
        	e2c = Node.linkNodes(nodes[4], nodes[2], 2, null, null);
        	e2d = Node.linkNodes(nodes[4], nodes[2], 2, null, null);
        	e2f = Node.linkNodes(nodes[4], nodes[5], 3, null, null);
        	f2e = Node.linkNodes(nodes[5], nodes[4], 3, null, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        
        // Remplissage distancier Bellman-Ford
        
        
        // Remplissage distancier Dijkstra
    }
    
    @Test
    public void testCout()
    {
    		int i;
    		int j;
    		for(i=0; i<graph.size(); ++i)
    		{
    			for(j=0; j<graph.size(); ++j)
    			{
    				ShortestPathSolution dijkstra = new DijkstraAlgorithm(new ShortestPathData(graph, nodes[i], nodes[j], null)).doRun();
    				ShortestPathSolution bf = new BellmanFordAlgorithm(new ShortestPathData(graph, nodes[i], nodes[j], null)).doRun();
    				assertEquals("Entre x"+i+" et x"+j, dijkstra, bf);
    				//assertEquals("Test", dijkstra, 1);
    				dijkstra.toString();
    			}
    		}
    }
}
