import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {
	private Connection conn;
	private Statement stmt;
	private ResultsModel model;
	private String inputFile;
	private String password;
	private String user;
	private String url;
	
	public DBManager(String password, String inputFile) {
		this.password = password;
		this.inputFile = inputFile;
		this.model = new ResultsModel();
		this.user = "root";
		this.url = "jdbc:mysql://localhost/Phase3";
		this.setupDatabase();
		
		this.populateDatabase();
	}
	
	private void openConnection() {
		try {
			if (conn != null) {
				conn.close();
				model.setResultSet(null);
			}
			
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getAllTableData() {
		this.openConnection();
		try {
			model.setResultSet(stmt.executeQuery("SELECT * FROM Student"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection();
		return model.getFormattedData();
	}
	
	public String[] runQuery(String sqlQuery) {
		this.openConnection();
		try {
			model.setResultSet(stmt.executeQuery(sqlQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection();
		return model.getData();
	}
	
	public void execUpdate(String sqlUpdate) {
		this.openConnection();
		try {
			stmt.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection();		
	}
	
	private void setupDatabase() {
		DatabaseCreator.createPhase3Database(password);
	}
	
	private void populateDatabase() {
		this.openConnection();
		DBDataParser.populateDatabase(stmt, inputFile);
		this.closeConnection();
	}
}
