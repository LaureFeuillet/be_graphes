package org.insa.algo.shortestpath;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import java.util.Iterator;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> tas = new BinaryHeap();
        
        // Initialisation du tableau de Labels
        Label[]tableauLabel = new Label [data.getGraph().size()];
        for(int i = 0; i < data.getGraph().size() ; ++i)
        {
        		// Utiliser les setters
        		tableauLabel[i].setCout(Double.POSITIVE_INFINITY);
        		tableauLabel[i].setPrecedent(null);
        		tableauLabel[i].setMarquage(false);
        		tableauLabel[i].setId(i);
        }
        
        tableauLabel[data.getOrigin().getId()].setCout(0);
        tas.insert(tableauLabel[data.getOrigin().getId()]);
        
        boolean testMarquage = true;
        		
        while(testMarquage) // Test du marquage des sommets
        {
        		testMarquage = false;
        		for(int i = 0; i < data.getGraph().size() ; ++i)
        		{
        			if(!tableauLabel[i].isMarquage())
        			{
        				testMarquage = true;
        			}		
        		}
        		
        		// Extraction du minimum du tas
        		Label labelMin = tas.deleteMin();
        		labelMin.setMarquage(true);
        		
        		Iterator<Arc> iterateurSuccesseurs = data.getGraph().get(labelMin.getId()).iterator();
        		while(iterateurSuccesseurs.hasNext()) // Pour tous les successeurs de labelMin
        		{
        			Arc arcSuccesseur = iterateurSuccesseurs.next();
        			Label labelSuccesseur = tableauLabel[arcSuccesseur.getDestination().getId()];
        			if(!labelSuccesseur.isMarquage())
        			{
        				if(labelSuccesseur.getCout() > (labelMin.getCout() + data.getCost(arcSuccesseur)))
        				{
        					// Penser à mettre le findMin en father de ceux qu'on a itéré
        				}
        			}
        		}
        }
     
        
        return solution;
    }

}
