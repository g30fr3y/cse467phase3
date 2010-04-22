
public class Driver
{
    public static void main(String[] args)
    {
        DBManager dbManager = new DBManager("", "emptyTest.xls");
        Samarati sam = new Samarati( QuasiId.PRODUCT_ID );
//        System.out.println( Generalizer.generalizeYear( "", 3, QuasiId.EXPIRE_YEAR ) );
        int kAnon = 3;
        int maxSup = 3;
////        QuasiId[] list = {QuasiId.PRODUCT_ID,QuasiId.PRICE, QuasiId.PRODUCT_YEAR, QuasiId.EXPIRE_YEAR,};
//        QuasiId[] list = {QuasiId.PRODUCT_ID, QuasiId.EXPIRE_YEAR};
//        // Call Samarati's and display the output
        String[][] output = Samarati.kanon( kAnon, maxSup, QuasiId.PRODUCT_ID ) ;
//        
//        
//        System.out.println(" " + 1/2);
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