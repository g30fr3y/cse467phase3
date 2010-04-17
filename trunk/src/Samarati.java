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
		
		// Create a Samarati object and initialize the quasi-id(s)
	    Samarati sam = new Samarati(list);
	    
		// Create a generalization table with the
		// k-anonymity and maximum suppression values.
	    GeneralizationTable genTable;
	    genTable = new GeneralizationTable(list);
	    
	    
	    
		// Perform the actual binary search portion of the algorithm.
//	    while (the solution is not found...) {
//		    // make a GeneralizationSteps object...
//		    GeneralizationSteps solution = new GeneralizationSteps();
//		    
//		    // place your guessed solution in an int array...
//		    int[] possibleSolution = new int[list.length];
//		    
//		    // set your GeneralizationSteps have the values you guessed
//		    for (int i = 0; i < list.length; i++) {
//		    	solution.setGenSteps(list[i], possibleSolution[i]);
//		    }
//		    
//		    // test the solution...
//		    genTable.testSolution(solution, kAnon, maxSup, false);
//			
//			// did you find it?  wash, rinse, repeat...
//	    }
		
	    
	    
	    
		// get the end time...

		// Return a string representation of the generalized data
		// with the execution time.
		return "It doesn't work yet";		
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
