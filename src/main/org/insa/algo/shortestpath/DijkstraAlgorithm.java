package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Path;

import java.util.ArrayList;
import java.util.Iterator;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        // Initialisation du tableau de Labels
        Label[]tableauLabel = new Label [data.getGraph().size()];
        for(int i = 0; i < data.getGraph().size() ; ++i)
        {
        		tableauLabel[i] = new Label(i, Double.POSITIVE_INFINITY, null, false);
        }
        
        tableauLabel[data.getOrigin().getId()].setCout(0);
        tas.insert(tableauLabel[data.getOrigin().getId()]);
        
        boolean testArrivee = false;
        
        boolean testMarquage = true;
        		
        while(testMarquage && !testArrivee) // Test du marquage des sommets
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
        		// Pour tous les successeurs de labelMin
        		while(iterateurSuccesseurs.hasNext()) 
        		{
        			Arc arcSuccesseur = iterateurSuccesseurs.next();
        			Label labelSuccesseur = tableauLabel[arcSuccesseur.getDestination().getId()];
        			// Si ce n'est pas encore marqué ...
        			if(!labelSuccesseur.isMarquage()) 
        			{
        				// ... et si le coût du successeur > au coût du min + le coût de l'arc entre les deux ...
        				if(labelSuccesseur.getCout() > (labelMin.getCout() + data.getCost(arcSuccesseur))) 
        				{
        					boolean successeurExisteTas = true;
        					if(labelSuccesseur.getCout() == Double.POSITIVE_INFINITY)
        					{
        						successeurExisteTas = false;
        					}
        					// ... alors, coût du successeur = le coût du min + le coût de l'arc,
        					// et father du successeur = min.
        					labelSuccesseur.setCout(labelMin.getCout() + data.getCost(arcSuccesseur));
        					labelSuccesseur.setPrecedent(arcSuccesseur);
        					// Si successeur existe dans le tas, alors le mettre à jour.
        					if(successeurExisteTas == true)
        					{
        						tas.remove(labelSuccesseur);
        						tas.insert(labelSuccesseur);
        					}
        					else
        					{
        						tas.insert(labelSuccesseur);
        					}
        					// Si ce successeur est la destination, arrêt de l'algorithme.
        					if(data.getDestination() == data.getGraph().get(labelSuccesseur.getId()))
        					{
        						testArrivee = true;
        						ArrayList<Arc> listeArcs = new ArrayList<>();
        						Arc arcIter = labelSuccesseur.getPrecedent();
        						while(arcIter.getOrigin() != data.getOrigin())
        						{
        							listeArcs.add(arcIter);
        							// Le nouvel arcIter est l'arc du label du noeud d'origine de l'ancien.
        							arcIter = tableauLabel[arcIter.getOrigin().getId()].getPrecedent();
        						}
        						listeArcs.add(arcIter);
        						Path chemin = new Path(data.getGraph(), listeArcs);
        						
        						solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        					}
        				}
        			}
        		}
        }
        if(!testArrivee)
        {
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        return solution;
    }

}
