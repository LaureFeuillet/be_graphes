package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
	public ShortestPathSolution doRun() {
    	
        // Données fournies par l'interface
    		ShortestPathData data = getInputData();
        
        // Solution à retourner, nulle pour l'instant
        ShortestPathSolution solution = null;
        
        // Tas contenant les Label
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        // Initialisation du tableau de Labels
        Label[]tableauLabel = new Label[data.getGraph().size()];
        for(int i = 0; i<data.getGraph().size(); ++i)
        {
        		tableauLabel[i] = new Label(i, Double.POSITIVE_INFINITY, null, false);
        }
        
        boolean testOrigineDestination = false;
        
        if(data.getOrigin() == data.getDestination())
		{
			testOrigineDestination = true;
			notifyDestinationReached(data.getDestination());
		}

    		// Insertion de l'origine dans le tas
        tableauLabel[data.getOrigin().getId()].setCout(0);
        tas.insert(tableauLabel[data.getOrigin().getId()]);
        
        // Tant qu'il y a des éléments dans le tas et qu'on n'a pas atteint l'arrivée
        while(!tas.isEmpty() && !(tableauLabel[data.getDestination().getId()].isMarquage()) && !testOrigineDestination) 
        {	
        		//System.out.println("Extraction d'un min.");
        		// Extraction du minimum du tas
        		Label labelMin = tas.deleteMin();
        		labelMin.setMarquage(true);
        		notifyNodeMarked(data.getGraph().get(labelMin.getId()));
        		
        		// Si ce successeur est la destination, arrêt de l'algorithme.
			if(data.getDestination() == data.getGraph().get(labelMin.getId()))
			{
				System.out.println("On a atteint la destination.");
				notifyDestinationReached(data.getGraph().get(labelMin.getId())); 
			}
        		
			// Parcours de tous les successeurs du min
        		Iterator<Arc> iterateurSuccesseurs = data.getGraph().get(labelMin.getId()).iterator();
        		while(iterateurSuccesseurs.hasNext()) 
        		{
        			Arc arcSuccesseur = iterateurSuccesseurs.next();
        			Label labelSuccesseur = tableauLabel[arcSuccesseur.getDestination().getId()];
        			System.out.println("labelSuccesseur = "+labelSuccesseur);

        			// Si ce successeur est autorisé et si il n'est pas encore marqué ...
        			if(data.isAllowed(arcSuccesseur) && !labelSuccesseur.isMarquage()) 
        			{
        				System.out.println("Successeur autorisé");
        				// ... et si le coût du successeur > au coût du min + le coût de l'arc entre les deux ...
        				if(labelSuccesseur.getCout() > (labelMin.getCout() + data.getCost(arcSuccesseur))) 
        				{
        					// Si le successeur 
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
        				}
        			}
        		}
	    }
   

        // Si Origine = Destination
        if(testOrigineDestination) 
        {
        		ArrayList<Arc> listeArcs = new ArrayList<>();
        		Path chemin = new Path(data.getGraph(), listeArcs);	
			solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        } 
        // Sinon si on n'atteint jamais la destination
        else if(!(tableauLabel[data.getDestination().getId()].isMarquage()))
        {
        		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else // Sinon on a trouvé la solution optimale
        {
        		System.out.println("On a trouvé la solution optimale.");
        		ArrayList<Arc> listeArcs = new ArrayList<>();
        		Arc arcIter = tableauLabel[data.getDestination().getId()].getPrecedent();
        		while(arcIter.getOrigin() != data.getOrigin())
        		{
        			System.out.println("Construction solution.");
        			listeArcs.add(arcIter);
        			// Le nouvel arcIter est l'arc du label du noeud d'origine de l'ancien.
        			System.out.println("Précédent : "+tableauLabel[arcIter.getOrigin().getId()]);
        			arcIter = tableauLabel[arcIter.getOrigin().getId()].getPrecedent();
        		}
        		listeArcs.add(arcIter);
        		Collections.reverse(listeArcs);
        		Path chemin = new Path(data.getGraph(), listeArcs);				
        		solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        }
        return solution;
    }
}
