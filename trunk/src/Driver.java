import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Driver {

	public static void main(String[] args) {
		
//		try {
//			File out = new File( "data.txt" );
//			FileWriter fw = new FileWriter ( out );
//			PrintWriter pw = new PrintWriter( fw );
//
//			for (int i = 0; i < 100; i++) {
//				String output = i + ";";				// ProductID : 0000-9999 (Unique Key)
//				output += (int)(Math.random()*11111) + ";";	// Price :     0-99,999
//				output += Math.abs(((int)(Math.random()*100)-50)) + ";"; // DeptID:     0-50 
//				output += (int)(Math.random()*10) + ";"; 	// Weight :    0-9
//				output += (int)(Math.random()*30)+1980 + ";"; // ProductYear:  1980-2010
//				output += (int)(Math.random()*35)+1980; // ExpireYear:   StartYear-2015 (or empty, 20% chance)
//				//System.out.println(output);
//				pw.println( output );
//			}
//			
//			fw.close( );
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		DBManager dbManager = new DBManager("", "st.xls");
		String[] info = dbManager.getAllTableData();
		for (String s : info) {
			System.out.println(s);
		}
		dbManager.closeConnection();
	}

}