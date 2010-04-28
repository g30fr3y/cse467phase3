
public class GeneralizationTable {
	
	public GeneralizationTable(int kAnon, QuasiId ... list) {
		this();
		selectedIds = list;
		this.kAnon = kAnon;
		
		// set up the table if k anon is greater than 1,
		// otherwise if k is 1, then we're just returning 
		// all data untouched (or ungeneralized)		
		if (this.kAnon > 1) {
			setClusterList();
			setKTupleList();
			
			if ( !kTupleListIsEmpty ) {
				setAttributeLists();
				fillGenTable();			
			}			
		}
	}
	
	private GeneralizationTable() {
		this.clusterList = null;
		this.kTupleList = null;
		this.selectedIds = null;
		this.genTable = null;
		this.clusterListAttributes = null;
		this.kTupleAttributes = null;
		this.kAnon = 0;
		this.kTupleListIsEmpty = true;
		this.kTupleListSizes = null;
		this.clusterListSizes = null;
	}

	public boolean testSolution(GeneralizationSteps solution, int maxSuppression) {
		int suppressionCount = 0;
		if ( !kTupleListIsEmpty && (this.kAnon > 1) ) {	
			for (int i = 0; i < kTupleList.length; i++) {
				int numHits = genTable[i].testSolution(solution, i);
				if (numHits < kAnon) {
					suppressionCount++;
					if (suppressionCount > maxSuppression) {
						return false; // we hit the suppression limit, stop checking solution
					} 
					
					// If the remaining rows to check are less than the suppression
					// limit, there's no need to check them...
					int suppressionAvailable = maxSuppression-suppressionCount;
					int remainingRows = genTable.length-i;
					if (suppressionAvailable >= remainingRows) {
						return true;
					}
				}
			}	
		}
		return true;
	}
	
	public String getSuppressedProductIds(GeneralizationSteps solution) {
		String output = "";
		 
		if ( !kTupleListIsEmpty && (this.kAnon > 1) ) {	
			for (int i = 0; i < kTupleList.length; i++) {
				int numHits = genTable[i].testSolution(solution, i);
				if (numHits < kAnon) {
					// This tuple must be suppressed.  Add it to output
					output += kTupleList[i] + ",";
				}
			}	
		}
		
		return output;
	}
	
	public String toString() {
		
		if (kTupleListIsEmpty) {
			return "Table is empty";
		} else {
			String newLine = System.getProperty("line.separator");
			String output = "\t\t";
			// get cluster names
			for (String s : clusterList) {
				output += s + "  ";
			}
			output += newLine;
			// get row data
			for (int i = 0; i < kTupleList.length; i++) {
				output += kTupleList[i] + "  " + genTable[i];
				output += newLine;
			}
			return output;
		}
	}

	private void fillGenTable() {
		genTable = new GeneralizationRow[kTupleList.length];
		
		for (int i = 0; i < kTupleList.length; i++) {
			genTable[i] = new GeneralizationRow(i);
		}
	}

	// Assuming that there are more than 0 actual product ids
	private void setClusterList() {
		// get a DBManager
		DBManager dbManager = new DBManager();
		
		String allQuasiIds = getCommaSeparatedQuasiIdList();
		String alreadyMatched = "----";
		String clusters = "";
		
		// create sql to get all ProductIds
		String sql = "SELECT " + QuasiId.PRODUCT_ID.dbName + " " +
					 "FROM Student";
		
		// place all ProductIds into string array
		String[] allProductIds = dbManager.runQuery(sql);
		
		// test each id in ProductIds array to find matches.
		int productIdPtr = 0;
		while ( productIdPtr < allProductIds.length ) {
			
			// get the next productId to find samples for
			String sampleProductId = allProductIds[productIdPtr];
			allProductIds[productIdPtr] = alreadyMatched; // set to "----"
			
			// get the quasiId values for the sample
			sql = "SELECT " + allQuasiIds + " " +
				  "FROM Student " +
				  "WHERE " + QuasiId.PRODUCT_ID.getDBName() + "='" + sampleProductId + "'";	
			String[] sampleProductIdQuasiIdValues = dbManager.runQuery(sql);
			
			// get the product id(s) for everything that matches the values
			// found above
			sql = "SELECT " + QuasiId.PRODUCT_ID.getDBName() + " " +
				  "FROM Student " +
				  "WHERE " + getQuasiIdValuePairs(sampleProductIdQuasiIdValues);
			String[] matchingProducts = dbManager.runQuery(sql);
			
			// set matching productIds in allProductIds array to "----"
			// we don't want to get duplicate data and re-match again
			if (matchingProducts.length > 1) {
				for (String s : matchingProducts) {
					for (int i = 0; i < allProductIds.length; i++) {
						if (allProductIds[i].equals(s)) {
							allProductIds[i] = alreadyMatched;
							i = allProductIds.length; // found it, break the loop
						}
					}
				}
			}
			
			String subCluster = "";
			for (String s : matchingProducts) {
				subCluster += "," + s; 					// add matches to sub-cluster string...
			}
			clusters += subCluster.substring(1) + ";"; 	// ... and end this round with a ";"
			
			productIdPtr++;
			
			// iterate to the next index that is not "----" and be sure
			// to stay within length of allProductIds array
			while ( (productIdPtr < allProductIds.length) &&
					(allProductIds[productIdPtr].equals(alreadyMatched)) )  {
				productIdPtr++; // advance the pointer, index is matched already
			}
		}
		
		// close that db connection...
		dbManager.closeConnection(false);	
		
		this.clusterList = clusters.split(";");
		this.setClusterListSizes();
	}
	
