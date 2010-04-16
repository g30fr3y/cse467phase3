import java.util.ArrayList;


public class GeneralizationTable {
	
	public GeneralizationTable(DBManager dbManager, QuasiId ... list) {
//		this.dbManager = dbManager;
		this.genTable = new ArrayList<GeneralizationCell>();
		this.selectIds = list;
		this.setClusterList();
		this.fillGenTable();
	}
	
	// some optimizations can happen here.  this is just initial code
	public boolean testSolution(GeneralizationSteps solution,
								int kAnonymity,
								int maxSuppression,
								boolean getTableData) {
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
			genTable.add(new GeneralizationCell(singleTuple, s, selectIds));
		}
	}

	private void setClusterList() {
		// TODO: finish method
		this.clusterList = new String[6];
		for (int i = 0; i < 6; i++) {
			clusterList[i] = "t" + i;
		}
	}
	
	private String[] clusterList;
	private QuasiId[] selectIds;
	private ArrayList<GeneralizationCell> genTable;
	private DBManager dbManager;

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
