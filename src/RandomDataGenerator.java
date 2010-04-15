/**
 * 
 */

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
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
    static String EXCEL_FILENAME = "default.xls";
    static final String EXCEL_SHEETNAME = "Sheet1";
    static final String[] HEADINGS = {"Product ID","Price","DeptID","Weight","Product Year","Expire Year"};
    static final double PERCENT_OF_EMPTY = .2;
    
    // Index names
    static final int PRODUCTID   = 0, 
                     PRICE       = 1, 
                     DEPTID      = 2, 
                     WEIGHT      = 3, 
                     PRODUCTYEAR = 4, 
                     EXPIREYEAR  = 5;
    
    // Random Variable Bounds
    static int PRODUCT_COUNT  = 10;       // Alter this variable to  
    static final int MAX_PRODUCT_ID = 9999,
                     MAX_PRICE      = 99999,
                     MAX_DEPT_ID    = 50,
                     MAX_WEIGHT     = 9,
                     START_YEAR     = 1980,
                     MAX_START_YEAR = 2010,
                     MAX_EXPIRE     = 2015;
    static final int NUM_HEADINGS = HEADINGS.length;
    
    // Create a 2d array to hold all the data
    static int[][] ROW_VALUES = new int[PRODUCT_COUNT][NUM_HEADINGS];
    
    
    /* *****End: Global Variables***** */
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // Get's the value from the command line, otherwise just defaults to 10
        if (args.length == 2)
        {
            EXCEL_FILENAME = args[0] + ".xls";
            PRODUCT_COUNT = Integer.parseInt(args[1]);
            ROW_VALUES = new int[PRODUCT_COUNT + 1][NUM_HEADINGS];
        }
        else if ( args.length == 1)
        {
            PRODUCT_COUNT = Integer.parseInt(args[0]);
            ROW_VALUES = new int[PRODUCT_COUNT + 1][NUM_HEADINGS];
        }
        
        createExcel(EXCEL_FILENAME);
    }

    /**
     * Calls all the necessary functions to create the excel file
     * @param filename
     * @param count
     */
    private static void createExcel(String filename)
    {
        File excelFile = new File(filename);
        
        try
        {
            // Excel Variables
            WritableWorkbook workbook = Workbook.createWorkbook(excelFile);
            
            // Create Sheet
            workbook.createSheet(EXCEL_SHEETNAME, 0);
            
            // Add Headings to sheet
            addHeadings(workbook.getSheet(0), HEADINGS);
            
            // Add Random Data
            addRandomData(workbook.getSheet(0));
            
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
    
    /**
     * Adds the first row to the excel file containing the headings
     * @param sheet
     * @param headings
     * @throws RowsExceededException
     * @throws WriteException
     */
    private static void addHeadings(WritableSheet sheet, String[] headings) throws RowsExceededException, WriteException
    {
        // Font Settings
        WritableFont bold = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
        
        // Write the headings
        for( int i = 0; i < headings.length ; i++ )
        {
            sheet.addCell( new Label(i, 0, headings[i], new WritableCellFormat(bold) ) );
        }
    }
    
    /**
     * Creates data for the excel sheet, by generating random data<br><br>
     * Here’s the general formulas:<br>
     * LowerBound <= random < UpperBound
     * <pre>    int random = (int) (Lower_Bound + Math.random() * ( Upper_Bound - Lower_bound) );</pre>
     * LowerBound <= random <=UpperBound<br>
     * <pre>    int random = (int) (Lower_Bound + Math.random() * ( Upper_Bound - Lower_bound) + 0.5) ;</pre>
     */
    private static void createRandomData()
    {   
        // Select a random starting point rather than randomly selecting Product ID's
        int startingID = (int)( Math.random()* MAX_PRODUCT_ID + 0.5);
        startingID += ( startingID + PRODUCT_COUNT > MAX_PRODUCT_ID ) ? (startingID + PRODUCT_COUNT) - MAX_PRODUCT_ID : 0 ;
        
        for (int id = 0 ; id < PRODUCT_COUNT ; id++) 
        {
            // Set up the entries for this row
            ROW_VALUES[id][PRODUCTID] = startingID + id;
            ROW_VALUES[id][PRICE]     = (int)( Math.random()* MAX_PRICE + 0.5 );
            ROW_VALUES[id][DEPTID]    = (int)( Math.random()* MAX_DEPT_ID + 0.5 );
            ROW_VALUES[id][WEIGHT]    = (int)( Math.random()* MAX_WEIGHT + 0.5 );
            ROW_VALUES[id][PRODUCTYEAR] = (int)( START_YEAR + Math.random()* (MAX_START_YEAR - START_YEAR ) + 0.5 );
            // 20% Chance of the Expire Year being empty
            if( Math.random() > .2)
                ROW_VALUES[id][EXPIREYEAR]  = (int)( ROW_VALUES[id][PRODUCTYEAR] + Math.random()* (MAX_EXPIRE - ROW_VALUES[id][PRODUCTYEAR] ) + 0.5 );
        }
    }

    /**
     * Calls the helper function createRandomData and then writes that data into the excel file
     */
    private static void addRandomData(WritableSheet sheet) throws RowsExceededException, WriteException
    {
        // First we create random data
        createRandomData();
        
        // We will retrieve the data from the 2d global array and stick it into the excel spreadsheet
        // We enter data starting at row 1 because the 1st row will be taken by the headings
        for( int row = 0; row < PRODUCT_COUNT ; row++)
        {
            sheet.addCell( new Number(PRODUCTID, row + 1, ROW_VALUES[row][PRODUCTID]) );
            sheet.addCell( new Number(PRICE, row + 1, ROW_VALUES[row][PRICE]) );
            sheet.addCell( new Number(DEPTID, row + 1, ROW_VALUES[row][DEPTID]) );
            sheet.addCell( new Number(WEIGHT, row + 1, ROW_VALUES[row][WEIGHT]) );
            sheet.addCell( new Number(PRODUCTYEAR, row + 1, ROW_VALUES[row][PRODUCTYEAR]) );
            sheet.addCell( new Number(EXPIREYEAR, row + 1, ROW_VALUES[row][EXPIREYEAR]) );
        }
    }
    
    /**
     * Sanity Check, just prints the data entered in the 2d array
     */
    public static void printData()
    {
        for (int id = 0 ; id <= PRODUCT_COUNT ; id++) 
        {
            System.out.println(ROW_VALUES[id][PRODUCTID] + "\t" +
                               ROW_VALUES[id][PRICE]     + "\t" +
                               ROW_VALUES[id][DEPTID]    + "\t" +
                               ROW_VALUES[id][WEIGHT]    + "\t" +
                               ROW_VALUES[id][PRODUCTYEAR] + "\t" + 
                               ROW_VALUES[id][EXPIREYEAR] );
        }
    }
}
