package algorithms.nmcs;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import components.Grid;
import game.Mode;

import java.util.concurrent.*;

import algorithms.DataManager;
import algorithms.ResearchAlgorithm;

public class NMCS implements ResearchAlgorithm{
	
	private static int algoId;
	
	private final int depthLevel;
	
	public NMCS(int depthLevel) {
		this.depthLevel = depthLevel;
		this.algoId = depthLevel;
	}
	
	public NMCSState setUpInitState() {
		Grid initGrid = new Grid();
		initGrid.initGrid();
		initGrid.updatePlayablePoints();
		
		NMCSState initState = new NMCSState(null, initGrid);
		return initState;
	}

	@Override
	public Grid algorithm() {
		NMCSState initialState = setUpInitState();
		long debut = System.currentTimeMillis();
        long dureeMax = 30 * 1000; // 30 seconds time limit
		return nmcs(initialState, depthLevel, debut, dureeMax);
	}
	
	
	/**
	 * This method executes the monte-carlo search. 
	 * In practise, we will never reach the maximum depth level (i.e a terminal state)
	 * so we do not test if the state is terminal or not
	 * 
	 * @param state
	 * @param level
	 * @param debut
	 * @param dureeMax
	 * @return the result of the monte carlo search for a given depth level and time limit
	 */
	public Grid nmcs(NMCSState state, int level, long debut, long dureeMax) {
	    if (level == 0) {
	        return state.rollout();
	    }

	    state.exploreChilds();
	    Grid bestGrid = new Grid();
	    
	    // Creating a thread pool
	    ExecutorService executor = Executors.newFixedThreadPool(3);
	    List<Future<Grid>> futures = new ArrayList<>();
	    
	    // Shuffling list so we don't only search in left childs due to time limit
	    List<NMCSState> randomChildList = state.getChilds();
	    Collections.shuffle(randomChildList);
	    
	    // Creating futures to get results for each childs
	    for (NMCSState childState : randomChildList) {	
	    	Callable<Grid> worker = new NMCSWorker(this, childState, level - 1, debut, dureeMax);
	    	Future<Grid> future = executor.submit(worker);
	    	futures.add(future);
	    }
	    
	    for (Future<Grid> future : futures) {
	    	if (System.currentTimeMillis() - debut > dureeMax) {
                break; // Stop algo if end of time
            }
	        try {
	            Grid currentChildGrid = future.get();
	            if (currentChildGrid.getLines().size() > bestGrid.getLines().size()) {
	                bestGrid = currentChildGrid;
	            }
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	        }
	    }

	    executor.shutdown();
	    
	    // Ensure to update score at the very end at the end and not for every threads
	    if (level == depthLevel) {
	    	// Save score in csv file
	    	DataManager.insertData(
	    		DataManager.getCurrRunningAlgo(),
	    		"" + Mode.getNumber() + Mode.getType(),
	    		bestGrid.getLines().size()
	   	    );
	    }
	    return bestGrid;
	}
	
	@Override
	public void trainAlgorithm(int iterations) {
		// TODO Auto-generated method stub
	}
	
	public static int getId() {
		return algoId;
	}
	
	public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        double endTime;
        double elapsedTime;
		NMCS nmcs1 = new NMCS(1);
		NMCS nmcs2 = new NMCS(2);

		NMCS nmcs3 = new NMCS(3);
		NMCS nmcs4 = new NMCS(4);
		
		
		/**                        DEPTH 1                        */
		
//		DataManager.setCurrRunningAlgo(1);
//		Grid level1 = nmcs1.algorithm();
//        System.out.println("Score pour une recherch de profondeur 1: " + level1.getLines().size());
//		endTime = System.currentTimeMillis();
//		elapsedTime = (endTime - startTime) * 0.001;
//        System.out.println("Time taken: " + elapsedTime + " seconds");
//        System.out.println("Grid found: \n" + level1);
		
		/**                        DEPTH 2                       */
        
		DataManager.setCurrRunningAlgo(2);
		Grid level2 = nmcs2.algorithm();
        System.out.println("Score pour une recherch de profondeur 2: " + level2.getLines().size());
		endTime = System.currentTimeMillis();
		elapsedTime = (endTime - startTime) * 0.001;
        System.out.println("Time taken: " + elapsedTime + " seconds");
        System.out.println("Grid found: \n" + level2);
        
		/**                        DEPTH 3                        */
		
//		DataManager.setCurrRunningAlgo(3);
//        Grid level3 = nmcs3.algorithm();
//        System.out.println("Score pour une recherch de profondeur 3: " + level3.getLines().size());
//		endTime = System.currentTimeMillis();
//		elapsedTime = (endTime - startTime) * 0.001;
//        System.out.println("Time taken: " + elapsedTime + " seconds");
//        System.out.println("Grid found: \n" + level3);
//        System.out.println("Grid found: \n" + level3);
	}
}