package org.insa.dijkstra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest 
{

    // Graphe
    private static Graph graphe;

    // Noeuds
    private static Node[] noeuds;

    // Arcs
    @SuppressWarnings("unused")
    private static Arc x1ax2, x1ax3, x2ax4, x2ax5, x2ax6, x3ax1, x3ax2, x3ax6, x5ax3, x5ax4, x5ax6, x6ax5; 
    
    // Distancier Bellman-Ford
    private static double[][] distancierBF = new double[6][6];
    
    // Distancier Dijkstra
    private static double[][] distancierD = new double[6][6];
    
    @BeforeClass
    public static void initialisation() throws IOException 
    {
        // Création des noeuds
        noeuds = new Node[6];
        for (int i = 0; i < noeuds.length; ++i) 
        {
            noeuds[i] = new Node(i, null);
        }
        
     // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");

        // Création des arcs
        x1ax2 = Node.linkNodes(noeuds[0], noeuds[1], 7, speed10, null);
        x1ax3 = Node.linkNodes(noeuds[0], noeuds[2], 8, speed10, null);
        x2ax4 = Node.linkNodes(noeuds[1], noeuds[3], 4, speed10, null);
        	x2ax5 = Node.linkNodes(noeuds[1], noeuds[4], 1, speed10, null);
        	x2ax6 = Node.linkNodes(noeuds[1], noeuds[5], 5, speed10, null);
        	x3ax1 = Node.linkNodes(noeuds[2], noeuds[0], 7, speed10, null);
        	x3ax2 = Node.linkNodes(noeuds[2], noeuds[1], 2, speed10, null);
        	x3ax6 = Node.linkNodes(noeuds[2], noeuds[5], 2, speed10, null);
        	x5ax3 = Node.linkNodes(noeuds[4], noeuds[2], 2, speed10, null);
        	x5ax4 = Node.linkNodes(noeuds[4], noeuds[2], 2, speed10, null);
        	x5ax6 = Node.linkNodes(noeuds[4], noeuds[5], 3, speed10, null);
        	x6ax5 = Node.linkNodes(noeuds[5], noeuds[4], 3, speed10, null);

        graphe = new Graph("ID", "", Arrays.asList(noeuds), null);
        
        ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        
        // Remplissage distanciers
        for(int ligne = 0; ligne<graphe.size(); ++ligne)
        {
        		for(int colonne=0; colonne < graphe.size(); ++colonne) 
        		{
        			// Bellman-Ford
        			 Path bf = new BellmanFordAlgorithm(new ShortestPathData(graphe, noeuds[ligne], noeuds[colonne], arcInspector)).doRun().getPath();
        			 if(bf != null)
        			 {
        				 distancierBF[ligne][colonne] = bf.getLength();
        			 } else
        			 {
        				 distancierBF[ligne][colonne] = 0;
        			 }
        			
        			// Dijkstra
        			Path d = new DijkstraAlgorithm(new ShortestPathData(graphe, noeuds[ligne], noeuds[colonne], arcInspector)).doRun().getPath();
        			if(d != null)
       			 {
       				 distancierD[ligne][colonne] = d.getLength();
       			 } /*else
       			 {
       				 distancierD[ligne][colonne] = (Double) null;
       			 }*/
        		}
        }
    }
    
	@Test
    public void testSimpleOracle()
    {
		distancierD.toString();
    		for(int ligne=0; ligne<graphe.size(); ++ligne)
    		{
    			for(int colonne=0; colonne<graphe.size(); ++colonne)
    			{
    				assertEquals("Problème x"+ligne+"x"+colonne, distancierBF[ligne][colonne], distancierD[ligne][colonne], (double)0);
    			}
    		}
    }
}
