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
 * @author Jose Trigueros
 * Creates an excel spreadsheet with randomly generated data. 
 */
public class RandomDataGenerator
{
    static final String EXCEL_FILENAME = "test.xls";
    static final String[] HEADINGS = {"Product ID","Price","DeptID","Weight","Product Year","Expire Year"};
    static final int PRODUCT_COUNT = 10;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        createExcel(EXCEL_FILENAME, PRODUCT_COUNT);
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
}
