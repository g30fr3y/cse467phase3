import java.util.Vector;

/**
 * This class describes an object that will hold the number of steps
 * of information loss for each quasi-identifier.  
 * 
 */
public class GeneralizationSteps {
	
	/**
	 * The default constructor initializes each index of the dataPair array to
	 * be -1, which means that the quasi-id is not enabled.  
	 */
	public GeneralizationSteps() {
		infoLossLevel = new int[QuasiId.values().length];
		
		for (int i = 0; i < QuasiId.values().length; i++) {
			infoLossLevel[i] = NOT_ENABLED;
		}
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
	public boolean dominates(int[] other) {
		int marker = 0;
		for (int entry : infoLossLevel) {
			if (entry < other[marker]) {
				return false;
			}
			marker++;
		}
		return true;
	}

	/**
	 * This method sets the number of levels of information loss 
	 * for a given quasi-identifier.
	 * 
	 * @param id		The quasi-identifier to set the information loss
	 * 					level for.
	 * @param steps		The value representing the number of levels of 
	 * 					information loss.
	 */
	public void setGenSteps(QuasiId id, int steps) {
		switch (id) {
			case DEPT_ID:
				infoLossLevel[QuasiId.DEPT_ID.getPosition()] = steps;
				break; 
			case EXPIRE_YEAR:
				infoLossLevel[QuasiId.EXPIRE_YEAR.getPosition()] = steps;
				break;
			case PRICE:
				infoLossLevel[QuasiId.PRICE.getPosition()] = steps;
				break;
			case PRODUCT_ID:
				infoLossLevel[QuasiId.PRODUCT_ID.getPosition()] = steps;
				break;
			case WEIGHT:
				infoLossLevel[QuasiId.WEIGHT.getPosition()] = steps;
				break;
			case PRODUCT_YEAR:
				infoLossLevel[QuasiId.PRODUCT_YEAR.getPosition()] = steps;
				break;
		}
	}
	
	/**
	 * The is a "getter" method to obtain the value set for the 
	 * number of levels of information loss for a given quasi-id.
	 * NOTE: the returned value of -1 means the quasi-identifier
	 * in question is not enabled.
	 * 
	 * @param id	The quasi-identifier to check the information
	 * 				loss level for.
	 * @return		The number of levels of information loss for
	 * 				this quasi-identifier.
	 */
	public int getGenStepValue(QuasiId id) {
		switch (id) {
			case DEPT_ID:
				return infoLossLevel[id.getPosition()];
			case EXPIRE_YEAR:
				return infoLossLevel[id.getPosition()];
			case PRICE:
				return infoLossLevel[id.getPosition()];
			case PRODUCT_ID:
				return infoLossLevel[id.getPosition()];
			case WEIGHT:
				return infoLossLevel[id.getPosition()];
			case PRODUCT_YEAR:
				return infoLossLevel[id.getPosition()];
			default:
				return -1;
		}
	}
	
	/**
	 * This method returns an array of QuasiId objects for which
	 * this class is enabled to use.  
	 * NOTE: An enabled quasi-id has a value greater than -1.
	 * 
	 * @return	Array of enabled QuasiId(s)
	 */
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

	/**
	 * This method returns a deep copy of the values contained in 
	 * the infoLossLevel array.  
	 * 
	 * @return	All values in the infoLossLevel array.
	 */
	public int[] getAllInfoLossLevels() {
		int[] data = new int[QuasiId.values().length];
		for (int i = 0; i < infoLossLevel.length; i++) {
			data[i] = infoLossLevel[i];
		}
		return data;
	}

	
	/**
	 * 
	 * The toString() method for this class will only return the 
	 * enabled quasi-ids within "[" and "]".
	 * Returning the enabled ids only is for readability.
	 * 
	 * @return 		A string enclosed by "[" and "]" containing
	 * 				the enabled quasi-ids for this object.
	 * 
	 */
	public String toString() {
		String output = "[ ";
		for (int entry : infoLossLevel) {
			if (entry > NOT_ENABLED) {
				output += entry + " ";				
			}
		}
		output += "]";
		return output;	
	}

	/**
	 * Private helper method to determine if the quasi-id in question
	 * is actually enabled.
	 * NOTE: An enabled quasi-identifier is greater than -1.  
	 * 
	 * @param id	The quasi-identifier to check.
	 * @return		True if enabled (value greater than -1),
	 * 				false otherwise.
	 */
	private boolean isEnabled(QuasiId id) {
		switch(id) {
			case DEPT_ID:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
			case EXPIRE_YEAR:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
			case PRICE:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
			case PRODUCT_ID:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
			case PRODUCT_YEAR:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
			case WEIGHT:
				if (infoLossLevel[id.getPosition()] > NOT_ENABLED) {
					return true;
				}
				break;
		}
		return false;
	}

	private int[] infoLossLevel;
	private final int NOT_ENABLED = -1;
}
