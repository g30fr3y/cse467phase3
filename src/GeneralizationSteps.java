/**
 * This is really the QuasiIdentifiers class with each non-negative
 * position representing the number of generalization steps for 
 * that position. I only sub-classed it to create a distinction 
 * between the two classes and make a prettier toString() for it.
 * This is where the dominates() method happens also.
 *
 */
public class GeneralizationSteps extends QuasiIdentifiers {
	
	public GeneralizationSteps(int[] mask) {
		super(mask);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = "[";
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			if (this.getQuasiIdValue(i) > -1) {
				String cell = String.format("%2s", getQuasiIdValue(i));
				output += cell;				
			}
		}
		output += " ]";
		return output;
	}
	
	/**
	 * This method is useful to test if one set of generalization
	 * steps dominates another.  Dominates is defined as each index
	 * of the possibleSolution is less than or equal to each index
	 * of the calling object's quasi-id list.  
	 * 
	 * @param possibleSolution	int array of quasi-id steps to test
	 * 							against.
	 * @return					True if all indices of 
	 * 							possibleSolution is less than or 
	 * 							equal to the quasi-id list.
	 */
	public boolean dominates(int[] possibleSolution) {
		int[] list = getQuasiIdList();
		
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			if (list[i] < possibleSolution[i]) {
				return false;
			}
		}
		return true;
	}
}
