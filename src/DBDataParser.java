import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class DBDataParser
{
    // Global Variables
    private static final int NUM_COLS     = 6;
    private static final int PRODUCT_ID   = 0,
                             PRICE        = 1,
                             DEPT_ID      = 2,
                             WEIGHT       = 3,
                             PRODUCT_YEAR = 4,
                             EXPIRE_YEAR  = 5;
    
    //TODO: Do we really need this Constructor?
    public DBDataParser()
    {

    }

    /**
     * Reads an excel file and collects the information putting it in a database.
     * @param statement 
     * @param filename
     */
    public static void populateDatabase( Statement statement, String filename )
    {
        String[] rowBuffer = new String[NUM_COLS];
        File inputExcel = new File(filename);
        Workbook workbook;
        
        try
        {
            workbook = Workbook.getWorkbook(inputExcel);
            
            // Assuming that all the data is contained is the first sheet
            Sheet sheet = workbook.getSheet(0);
            
            // Loop through all the contents reading them into a string buffer
            // We skip the first row because that should contain the headings
            // Note: sheet.getCell(COL,ROW)
            for( int i = 1; i < sheet.getRows() ; i++ )
            {
                for( int j = 0; j < sheet.getColumns() ; j++ )
                {
                    // Store Values into the row buffer
                    rowBuffer[j] = sheet.getCell(j,i).getContents(); 
                }
                
                // Add a batch job for the connection
                statement.addBatch("INSERT INTO Student " + 
                                   "VALUES('" + 
                                   rowBuffer[PRODUCT_ID] + "','" + 
                                   rowBuffer[PRICE] + "','" + 
                                   rowBuffer[DEPT_ID] + "','" + 
                                   rowBuffer[WEIGHT] + "','" + 
                                   rowBuffer[PRODUCT_YEAR] + "','" + 
                                   rowBuffer[EXPIRE_YEAR] + "')");
            }
            
            // Once all the rows have been added to the batch we can run all those queries in one go
            statement.executeBatch();
            
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BiffException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}