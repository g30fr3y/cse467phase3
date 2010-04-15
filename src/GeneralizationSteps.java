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
		String output = "[ ";
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			if (this.getQuasiIdValue(i) > -1) {
				output += this.getQuasiIdValue(i) + " ";				
			}
		}
		output += "]";
		return output;
	}
	
	public boolean dominates(int[] possibleSolution) {
		// TODO: finish method
		return false;
	}
}
