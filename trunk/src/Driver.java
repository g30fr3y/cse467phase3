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
		
//		QuasiIdentifiers quasi = new QuasiIdentifiers(QuasiIdentifiers.createQuasiIdMask());
//		System.out.println(quasi);
		
//		int[] mask = { 1, -1, 2, 1, -1, 2 };
//		int[] list1 = {1, -1, 2, 1, -1, 2};	// should be equal; dom == true
//		int[] list2 = {0, -1, 1, 0, -1, 1}; // dom == true
//		int[] list3 = {2, -1, 3, 2, -1, 3}; // dom == false
//		int[] list4 = {2,  1, 3, 2,  1, 3}; // dom == false; mismatched quasi-ids???
//		int[] list5 = {-1, -1, -1, -1, -1, -1}; // dom == false; no quasi-ids???
//		GeneralizationSteps test = new GeneralizationSteps(mask);
//		System.out.println(test);
//		System.out.println("Testing list1 : " + test.dominates(list1) + " ; TRUE");
//		System.out.println("Testing list2 : " + test.dominates(list2) + " ; TRUE");
//		System.out.println("Testing list3 : " + test.dominates(list3) + " ; FALSE");
//		System.out.println("Testing list4 : " + test.dominates(list4) + " ; FALSE");
//		System.out.println("Testing list5 : " + test.dominates(list5) + " ; TRUE");
		
		System.out.println(Identifier.Ids.PRODUCT_ID.getPosition());
	}

}