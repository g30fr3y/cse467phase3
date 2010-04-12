import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Driver {

	public static void main(String[] args) {
		
		try {
			File out = new File( "data.txt" );
			FileWriter fw = new FileWriter ( out );
			PrintWriter pw = new PrintWriter( fw );

			for (int i = 0; i < 100; i++) {
				String output = i + ";";
				output += (int)(Math.random()*10000) + ";";
				output += Math.abs(((int)(Math.random()*100)-50)) + ";";
				output += (int)(Math.random()*10) + ";";
				output += (int)(Math.random()*10)+1980 + ";";
				output += (int)(Math.random()*10)+1980;
				//System.out.println(output);
				pw.println( output );
			}
			
			fw.close( );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager dbManager = new DBManager("password", "data.txt");
		String[] info = dbManager.getAllTableData();
		for (String s : info) {
			System.out.println(s);
		}
	}

}
