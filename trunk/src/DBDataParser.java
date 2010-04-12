import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;


public class DBDataParser {
	public DBDataParser() {
		
	}
	
	public static void populateDatabase(Statement stmt,
										String fileName) {
		File file = new File( fileName );
		BufferedReader in = null;
		try {
			in = new BufferedReader( new FileReader ( file ) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			String line;
			while ((line = in.readLine( )) != null) {
				String[] data = line.split(";");
				String sql = "INSERT INTO Student " +
							 "VALUES('" + data[0] + "','" + data[1] + "','" +
							              data[2] + "','" + data[3] + "','" +
							              data[4] + "','" + data[5] + "')";
				stmt.executeUpdate(sql);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
