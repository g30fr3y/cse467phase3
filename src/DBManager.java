import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager
{
    // Global Variables
    private Connection conn;
    private Statement stmt;
    private ResultsModel model;
    private String inputFile;
    private String user;
    private String url;
    private final String DB_NAME = "Phase3";
    private final String TABLE_NAME = "Student";
    private final String DEFAULT_PASSWORD = "";

    public DBManager(String password, String inputFile)
    {
        this.inputFile = inputFile;
        this.model = new ResultsModel();
        this.user = "root";
        this.url = "jdbc:mysql://localhost/" + DB_NAME;
        
        this.setupDatabase(password);
        this.openConnection(password);
        this.populateDatabase();
    }
    
    public DBManager () {
        this.model = new ResultsModel();
        this.user = "root";
        this.url = "jdbc:mysql://localhost/" + DB_NAME;
        this.openConnection(DEFAULT_PASSWORD);  	
    }

    private void openConnection(String password)
    {
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (SQLException sql)
        {
            sql.printStackTrace();
        }
    }

    /**
     * Closes all the connections made to the database, furthermore if 
     * the argument is true then all the data from the database is erased
     * otherwise it is kept. Maybe not the best choices is variables...
     * @param eraseData
     */
    public void closeConnection(boolean eraseData)
    {
        try
        {
            // Clears database if the connection is closed with a true parameter
            if(eraseData)
                clearTableData();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String[] getAllTableData()
    {
        model.setResultSet(null);
        try
        {
            model.setResultSet(stmt.executeQuery("SELECT * FROM " + TABLE_NAME));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return model.getFormattedData();
    }

    public String[] runQuery(String sqlQuery)
    {
        model.setResultSet(null);
        try
        {
            model.setResultSet(stmt.executeQuery(sqlQuery));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return model.getData();
    }

    public void execUpdate(String sqlUpdate)
    {
        model.setResultSet(null);
        try
        {
            stmt.executeUpdate(sqlUpdate);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void setupDatabase(String password)
    {
        DatabaseCreator.createPhase3Database(password);
    }

    private void populateDatabase()
    {
        DBDataParser.populateDatabase(stmt, inputFile);
    }
    
    /**
     * Erases all the data from the database.
     */
    private void clearTableData()
    {
        execUpdate("Delete From " + TABLE_NAME);
    }
}
