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
	
	/**
	 * Our constructor takes the three arguments given in the 
	 * class instructions: quasi-identifiers, maximum suppression 
	 * value, and k anonymity value.  
	 * 
	 * @param quasiIdMask	Assuming a comma separated list of quasi-id(s)
	 */
	public Samarati(int[] quasiIdMask) {
		this.setQuasiIdList(quasiIdMask);
		this.setLatticeHeight();
	}
	
	// TODO: A lot more work is needed here, work in progress...
	// 		 This is the method required in the instructions...
	public static String kanon(int[] quasiIdMask, int kAnon, int maxSup) {
		DBManager dbManager = new DBManager("");
		// get the start time...
		
		// Create a Samarati object and initialize the quasi-id(s)
	    Samarati sam = new Samarati(quasiIdMask);
	    
		// Create a generalization table with the
		// k-anonymity and maximum suppression values.
	    GeneralizationTable genTable = new GeneralizationTable(
	    								sam.getQuasiIdList(), dbManager);
	    
		// Perform the actual binary search portion of the algorithm.
	    // The GeneralizationTable method testSolution() will be useful here
		
		// get the end time...
		
		// Return a string representation of the generalized data
		// with the execution time.
	    dbManager.closeConnection(true);
		return "It doesn't work yet";		
	}
	
	/**
	 * We store the quasi-identifiers in an int array.  All values
	 * are set to -1 by default which means that there are no 
	 * quasi-identifier(s) set.  Each quasi-identifier that is set
	 * will become a positive 1.  
	 * EX: suppose you want to set product id to be the only quasi-id.
	 * Set quasiIdList[PRODUCT_ID] to 1.
	 * Then quasiIdList would look like { 1,-1,-1,-1,-1,-1 }.
	 *  
	 * @param quasiIdMask	An int array representing the desired
	 * 						quasi-identifiers to use.
	 */
	private void setQuasiIdList(int[] quasiIdMask) {
		quasiIdList = new QuasiIdentifiers(quasiIdMask);
	}

	/**
	 * @return	Returns a deep copy of the int array representing
	 * 			the selected quasi-identifiers.  
	 */
	public int[] getQuasiIdList() {
		return this.quasiIdList.getQuasiIdList();
	}

	/**
	 * Return the maximum number of generalizations that would
	 * be possible to perform on a given quasi-identifier.  
	 * 
	 * @param quasiId
	 * @return
	 */
	private int getMaxGeneralizations(int quasiId) {
		// TODO 
		// We need to determine a way to discover the maximum 
		// generalizations allows for the quasi-identifier passed 
		// to this method.
		
		// Maybe we can hard-code this ahead of time.  I'm not sure 
		// how to determine this, so we may have to scrap this path...
		switch (quasiId) {
			case QuasiIdentifiers.PRODUCT_ID:
				return 0;
			case QuasiIdentifiers.PRICE:
				return 0;
			case QuasiIdentifiers.DEPT_ID:
				return 0;
			case QuasiIdentifiers.WEIGHT:
				return 0;
			case QuasiIdentifiers.EXPIRE_YEAR:
				return 0;
			case QuasiIdentifiers.PRODUCT_YEAR:
				return 0;
			default:
				return 0;
		}
	}

	/**
	 * The lattice isn't needed, but we still need to know the number
	 * of levels (or height) of the lattice.  This method uses a simple
	 * algorithm to determine the height of the lattice.  The max 
	 * generalizations possible for each attribute is needed to 
	 * calculate the height.  
	 * 
	 * This method iterates through each position in the quasi-id array
	 * to see if it is selected.  If the it is selected (value at the 
	 * position should be greater than -1), we call the method to get
	 * the max generalization for that position.  
	 */
	private void setLatticeHeight() {
		int levels = 0;
		for (int i = 0; i < QuasiIdentifiers.TOTAL_QUASI_IDS_OPTIONS; i++) {
			if (quasiIdList.getQuasiIdValue(i) > -1) {
				int maxGeneralizations = getMaxGeneralizations(i);
				levels = levels + (maxGeneralizations-1);
			}
		}
		this.latticeHeight = levels+1;
	}
	
	private int latticeHeight;
	private QuasiIdentifiers quasiIdList;
}
