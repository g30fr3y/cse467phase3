/**
 * This is really the QuasiIdentifiers class with each non-negative
 * position representing the number of generalization steps for 
 * that position. I only sub-classed it to create a distinction 
 * between the two classes and make a prettier toString() for it.
 * This is where the dominates() method happens also.
 *
 */
public class GeneralizationSteps extends QuasiIdentifiers {
	
	public GeneralizationSteps() {
		productId = -1;
		price = -1;
		weight = -1;
		deptId = -1;
		productYear = -1;
		expireYear = -1;
	}
	
	public int getQuasiId(QuasiId id) {
		switch (id) {
			case DEPT_ID:
				return this.deptId;
			case EXPIRE_YEAR:
				return this.expireYear;
			case PRICE:
				return this.price;
			case PRODUCT_ID:
				return this.productId;
			case PRODUCT_YEAR:
				return this.productYear;
			case WEIGHT:
				return this.weight;
			default:
				return -1;
		}	
	}
	
	public void setQuasiId(QuasiId id, int value) {
		switch (id) {
		case DEPT_ID:
			this.deptId = value;
			break;
		case EXPIRE_YEAR:
			this.expireYear = value;
			break;
		case PRICE:
			this.price = value;
			break;
		case PRODUCT_ID:
			this.productId = value;
			break;
		case PRODUCT_YEAR:
			this.productYear = value;
			break;
		case WEIGHT:
			this.weight = value;
			break;
		}		
	}
	
	private int[] getSelected() {
		int[] selected = new int[QuasiId.values().length];
		int marker = 0;
		for (QuasiId id : QuasiId.values()) {
			selected[marker] = this.getQuasiId(id);
			marker++;
		}
		return selected;		
	}
	
	public boolean equals(GeneralizationSteps other) {
		if ( !(other instanceof GeneralizationSteps) ) {
			return false;
		} else {
			int[] values1 = this.getSelected();
			int[] values2 = other.getSelected();
			for (int i = 0; i < QuasiId.values().length; i++) {
				if ( !(values1[i] == values2[i]) ) {
					return false;
				}
			}
			return true;
		}
	}
	
	public String toString() {
		String output = "[ ";
		if (productId > -1) {
			output += QuasiId.PRODUCT_ID + " ";
		}
		if (deptId > -1) {
			output += QuasiId.DEPT_ID + " ";
		}
		if (price > -1) {
			output += QuasiId.PRICE + " ";
		}
		if (weight > -1) {
			output += QuasiId.WEIGHT + " ";
		}
		if (productYear > -1) {
			output += QuasiId.PRODUCT_YEAR + " ";
		}
		if (expireYear > -1) {
			output += QuasiId.EXPIRE_YEAR + " ";
		}
		output += "]";
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
	public boolean dominates(GeneralizationSteps other) {
		return false;
	}
	
	private int productId;
	private int price;
	private int weight;
	private int deptId;
	private int productYear;
	private int expireYear;
}
