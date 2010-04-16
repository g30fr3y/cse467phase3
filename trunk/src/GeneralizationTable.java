import java.util.ArrayList;


public class GeneralizationTable {
	
	public GeneralizationTable(QuasiId ... list) {
		this.genTable = new ArrayList<GeneralizationCell>();
		this.selectedIds = list;
		this.setClusterList();
		this.setSingleTupleList();
//		this.fillGenTable();
	}

	// some optimizations can happen here.  this is just initial code
	public boolean testSolution(GeneralizationSteps solution,
								int kAnonymity,
								int maxSuppression,
								boolean getTableData) {
//		DBManager dbManager = new DBManager();
		int suppressionCount = 0;
		int lineKanonymity = 0;
		String currentRow = genTable.get(0).getSingleTuple();
		for (GeneralizationCell gc : genTable) {
			if (gc.getSingleTuple().equals(currentRow)) {
				if (solution.dominates(gc.getGeneralizationSteps().getDataPairs())) {
					lineKanonymity++;
				}
			} else {
				// starting a new row
				if (lineKanonymity < kAnonymity) {
					// we didn't meet the kanon for the last row
					suppressionCount++;
					if (suppressionCount > maxSuppression) {
//						dbManager.closeConnection(false);
						return false;	// we broke the max suppression limit
					}
				}
				
				// we're still going if we made it thru that...
				// reset and start the next row
				currentRow = gc.getSingleTuple();
				lineKanonymity = 0;
				if (solution.dominates(gc.getGeneralizationSteps().getDataPairs())) {
					lineKanonymity++;
				}
				
			}
		}
//		dbManager.closeConnection(false);
		return true;
	}
	
	// more formatting can happen here...
	public String toString() {
		String output = "";
		output += "\t";
		
		// getting the header info...
		for (String s : this.clusterList) {
			output += s + "\t";
		}
		
		output += "\n";
		
		// getting the cells...
		String currentRow = genTable.get(0).getSingleTuple();
		output += currentRow + "  ";
		for (GeneralizationCell gc : genTable) {
			if (gc.getSingleTuple().equals(currentRow)) {
				output += gc.toString() + "  ";
			} else {
				currentRow = gc.getSingleTuple();
				output += "\n" + currentRow + "  ";
				output += gc.toString() + "  ";
			}
		}
		
		return output;
	}

	private void fillGenTable() {
		for (String s : clusterList) {
			if ( !(s.contains(",")) ) {		// we only want single tuples here...
				fillGenRow(s);
			}
		}
	}
	
	private void fillGenRow(String singleTuple) {
		for (String s : clusterList) {
			genTable.add(new GeneralizationCell(singleTuple, s, selectedIds));
		}
	}

	private void setClusterList() {
		// get a DBManager
		DBManager dbManager = new DBManager();
		
		String allQuasiIds = getCommaSeparatedQuasiIdList();
		String alreadyMatched = "----";
		
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
				  "WHERE " + getQuasiIdValuePairs(sampleProductIdQuasiIdValues, sampleProductId);
			String[] matchingProducts = dbManager.runQuery(sql);
			
			// set matching productIds in allProductIds array to "----"
			// we don't want to get duplicate data and re-match again
			if (matchingProducts != null) {
				for (String s : matchingProducts) {
					for (int i = 0; i < allProductIds.length; i++) {
						if (allProductIds[i].equals(s)) {
							allProductIds[i] = alreadyMatched;
							i = allProductIds.length; // found it, break the loop
						}
					}
				}
			}

			if (matchingProducts != null) {
				for (int i = 0; i < matchingProducts.length; i++) {
					System.out.println(sampleProductId + " " + matchingProducts[i]);
				}				
			}
			
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
	}
	
	private String getQuasiIdValuePairs(String[] values, String samplePid) {
		String output = "";
		for (int i = 0; i < selectedIds.length; i++) {
			output += " AND " + selectedIds[i].getDBName() + "='" + values[i] + "'";
		}
		output += " AND " + QuasiId.PRODUCT_ID.getDBName() + "!='" + samplePid + "'";
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
		// TODO Auto-generated method stub
		
	}
	
	private String[] clusterList;
	private String[] singleTupleList;
	private QuasiId[] selectedIds;
	private ArrayList<GeneralizationCell> genTable;
	
	
	private class GeneralizationCell {
		private String singleTuple;
		private String targetCluster;
		private GeneralizationSteps genSteps;
		
		public GeneralizationCell(String singleTuple, 
								  String targetCluster,
								  QuasiId[] list) {
			this.singleTuple = singleTuple;
			this.targetCluster = targetCluster;
			this.setGeneralizationSteps(list);
		}
		
		public String getSingleTuple() {
			return singleTuple;
		}
		
		private void setGeneralizationSteps(QuasiId[] list) {
			// TODO: finish method
			// 		 this method will take a little time to figure out
			genSteps = new GeneralizationSteps();
			for (QuasiId id : list) {
				// determine generalization needed for each id in list
				int numSteps = 2;
				genSteps.setGenSteps(id, numSteps);
			}
		}
		
		public GeneralizationSteps getGeneralizationSteps() {
			return genSteps;
		}
		
		public String toString() {
			return genSteps.viewStepsOnly();
		}
	}
}
