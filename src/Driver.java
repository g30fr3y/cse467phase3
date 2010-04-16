import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Driver {

	public static void main(String[] args) {		
//		DBManager dbManager = new DBManager("", "test.xls");
//		String[] info = dbManager.getAllTableData();
//		for (String s : info) {
//			System.out.println(s);
//		}
//		dbManager.closeConnection(true);
		
//		GeneralizationSteps test = new GeneralizationSteps();
//		test.setGenSteps(QuasiId.DEPT_ID, 10);
//		test.setGenSteps(QuasiId.PRODUCT_ID, 4);
//		test.setGenSteps(QuasiId.PRICE, 3);
//		test.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
//		
//		GeneralizationSteps test0 = new GeneralizationSteps();
//		test0.setGenSteps(QuasiId.DEPT_ID, 10);
//		test0.setGenSteps(QuasiId.PRODUCT_ID, 4);
//		test0.setGenSteps(QuasiId.PRICE, 3);
//		test0.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
//		
//		GeneralizationSteps test1 = new GeneralizationSteps();
//		test1.setGenSteps(QuasiId.DEPT_ID, 9);
//		test1.setGenSteps(QuasiId.PRODUCT_ID, 4);
//		test1.setGenSteps(QuasiId.PRICE, 3);
//		test1.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
//		
//		GeneralizationSteps test2 = new GeneralizationSteps();
//		test2.setGenSteps(QuasiId.DEPT_ID, 11);
//		
//		System.out.println(test);
//		System.out.println(test.viewStepsOnly());
//
//		System.out.println(test.dominates(test0.getDataPairs())); // true
//		System.out.println(test.dominates(test1.getDataPairs())); // true
//		System.out.println(test.dominates(test2.getDataPairs())); // false
		
		GeneralizationTable table = new GeneralizationTable(null, QuasiId.DEPT_ID, QuasiId.PRODUCT_ID);
		System.out.println(table);
		
	}

}