
public class Driver
{
    public static void main(String[] args)
    {
        DBManager dbManager = new DBManager("", "st.xls");
        
        int kAnon = 1;
        int maxSup = 1;
//        QuasiId[] list = {QuasiId.PRODUCT_ID,QuasiId.PRICE, QuasiId.PRODUCT_YEAR, QuasiId.EXPIRE_YEAR,};
        QuasiId[] list = {QuasiId.PRODUCT_ID,QuasiId.WEIGHT};
        // Call Samarati's and display the output
        String[][] output = Samarati.kanon( kAnon, maxSup, list ) ;
        
        int i = 0;
        for( String[] row : output )
        {
            for( int k = 0; k < row.length ; k++)
                System.out.print(row[k] + "\t");
            System.out.println();
        }
        
        dbManager.closeConnection( false );
        
    }
}