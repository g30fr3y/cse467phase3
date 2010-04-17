public class GeneralizationSteps {
	
	public GeneralizationSteps() {
		dataPairs = new GeneralizationStepsEntry[QuasiId.values().length];
		
		for (int i = 0; i < QuasiId.values().length; i++) {
			dataPairs[i] = new GeneralizationStepsEntry();
		}

		dataPairs[PRODUCT_ID_POSITION].setAttributeId(QuasiId.PRODUCT_ID);
		dataPairs[PRICE_POSITION].setAttributeId(QuasiId.PRICE);
		dataPairs[WEIGHT_POSITION].setAttributeId(QuasiId.WEIGHT);
		dataPairs[DEPT_ID_POSITION].setAttributeId(QuasiId.DEPT_ID);
		dataPairs[PRODUCT_YEAR_POSITION].setAttributeId(QuasiId.PRODUCT_YEAR);
		dataPairs[EXPIRE_YEAR_POSITION].setAttributeId(QuasiId.EXPIRE_YEAR);
	}
	
	// if id is already in vector, the old steps value become
	// this step value...
	public void setGenSteps(QuasiId id, int steps) {
		switch (id) {
			case DEPT_ID:
				dataPairs[DEPT_ID_POSITION].setNumSteps(steps);
				break; 
			case EXPIRE_YEAR:
				dataPairs[EXPIRE_YEAR_POSITION].setNumSteps(steps);
				break;
			case PRICE:
				dataPairs[PRICE_POSITION].setNumSteps(steps);
				break;
			case PRODUCT_ID:
				dataPairs[PRODUCT_ID_POSITION].setNumSteps(steps);
				break;
			case WEIGHT:
				dataPairs[WEIGHT_POSITION].setNumSteps(steps);
				break;
			case PRODUCT_YEAR:
				dataPairs[PRODUCT_YEAR_POSITION].setNumSteps(steps);
				break;
		}
	}
	
	public int getGenStepValue(QuasiId id) {
		switch (id) {
			case DEPT_ID:
				return dataPairs[DEPT_ID_POSITION].getNumSteps();
			case EXPIRE_YEAR:
				return dataPairs[EXPIRE_YEAR_POSITION].getNumSteps();
			case PRICE:
				return dataPairs[PRICE_POSITION].getNumSteps();
			case PRODUCT_ID:
				return dataPairs[PRODUCT_ID_POSITION].getNumSteps();
			case WEIGHT:
				return dataPairs[WEIGHT_POSITION].getNumSteps();
			case PRODUCT_YEAR:
				return dataPairs[PRODUCT_YEAR_POSITION].getNumSteps();
			default:
				return -1;
		}
	}
	
	public GeneralizationStepsEntry[] getDataPairs() {
		return this.dataPairs;
	}
	
	public String viewStepsOnly() {
		String output = "[ ";
		for (GeneralizationStepsEntry entry : dataPairs) {
			if (entry.getNumSteps() > -1) {
				output += entry.getNumSteps() + " ";				
			}
		}
		output += "]";
		return output;		
	}

	public String toString() {
		String output = "";
		for (GeneralizationStepsEntry entry : dataPairs) {
			output += entry.toString() + "\n";
		}
		return output;
	}
	
	/**
	 * This method is useful to test if one set of generalization
	 * steps dominates another.  Dominates is defined as each index
	 * of the possibleSolution is less than or equal to each index
	 * of the calling object's quasi-id list.  
	 * 
	 * @param possibleSolution	an array of quasi-id steps to test
	 * 							against.
	 * @return					True if all indices of 
	 * 							possible solution is less than or 
	 * 							equal to the quasi-id list.
	 */
	public boolean dominates(GeneralizationStepsEntry[] other) {
		int marker = 0;
		for (GeneralizationStepsEntry entry : dataPairs) {
			if (entry.getNumSteps() < other[marker].getNumSteps()) {
				return false;
			}
			marker++;
		}
		return true;
	}
	
	private GeneralizationStepsEntry[] dataPairs;
	private static int PRODUCT_ID_POSITION = 0;
	private static int PRICE_POSITION = 1;
	private static int WEIGHT_POSITION = 2;
	private static int DEPT_ID_POSITION = 3;
	private static int PRODUCT_YEAR_POSITION = 4;
	private static int EXPIRE_YEAR_POSITION = 5;
	
	private class GeneralizationStepsEntry {
		private QuasiId attributeId;
		private int numSteps;
		
		public GeneralizationStepsEntry() {
			this.attributeId = null;
			this.numSteps = -1;
		}
		
		public GeneralizationStepsEntry(QuasiId id, int numSteps) {
			this.attributeId = id;
			this.numSteps = numSteps;
		}
		
		public String toString() {
			return this.attributeId + ": " + this.numSteps;
		}

		public void setAttributeId(QuasiId attributeId) {
			this.attributeId = attributeId;
		}

		public void setNumSteps(int numSteps) {
			this.numSteps = numSteps;
		}

		public QuasiId getAttributeId() {
			return attributeId;
		}

		public int getNumSteps() {
			return numSteps;
		}
	}
}
