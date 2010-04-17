
public class GeneralizationTable {
	
	public GeneralizationTable(QuasiId ... list) {
		this.selectedIds = list;
		this.setClusterList();
		this.setSingleTupleList();
		this.fillGenTable();
	}

	public boolean testSolution(GeneralizationSteps solution,
								int kAnonymity,
								int maxSuppression,
								boolean getTableData) {
		int suppressionCount = 0;
		for (GeneralizationRow gr : genTable) {
			int numHits = gr.testSolution(solution);
			if (numHits < kAnonymity) {
				suppressionCount++;
				if (suppressionCount > maxSuppression) {
					return false; // we hit the suppression limit, stop checking solution
				}
			}
		}
		
		return true;
	}
	
	public String toString() {
		String output = "\t\t";
		
		// get cluster names
		for (String s : clusterList) {
			output += s + "  ";
		}

		output += "\n";
		
		// get row data
		for (int i = 0; i < singleTupleList.length; i++) {
			output += singleTupleList[i] + "  " + genTable[i];
			output += "\n";
		}
		
		return output;
	}

	private void fillGenTable() {
		genTable = new GeneralizationRow[singleTupleList.length];
		
		for (int i = 0; i < singleTupleList.length; i++) {
			genTable[i] = new GeneralizationRow(singleTupleList[i], selectedIds);
		}
	}

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
		
//		// test output
//		for (int i = 0; i < allProductIds.length; i++) {
//			System.out.println(allProductIds[i]);
//		}
		
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

//			System.out.println("values for " + sampleProductId);
//			for (String s : sampleProductIdQuasiIdValues) {
//				System.out.println(s);
//			}
			
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

//			// let's see what the matches were...
//			for (int i = 0; i < matchingProducts.length; i++) {
//				System.out.println(sampleProductId + " " + matchingProducts[i]);
//			}	
			
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
		
//		// just checking that all ids got checked...
//		for (String s : allProductIds) {
//			System.out.println(s);
//		}
		
		// close that db connection...
		dbManager.closeConnection(false);	
		
		// put all our new clusters into the class's clusterList array
		this.clusterList = clusters.split(";");
		
//		// view the clusters
//		for (String s : this.clusterList) {
//			System.out.println(s);
//		}
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
	
	private void setSingleTupleList() {
		String singles = "";
		for (String s : clusterList) {
			if ( !(s.contains(","))) {
				singles += s + ";";
			}
		}
		singleTupleList = singles.split(";");
		
//		// check the single tuple list...
//		System.out.println("...");
//		for (String s : this.singleTupleList) {
//			System.out.println(s);
//		}
	}
	
	private String[] clusterList;
	private String[] singleTupleList;
	private QuasiId[] selectedIds;
	private GeneralizationRow[] genTable;
	
	
	private class GeneralizationRow {
		private GeneralizationSteps[] genStepsRow;
		
		public GeneralizationRow(String singleTuple, QuasiId[] list) {
			genStepsRow = new GeneralizationSteps[clusterList.length];
			setGeneralizationStepsRow(singleTuple, list);
		}
		
		private void setGeneralizationStepsRow(String singleTuple, QuasiId[] list) {
			DBManager dbManager = new DBManager();
			for (int i = 0; i < clusterList.length; i++) {
				genStepsRow[i] = new GeneralizationSteps();
				for (QuasiId id : list) {
//					System.out.println(singleTuple);
//					System.out.println(clusterList[i]);
					int steps = 0;
					if (singleTuple.equals(clusterList[i])) {
						steps = 0;
					} else {
						String[] attributes = dbManager.runQuery(setQuery(id,singleTuple,clusterList[i]));
						steps = Generalizer.getNumGeneralization(attributes[0], attributes[1], id);
					}
					genStepsRow[i].setGenSteps(id, steps);
				}				
			}
			dbManager.closeConnection(false);
		}
		
		private String setQuery(QuasiId id, String singleTuple, String cluster) {
			String clusterName = cluster;
			if (cluster.contains(",")) {
				clusterName = cluster.substring(0, cluster.indexOf(",")); 
			}
			return "SELECT " + id.getDBName() + " " +
			 	   "FROM Student " +
			       "WHERE " + QuasiId.PRODUCT_ID.getDBName() + "='" + singleTuple + "' OR " +
			            QuasiId.PRODUCT_ID.getDBName() + "='" + clusterName + "'";
		}
		
		public int testSolution(GeneralizationSteps solution) {
			int numSolutions = 0;
			
			for (int i = 0; i < genStepsRow.length; i++) {
				if (solution.dominates(genStepsRow[i].getDataPairs())) {
					numSolutions += clusterList[i].split(",").length;	// cluster could be more than one
				}
			}
			
			return numSolutions-1; // one data pair is always zeros, not a valid solution			
		}
		
		public String toString() {
			String output = "";
			for (GeneralizationSteps gs : genStepsRow) {
				output += gs.viewStepsOnly() + "\t";
			}
			return output;
		}
	}
}
