package org.insa.algo.shortestpath;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        // Initialisation
        // label.cost[0]=0
        for(int i = 1; i < data.getGraph().size() ; ++i)
        {
        		// label.cost[i] = infini
        }
        
        
        
        
        
        
        
        
        
        return solution;
    }

}
