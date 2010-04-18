/*
 * Here are the general steps to accomplish Samarati's algorithm:
 * 1) Get quasi-identifiers, maximum suppression value, k
 *    anonymity value.
 * 2) Get maximum generalization steps for each quasi-identifier.
 * 3) Use those values from step 2 to get the lattice height.
 * 4) Create a generalization table.
 * 5) Perform the actual binary search portion of the algorithm.
 */
public class Samarati {	

	public Samarati(QuasiId ... list) {
		quasiIdList = list;
		setLatticeHeight(list);
	}
	
	// TODO: A lot more work is needed here, work in progress...
	// 		 This is the method required in the instructions...
	public static String kanon(int kAnon, int maxSup, QuasiId ... list) {
		// get the start time...
    	long startTime = System.currentTimeMillis();
		
		// Create a Samarati object and initialize the quasi-id(s)
	    Samarati sam = new Samarati(list);
	    
		// Create a generalization table with the
		// k-anonymity and maximum suppression values.
	    GeneralizationTable genTable;
	    genTable = new GeneralizationTable(list);
	    
	    

	    GeneralizationSteps solution = new GeneralizationSteps();
		// Perform the actual binary search portion of the algorithm.
	    boolean foundIt = false;
	    while ( !foundIt ) {
		    // make a GeneralizationSteps object...
		    solution = new GeneralizationSteps();
		    
		    // place your guessed solution in an int array...
		    int[] possibleSolution = new int[list.length];
		    
		    // set your GeneralizationSteps to have the values you guessed
		    for (int i = 0; i < list.length; i++) {
		    	solution.setGenSteps(list[i], possibleSolution[i]);
		    }
		    
		    // test the solution...
			// did you find it?  wash, rinse, repeat...
		    foundIt = genTable.testSolution(solution, kAnon, maxSup, false);
	    }
		
	    
	    
	    
		// get the end time...
		long endTime = System.currentTimeMillis();
	    String totalTime = (endTime-startTime)/1000.0 + " seconds";

		// Return a string representation of the generalized data/execution time
	    String data = Generalizer.getGeneralizedData(solution);
	    return data + "\n" + totalTime;	
	}

	/**
	 * The lattice isn't needed, but we still need to know the number
	 * of levels (or height) of the lattice.  This method uses a simple
	 * algorithm to determine the height of the lattice.  The max 
	 * generalizations possible for each attribute is needed to 
	 * calculate the height.  
	 */
	private void setLatticeHeight(QuasiId ... list) {
		int levels = 0;

		for (QuasiId id : list) {
			levels = levels + (id.getMaxGen()-1);
		}
		this.latticeHeight = levels+1;
	}
	
	private int latticeHeight;
	private QuasiId[] quasiIdList;
}
