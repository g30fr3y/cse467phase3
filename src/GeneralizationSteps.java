import java.util.Vector;

public class GeneralizationSteps {
	
	public GeneralizationSteps() {
		dataPairs = new GeneralizationStepsEntry[QuasiId.values().length];
		
		for (int i = 0; i < QuasiId.values().length; i++) {
			dataPairs[i] = new GeneralizationStepsEntry();
		}

		dataPairs[QuasiId.PRODUCT_ID.getPosition()].setAttributeId(QuasiId.PRODUCT_ID);
		dataPairs[QuasiId.PRICE.getPosition()].setAttributeId(QuasiId.PRICE);
		dataPairs[QuasiId.WEIGHT.getPosition()].setAttributeId(QuasiId.WEIGHT);
		dataPairs[QuasiId.DEPT_ID.getPosition()].setAttributeId(QuasiId.DEPT_ID);
		dataPairs[QuasiId.PRODUCT_YEAR.getPosition()].setAttributeId(QuasiId.PRODUCT_YEAR);
		dataPairs[QuasiId.EXPIRE_YEAR.getPosition()].setAttributeId(QuasiId.EXPIRE_YEAR);
	}
	
	// if id is already in vector, the old steps value become
	// this step value...
	public void setGenSteps(QuasiId id, int steps) {
		switch (id) {
			case DEPT_ID:
				dataPairs[QuasiId.DEPT_ID.getPosition()].setNumSteps(steps);
				break; 
			case EXPIRE_YEAR:
				dataPairs[QuasiId.EXPIRE_YEAR.getPosition()].setNumSteps(steps);
				break;
			case PRICE:
				dataPairs[QuasiId.PRICE.getPosition()].setNumSteps(steps);
				break;
			case PRODUCT_ID:
				dataPairs[QuasiId.PRODUCT_ID.getPosition()].setNumSteps(steps);
				break;
			case WEIGHT:
				dataPairs[QuasiId.WEIGHT.getPosition()].setNumSteps(steps);
				break;
			case PRODUCT_YEAR:
				dataPairs[QuasiId.PRODUCT_YEAR.getPosition()].setNumSteps(steps);
				break;
		}
	}
	
	public int getGenStepValue(QuasiId id) {
		switch (id) {
			case DEPT_ID:
				return dataPairs[id.getPosition()].getNumSteps();
			case EXPIRE_YEAR:
				return dataPairs[id.getPosition()].getNumSteps();
			case PRICE:
				return dataPairs[id.getPosition()].getNumSteps();
			case PRODUCT_ID:
				return dataPairs[id.getPosition()].getNumSteps();
			case WEIGHT:
				return dataPairs[id.getPosition()].getNumSteps();
			case PRODUCT_YEAR:
				return dataPairs[id.getPosition()].getNumSteps();
			default:
				return -1;
		}
	}
	
	public GeneralizationStepsEntry[] getDataPairs() {
		return this.dataPairs;
	}
	
	private boolean isEnabled(QuasiId id) {
		switch(id) {
			case DEPT_ID:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
			case EXPIRE_YEAR:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
			case PRICE:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
			case PRODUCT_ID:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
			case PRODUCT_YEAR:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
			case WEIGHT:
				if (dataPairs[id.getPosition()].getNumSteps() > -1) {
					return true;
				}
				break;
		}
		return false;
	}
	
	public QuasiId[] getEnabledQuasiIds() {
		Vector<QuasiId> ids = new Vector<QuasiId>();
		for (QuasiId id : QuasiId.values()) {
			if (this.isEnabled(id)) {
				ids.add(id);
			}
		}
		
		QuasiId[] enabledIds = new QuasiId[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			enabledIds[i] = ids.get(i);
		}
		
		return enabledIds;		
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