	private String getQuasiIdValuePairs(String[] values) {
		String output = "";
		for (int i = 0; i < selectedIds.length; i++) {
			output += " AND " + selectedIds[i].getDBName() + "='" + values[i] + "'";
		}
		output = output.substring(5);
		return output;		
	}
	
	private String getCommaSeparatedQuasiIdList() {
		String allQuasiIds = "";
		for (QuasiId id : this.selectedIds) {
			allQuasiIds +=  "," + id.getDBName();
		}
		allQuasiIds = allQuasiIds.substring(1); // remove first comma
		return allQuasiIds;
	}
	
	private void setKTupleList() {
		String kTuples = "";		
		for (String s : clusterList) {
			if (s.split(",").length < this.kAnon) {
				kTuples += s + ";";
			}			
		}
		
		if (kTuples.isEmpty()) {
			kTupleListIsEmpty = true;
		} else {
			kTupleList = kTuples.split(";");
			this.setKTupleListSizes();
			kTupleListIsEmpty = false;			
		}
	}
	
	private void setAttributeLists() {
		clusterListAttributes = new String[clusterList.length][selectedIds.length];
		kTupleAttributes = new String[kTupleList.length][selectedIds.length];
		
		DBManager dbManager = new DBManager();
		
		String quasiIds = "";
	    for ( QuasiId id : selectedIds )
	    {
	        quasiIds += "," + id.getDBName();
	    }
	    quasiIds = quasiIds.substring( 1 );
		
		for (int i = 0; i < kTupleList.length; i++) {
			String kTupleName = kTupleList[i];
			if (kTupleName.contains(",")) {
				kTupleName = kTupleList[i].substring(0, kTupleList[i].indexOf(",")); 
			}
			String[] attributes = dbManager.runQuery( "SELECT " + quasiIds + " " +
													  "FROM Student " + 
													  "WHERE " + QuasiId.PRODUCT_ID.getDBName() + 
													  "='" + kTupleName + "'");
		
			for (int j = 0; j < selectedIds.length; j++) {
				kTupleAttributes[i][j] = attributes[j]; 
			}
		}
		
		for (int i = 0; i < clusterList.length; i++) {
			String clusterName = clusterList[i];
			if (clusterName.contains(",")) {
				clusterName = clusterList[i].substring(0, clusterList[i].indexOf(",")); 
			}
			String[] attributes = dbManager.runQuery( "SELECT " + quasiIds + " " +
													  "FROM Student " + 
													  "WHERE " + QuasiId.PRODUCT_ID.getDBName() + 
													  "='" + clusterName + "'");
			for (int j = 0; j < selectedIds.length; j++) {
				clusterListAttributes[i][j] = attributes[j];
			}
		}	
		
		dbManager.closeConnection(false);
	}
	
	private void setClusterListSizes() {
		clusterListSizes = new int[clusterList.length];
		for (int i = 0; i < clusterList.length; i++) {
			clusterListSizes[i] = clusterList[i].split(",").length;
		}
	}
	
	private void setKTupleListSizes() {
		kTupleListSizes = new int[kTupleList.length];
		for (int i = 0; i < kTupleList.length; i++) {
			kTupleListSizes[i] = kTupleList[i].split(",").length;
		}
	}

	private String[] clusterList;
	private int[] clusterListSizes;
	private String[][] clusterListAttributes;
	private String[] kTupleList;
	private int[] kTupleListSizes;
	private String[][] kTupleAttributes;
	private QuasiId[] selectedIds;
	private GeneralizationRow[] genTable;
	private int kAnon;
	private boolean kTupleListIsEmpty;
	
	
	private class GeneralizationRow {
		private GeneralizationSteps[] genStepsRow;
		
		public GeneralizationRow(int singleTupleIndex) {
			genStepsRow = new GeneralizationSteps[clusterList.length];
		}
		
		private void setup(int kTupleIndex, int genStepIndex) {
			String kTuple = kTupleList[kTupleIndex];
			genStepsRow[genStepIndex] = new GeneralizationSteps();
			if (kTuple.equals(clusterList[genStepIndex])) {
				int steps = 0;
				for (int j = 0; j < selectedIds.length; j++) {
					genStepsRow[genStepIndex].setGenSteps(selectedIds[j], steps);
				}
			} else {
				for (int j = 0; j < selectedIds.length; j++) {
					int steps = 0;
					String attribute1 = kTupleAttributes[kTupleIndex][j];
					String attribute2 = clusterListAttributes[genStepIndex][j];
					steps = Generalizer.getNumGeneralization(attribute1, attribute2, selectedIds[j]);
					genStepsRow[genStepIndex].setGenSteps(selectedIds[j], steps);
				}					
			}			
		}
		
		public int testSolution(GeneralizationSteps solution, int kTuplePosition) {
			int numSolutions = 0;
			
			for (int i = 0; i < genStepsRow.length; i++) {
				
				if (genStepsRow[i] == null) {
					setup(kTuplePosition, i);
				}
				
				
				if (solution.dominates(genStepsRow[i].getAllInfoLossLevels())) {
					numSolutions += clusterListSizes[i];	// cluster could be more than one
					numSolutions += kTupleListSizes[kTuplePosition];
				}
				
				// If the number of solutions found is greater than or equal
				// to the desired k anon, stop testing solutions, we're good
				// to go.
				if (numSolutions >= kAnon) {
					i = genStepsRow.length;
				}
			}
			
			return numSolutions;		
		}
		
		public String toString() {
			String output = "";
			for (int i = 0; i < genStepsRow.length; i++) {
				if (genStepsRow[i] == null) {
					output += "[null]\t";
				} else {
					output += genStepsRow[i].toString() + "\t";					
				}
			}
			return output;
		}
	}
}
