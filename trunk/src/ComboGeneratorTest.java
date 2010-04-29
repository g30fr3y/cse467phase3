import java.util.Vector;


public class ComboGeneratorTest {
	private int height;
	private int combos;
	private int[][] data;
	private int[] levelPosition;
	private int[][] levels;
	private int[] checkStatus;
	private int[] inputNumbers;
	
	private final int NOT_CHECKED = 0;
	private final int HAS_SOLUTION = 1;
	private final int NO_SOLUTION = -1;
	
	public ComboGeneratorTest(int ... inputNumbers) {
		setup(inputNumbers);
	}
	
	public ComboGeneratorTest(QuasiId ... ids) {
		int[] inputs = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			inputs[i] = ids[i].getMaxGen();
		}
		setup(inputs);
	}
	
	private void setup(int ... inputNumbers) {
		this.setInputNumbers(inputNumbers);
		this.setHeight();
		this.setCombos();
		this.setCheckStatus();
		this.setData();
		this.setLevels();
	}



	public int getHeight() {
		return height;
	}



	public int getCombos() {
		return combos;
	}



	public int[][] getData() {
		return data;
	}



	public int[][] getLevels() {
		return levels;
	}



	public int[] getCheckStatus() {
		return checkStatus;
	}



	public int[] getInputNumbers() {
		return inputNumbers;
	}



	public void setHeight() {
		int tempHeight = 0;
		for (int i : this.inputNumbers) {
			tempHeight += (i-1);
		}
		this.height = tempHeight+1;
	}



	public void setCombos() {
		int tempCombos = 1;
		for (int i : this.inputNumbers) {
			tempCombos *= i;
		}
		this.combos = tempCombos;
	}



	public void setData() {
		int numInputNumbers = this.getInputNumbers().length;
		this.data = new int[this.getCombos()][numInputNumbers];
		this.levelPosition = new int[this.getCombos()];

		int[] incFactorCounters = new int[numInputNumbers];
		int[] incFactor = new int[numInputNumbers];
		int leftPosition = numInputNumbers-1;
		incFactor[leftPosition] = 1;
		int tempFactor = incFactor[leftPosition];
		for (int i = leftPosition-1; i > -1; i--) {
			tempFactor *= inputNumbers[i+1];
			incFactor[i] = tempFactor;
		}
		
		int inc = 0;
		int[] tempDigits = new int[numInputNumbers];
		do {
			// add to data[][] 
			int sum = 0;
			for (int i = 0; i < numInputNumbers; i++) {
				data[inc][i] = tempDigits[i];
				sum += tempDigits[i];
			}
			// determine levelPosition
			this.levelPosition[inc] = sum;
			
			for (int i = 0; i < numInputNumbers; i++) {
				incFactorCounters[i] += 1;
				if (incFactorCounters[i] == incFactor[i]) {
					// zero out incFactorCounter
					incFactorCounters[i] = 0;
					
					// increment the tempDigit
					// if the tempDigit is at the limit, roll back to zero
					tempDigits[i] += 1;
					if (tempDigits[i] == this.inputNumbers[i]) {
						tempDigits[i] = 0;
					}
				}
			}
			
			inc++;
		} while(inc < this.getCombos());
	}



	public void setLevels() {
		this.levels = new int[this.getHeight()][];
		
		for (int i = 0; i < this.getHeight(); i++) {
			Vector<Integer> totalMatches = new Vector<Integer>();
			for (int j = 0; j < this.getCombos(); j++) {
				if (this.levelPosition[j] == i) {
					totalMatches.add(j);
				}
			}
			
			levels[i] = new int[totalMatches.size()];
			for (int j = 0; j < totalMatches.size(); j++) {
				levels[i][j] = totalMatches.get(j);
			}
		}
	}



	public void setCheckStatus() {
		this.checkStatus = new int[this.getHeight()];
		for (int i : this.checkStatus) {
			i = 0;
		}
	}



	public void setInputNumbers(int[] inputNumbers) {
		this.inputNumbers = new int[inputNumbers.length];
		for (int i = 0; i < inputNumbers.length; i++) {
			this.inputNumbers[i] = inputNumbers[i];
		}
	}
	
	public void printAllCombosAndSums() {
		for (int i = 0; i < this.getCombos(); i++) {
			System.out.print(this.levelPosition[i] + "\t");
			for (int j : this.data[i]) {
				System.out.print(j);
			}
			System.out.print("\t" + i + "\n");
		}
	}
	
	public void printLattice() {
		for (int i = 0; i < this.getHeight(); i++) {
			System.out.print(i + "\t");
			for (int j : this.levels[i]) {
				System.out.print(j + " ");
			}
			System.out.print("\n");
		}
	}
	
	public int[][] getCombosAt(int level) {
		int[][] combos = new int[this.levels[level].length][];
		for (int i = 0; i < this.levels[level].length; i++) {
			combos[i] = this.data[levels[level][i]];
		}
		return combos;
	}



	public static void main(String[] args) {
//		ComboGeneratorTest demo = new ComboGeneratorTest(2,2,3);
//		ComboGeneratorTest demo = new ComboGeneratorTest(4,5,6,4,3,3);
//		demo.printAllCombosAndSums();
//		demo.printLattice();
//		int[][] solutions = demo.getCombosAt(15);
//		for (int j = 0; j < solutions.length; j++) {
//			for (int i : solutions[j]) {
//				System.out.print(i);
//			}
//			System.out.println(" ");
//		}
		
	}

}
