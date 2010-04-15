import java.util.Scanner;


public class QuasiIdentifiers {
	
	/**
	 * Default constructor sets all quasi-id positions in 
	 * the quasiIdList to -1 which means there are no 
	 * quasi-ids currently selected.
	 */
	public QuasiIdentifiers() {
		quasiIdList = new int[TOTAL_QUASI_IDS_OPTIONS];
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			this.quasiIdList[i] = -1;
		}
	}
	
	/**
	 * This constructor allows an integer array representation of 
	 * the desired quasi-id(s) to be passed as an argument and the
	 * class's own quasidIdList field is set to resemble this array.
	 * 
	 * @param mask	This is an integer array with the appropriate
	 * 				quasi-id positions that the user wishes to 
	 *  			use set to the value 1.  
	 */
	public QuasiIdentifiers(int[] mask) {
		this();
		if (mask.length == TOTAL_QUASI_IDS_OPTIONS) {
			for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
				this.quasiIdList[i] = mask[i];
			}			
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			output += QUASI_ID_TAGS[i] + ": " + quasiIdList[i] + "\n";
		}
		return output;
	}
	
//	/**
//	 * This method takes an integer representing a quasi-id position 
//	 * in the quasiIdList and sets that position to the given value. 
//	 * Will be useful when setting maximum generalization values and 
//	 * other functions later.
//	 * 
//	 * @param quasiId	Must be an int between 0 and 5.  Should use
//	 * 					the static ints defined for each quasi-id
//	 * 					position in the class.
//	 * @param value		New value to set the quasi-id to.
//	 */
//	public void setQuasiIdValue(int quasiId, int value) {
//		if (quasiId < 6 && quasiId > -1) {
//			quasiIdList[quasiId] = value;			
//		}
//	}
	
	/**
	 * This method takes an integer representing a quasi-id position 
	 * in the quasiIdList and gets that position's current value. 
	 * 
	 * @param quasiId	Must be an int between 0 and 5.  Should use
	 * 					the static ints defined for each quasi-id
	 * 					position in the class.
	 */	
	public int getQuasiIdValue(int quasiId) {
		if (quasiId < 6 && quasiId > -1) {
			return quasiIdList[quasiId];			
		} else {
			return -1;
		}
	}
	
	/**
	 * @return	A deep copy of the quasiIdList.
	 */
	public int[] getQuasiIdList() {
		int[] list = new int[TOTAL_QUASI_IDS_OPTIONS];
		for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
			list[i] = quasiIdList[i];
		}
		return list;
	}

	/**
	 * This is a useful method to produce a quasi-id mask through
	 * the console.  This logic could be easily duplicated in a 
	 * GUI though.  
	 * 
	 * @return	a int[] representing the mask that the user wishes
	 * 			to use.
	 */
	public static int[] createQuasiIdMask() {
		int[] mask = new int[TOTAL_QUASI_IDS_OPTIONS];
		Scanner input = new Scanner(System.in);
		boolean finished = false;
		
		while ( !finished ) {
			System.out.print("Select the desired quasi-identifiers or ");
			System.out.print("type 6 to finish.\n");
			for (int i = 0; i < TOTAL_QUASI_IDS_OPTIONS; i++) {
				System.out.println(i + ") " + QUASI_ID_TAGS[i]);
			}
			
			
			// TODO: Get rid of those magic numbers
			try {
				int selection = input.nextInt();
				if (selection == 6) {
					finished = true;
				} else if (selection > -1 && selection < 6) {
					System.out.println(QUASI_ID_TAGS[selection] + " chosen.");
					mask[selection] = 1;
				} else {
					System.out.println("Input not recognized.");					
				}					
			} catch (Exception e) {
				System.out.println("Input not recognized.");
				input = new Scanner(System.in);
			}
		}
		return mask;
	}

	// We store the quasi-identifiers in an int array.  All values
	// are set to -1 by default which means that there are no 
	// quasi-identifier(s) set.  Each quasi-identifier that is set
	// will become a positive 1.  
	// EX: suppose you want to set product id to be the only quasi-id.
	// Set quasiIdList[PRODUCT_ID] to 1.
	// Then quasiIdList would look like { 1,-1,-1,-1,-1,-1 }.  
	private int[] quasiIdList;
	
	// These static ints represent the position of the quasi-id
	// in the quasidIdList array.
	public static final int PRODUCT_ID = 0;
	public static final int PRICE = 1;
	public static final int DEPT_ID = 2;
	public static final int WEIGHT = 3;
	public static final int PRODUCT_YEAR = 4;
	public static final int EXPIRE_YEAR = 5;
	
	// These strings are just convenience strings for output.
	public static final String[] QUASI_ID_TAGS = { "PRODUCT_ID",
												   "PRICE",
												   "DEPT_ID",
												   "WEIGHT",
												   "PRODUCT_YEAR",
												   "EXPIRE_YEAR" };
	
	public static final int TOTAL_QUASI_IDS_OPTIONS = 6;
}
