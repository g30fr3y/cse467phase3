import java.util.ArrayList;


public class GeneralizationTable {
	
	private String[] clusterList;
	private QuasiIdentifiers quasiIdList;
	private ArrayList<GeneralizationCell> genTable;
	private DBManager dbManager;
	
	public GeneralizationTable(int[] quasiIdList, DBManager dbManager) {
		this.quasiIdList = new QuasiIdentifiers(quasiIdList);
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
				if (solution.dominates(gc.getGeneralizationSteps())) {
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
				if (solution.dominates(gc.getGeneralizationSteps())) {
					lineKanonymity++;
				}
				
			}
		}
		return true;
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
			genTable.add(new GeneralizationCell(singleTuple, s));
		}
	}

	private void setClusterList() {
		// TODO: finish method
	}
	
	// more formatting can happen here...
	public String toString() {
		String output = "";
		output += "   ";
		
		// getting the header info...
		for (String s : this.clusterList) {
			output += s + "\t";
		}
		
		// getting the cells...
		String currentRow = genTable.get(0).getSingleTuple();
		output += currentRow + "\t";
		for (GeneralizationCell gc : genTable) {
			if (gc.getSingleTuple().equals(currentRow)) {
				output += gc.toString() + "\t";
			} else {
				currentRow = gc.getSingleTuple();
				output += currentRow + "\t";
			}
		}
		
		return output;
	}

	class GeneralizationCell {
		private String singleTuple;
		private String targetCluster;
		private GeneralizationSteps genSteps;
		
		public GeneralizationCell(String singleTuple, 
								  String targetCluster) {
			this.singleTuple = singleTuple;
			this.targetCluster = targetCluster;
			this.setGeneralizationSteps();
		}
		
		public String getSingleTuple() {
			return singleTuple;
		}
		
		private void setGeneralizationSteps() {
			// TODO: finish method
			// 		 this method will take a little time to figure out
		}
		
		public int[] getGeneralizationSteps() {
			return genSteps.getQuasiIdList();
		}
		
		public String toString() {
			return genSteps.toString();
		}
	}
}
