package org.insa.algo.shortestpath;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Node;
import org.insa.graph.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;


public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
	public ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        // En fonction du mode (temps ou distance) il y a un facteur différent pour les estimations.
        double facteurMode = 1.0;
        if (this.getInputData().getMode().equals(Mode.TIME))
        {
        		//L'estimation du minimum de temps est le minimum de la distance divisé par le maximum de vitesse
        		facteurMode = (double)1/(double)data.getGraph().getGraphInformation().getMaximumSpeed()*(double)3600/(double)1000;
        }

        
        BinaryHeap<LabelAStar> tas = new BinaryHeap<LabelAStar>();
        
        // Initialisation du tableau de Labels
        LabelAStar[]tableauLabel = new LabelAStar [data.getGraph().size()];
        int i = 0;
        for(Node n : data.getGraph())
        {
        		tableauLabel[i] = new LabelAStar(i, Double.POSITIVE_INFINITY, null, false, facteurMode*(double)n.getPoint().distanceTo(data.getDestination().getPoint()));

        		i ++;
        }
        
        
        
        tableauLabel[data.getOrigin().getId()].setCout(0);
        tas.insert(tableauLabel[data.getOrigin().getId()]);
        tas.print();
        
        boolean testArrivee = false;
        boolean testOrigineDestination = false;
        
        if(data.getOrigin() == data.getDestination())
		{
			testOrigineDestination = true;
			notifyDestinationReached(data.getDestination());
		}
                		
        while(!tas.isEmpty() && !testArrivee && !testOrigineDestination) 
        {	
        		// Extraction du minimum du tas
        		LabelAStar labelMin = tas.deleteMin();
        		labelMin.setMarquage(true);
        		notifyNodeMarked(data.getGraph().get(labelMin.getId()));
        		
        		// Si ce successeur est la destination, arrêt de l'algorithme.
			if(data.getDestination() == data.getGraph().get(labelMin.getId()))
			{
				notifyDestinationReached(data.getGraph().get(labelMin.getId())); 
				testArrivee = true;
				ArrayList<Arc> listeArcs = new ArrayList<>();
				Arc arcIter = labelMin.getPrecedent();
				while(arcIter.getOrigin() != data.getOrigin())
				{
					listeArcs.add(arcIter);
					// Le nouvel arcIter est l'arc du label du noeud d'origine de l'ancien.
					arcIter = tableauLabel[arcIter.getOrigin().getId()].getPrecedent();
				}
				listeArcs.add(arcIter);
				Collections.reverse(listeArcs);
				Path chemin = new Path(data.getGraph(), listeArcs);				
				solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
			}
        		
        		Iterator<Arc> iterateurSuccesseurs = data.getGraph().get(labelMin.getId()).iterator();
        		// Pour tous les successeurs de labelMin
        		while(iterateurSuccesseurs.hasNext()) 
        		{
        			Arc arcSuccesseur = iterateurSuccesseurs.next();
        			LabelAStar labelSuccesseur = tableauLabel[arcSuccesseur.getDestination().getId()];
        			// Si ce n'est pas encore marqué ...
        			if(!labelSuccesseur.isMarquage()) 
        			{
        				// ... et si le coût du successeur > au coût du min + le coût de l'arc entre les deux ...
        				if(labelSuccesseur.getCout() > (labelMin.getCout() + data.getCost(arcSuccesseur))) 
        				{
        					if(labelSuccesseur.getCout() != Double.POSITIVE_INFINITY)
        					{
        						tas.remove(labelSuccesseur);	
        					}
        					else 
        					{
        						notifyNodeReached(data.getGraph().get(labelSuccesseur.getId()));
        					}
        					// ... alors, coût du successeur = le coût du min + le coût de l'arc,
        					// et father du successeur = min.
        					labelSuccesseur.setCout(labelMin.getCout() + data.getCost(arcSuccesseur));
        					labelSuccesseur.setPrecedent(arcSuccesseur);
        					// Si successeur existe dans le tas, alors le mettre à jour.
        					tas.insert(labelSuccesseur);
        					// Si ce successeur est l'origine du graphe
        					if(data.getOrigin() == data.getGraph().get(labelSuccesseur.getId()))
        					{
        						notifyOriginProcessed(data.getGraph().get(labelSuccesseur.getId()));
        					}
        				}
        			}
        		}
        }
        if(testOrigineDestination)
        {
	        	ArrayList<Arc> listeArcs = new ArrayList<>();
	        	Path chemin = new Path(data.getGraph(), listeArcs);				
			solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        }
        else if(!testArrivee)
        {
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        return solution;
    }

}

