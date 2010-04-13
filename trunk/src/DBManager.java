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
		this.inputFile = inputFile;
		this.model = new ResultsModel();
		this.user = "root";
		this.url = "jdbc:mysql://localhost/Phase3";
		this.setupDatabase(password);
		
		this.openConnection(password);
		
		this.populateDatabase();
	}
	
	private void openConnection(String password) {
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			// TODO: add code to clear out the database tables
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getAllTableData() {
		model.setResultSet(null);
		try {
			model.setResultSet(stmt.executeQuery("SELECT * FROM Student"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model.getFormattedData();
	}
	
	public String[] runQuery(String sqlQuery) {
		model.setResultSet(null);
		try {
			model.setResultSet(stmt.executeQuery(sqlQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model.getData();
	}
	
	public void execUpdate(String sqlUpdate) {
		model.setResultSet(null);
		try {
			stmt.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	private void setupDatabase(String password) {
		DatabaseCreator.createPhase3Database(password);
	}
	
	private void populateDatabase() {
		DBDataParser.populateDatabase(stmt, inputFile);
	}
}
