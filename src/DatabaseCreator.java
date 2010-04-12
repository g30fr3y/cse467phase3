import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The purpose of this Class is to set up the databases along with its
 * corresponding tables.
 * @author Jason Tennant<br>Jose Trigueros
 *
 */
public class DatabaseCreator
{
    // Global Variables
    private static String password = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private final static String USER = "root";
    private final static String MYSQL_URL = "jdbc:mysql://localhost/mysql";
    private final static int CREATE_DB  = 0,
                             USE        = 1,
                             DROP_TABLE = 2,
                             CREATE_TB  = 3;
    private final static String[] COMMAND_LIST = {
                                                   "CREATE DATABASE IF NOT EXISTS ",
                                                   "USE ",
                                                   "DROP TABLE IF EXISTS ",
                                                   "CREATE TABLE "
                                                 };
    
    /**
     * Initializes all the connections and statements needed to start
     * creating the databases.
     */
    private static void initializeSettings()
    {
        try
        {
            // Initialize all the settings needed to start creating databases
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(MYSQL_URL, USER, password );
            statement = connection.createStatement();
            
        } catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a database with the name of the argument.
     * @param database
     */
    private static void createDatabase(String database)
    {
        try
        {
            statement.executeUpdate(COMMAND_LIST[CREATE_DB] + database);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Closes all open connections and statements.
     */
    private static void closeAll()
    {
        try
        {
            statement.close();
            connection.close();
            
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Specific to Phase 3, this sets up the Database and Tables required
     * for Phase 3.
     * @param password
     */
    public static void createPhase3Database(String password)
    {
        // Local Vars
        String database = "Phase3"; 
        String table = "Student";
        
        // Initialize Settings
        DatabaseCreator.password = password;
        initializeSettings();
        
        // Create Database
        createDatabase(database);
        
        try
        {
            // Commands for creating custom table, adding to a batch
            statement.addBatch(COMMAND_LIST[USE]+database);
            statement.addBatch(COMMAND_LIST[DROP_TABLE] 
                               + "`" + database + "`.`" + table + "`");
            statement.addBatch(COMMAND_LIST[CREATE_TB] 
                               + "`" + database + "`.`" + table + "`" +
                               "(" + 
                               "`productID` varchar(5) UNIQUE NOT NULL," +
                               "`price` int  NOT NULL DEFAULT 0," + 
                               "`deptID` int  NOT NULL DEFAULT 0," +
                               "`weight` int  NOT NULL DEFAULT 0," +
                               "`productYear` varchar(5)  NOT NULL," +
                               "`expireYear` varchar(5)  NOT NULL" + 
                               ") ENGINE = MyISAM;");
            
            // Executing Batch of Statements
            statement.executeBatch();
            
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            // Close up all the connections
            closeAll();
        }
    }
}