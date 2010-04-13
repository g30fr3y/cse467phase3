/**
 * 
 */

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author Jose Trigueros<br>Jason Tennant
 * Creates an excel spreadsheet with randomly generated data. 
 */
public class RandomDataGenerator
{
    /* *****Begin: Global Variables***** */ 
    static final String EXCEL_FILENAME = "test.xls";
    static final String[] HEADINGS = {"Product ID","Price","DeptID","Weight","Product Year","Expire Year"};
    
    // Index names
    static final int PRODUCTID   = 0, 
                     PRICE       = 1, 
                     DEPTID      = 2, 
                     WEIGHT      = 3, 
                     PRODUCTYEAR = 4, 
                     EXPIREYEAR  = 5;
    
    // Random Variable Bounds
    static final int PRODUCT_COUNT  = 10,
                     MAX_PRICE      = 99999,
                     MAX_DEPT_ID    = 50,
                     MAX_WEIGHT     = 9,
                     START_YEAR     = 1980,
                     MAX_START_YEAR = 2010,
                     MAX_EXPIRE     = 2015;
    static final int NUM_HEADINGS = HEADINGS.length;
    
    // Create a 2d array to hold all the data
    static final int[][] ROW_VALUES = new int[PRODUCT_COUNT + 1][NUM_HEADINGS];
    
    /* *****End: Global Variables***** */
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
//        createExcel(EXCEL_FILENAME, PRODUCT_COUNT);
        createRandomData();
    }

    private static void createExcel(String filename, int count)
    {
        File excelFile = new File(filename);
        
        try
        {
            // Excel Variables
            WritableWorkbook workbook = Workbook.createWorkbook(excelFile);
            
            // Create Sheet
            workbook.createSheet("Sheet1", 0);
            
            // Add Headings to sheet
            addHeadings(workbook.getSheet(0), HEADINGS);
            
            // TODO: Add Random Data
//            addRandomData(workbook.getSheet(0));
            
            workbook.write();
            workbook.close();
            
        } catch (RowsExceededException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriteException e)
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
    
    private static void addHeadings(WritableSheet sheet, String[] headings) throws RowsExceededException, WriteException
    {
        // Excel Settings
        // TODO: Figure out how to make the headings bold
//        WritableCellFormat Bold = new Writ;
        
        // Write the headings
        for( int i = 0; i < headings.length ; i++ )
        {
            sheet.addCell(new Label(i, 0, headings[i]));
        }
    }
    
    /**
     * Creates data for the excel sheet, by generating random data<br><br>
     * Here’s the general formulas:<br>
     * LowerBound <= random < UpperBound
     * <pre>    int random = (int) (Lower_Bound + Math.random() * ( Upper_Bound - Lower_bound) );</pre>
     * LowerBound <= random <=UpperBound<br>
     * <pre>    int random = (int) (Lower_Bound + Math.random() * ( Upper_Bound - Lower_bound) + 0.5) ;</pre>
     * @param sheet
     */
    public static void createRandomData()
    {
        for (int id = 1 ; id <= PRODUCT_COUNT ; id++) 
        {
            // Set up the entries for this row
            ROW_VALUES[id][PRODUCTID] = id;
            ROW_VALUES[id][PRICE]     = (int)( Math.random()* MAX_PRICE + 0.5 );
            ROW_VALUES[id][DEPTID]    = (int)( Math.random()* MAX_DEPT_ID + 0.5 );
            ROW_VALUES[id][WEIGHT]    = (int)( Math.random()* MAX_WEIGHT + 0.5 );
            ROW_VALUES[id][PRODUCTYEAR] = (int)( START_YEAR + Math.random()* (MAX_START_YEAR - START_YEAR ) + 0.5 );
            //TODO: Add the logic for the 20% chance not having an expiration date
            ROW_VALUES[id][EXPIREYEAR]  = (int)( ROW_VALUES[id][PRODUCTYEAR] + Math.random()* (MAX_EXPIRE - ROW_VALUES[id][PRODUCTYEAR] ) + 0.5 );
            
            // TODO: Sanity check
            System.out.println(ROW_VALUES[id][PRODUCTID] + "\t" +
                               ROW_VALUES[id][PRICE]     + "\t" +
                               ROW_VALUES[id][DEPTID]    + "\t" +
                               ROW_VALUES[id][WEIGHT]    + "\t" +
                               ROW_VALUES[id][PRODUCTYEAR] + "\t" + 
                               ROW_VALUES[id][EXPIREYEAR] + "\n");
        }
        //      String output = i + ";";                // ProductID : 0000-9999 (Unique Key)
        //      output += (int)(Math.random()*11111) + ";"; // Price :     0-99,999
        //      output += Math.abs(((int)(Math.random()*100)-50)) + ";"; // DeptID:     0-50 
        //      output += (int)(Math.random()*10) + ";";    // Weight :    0-9
        //      output += (int)(Math.random()*30)+1980 + ";"; // ProductYear:  1980-2010
        //      output += (int)(Math.random()*35)+1980; // ExpireYear:   StartYear-2015 (or empty, 20% chance)
        //      //System.out.println(output);
        //      pw.println( output );
        //  }
    }

    private static void addData(WritableSheet sheet)
    {
        
    }
}
