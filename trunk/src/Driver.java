
public class Driver
{
    public static void main(String[] args)
    {
        long executionTime = System.currentTimeMillis();
        DBManager dbManager = new DBManager("", "emptyTest.xls");
        
        
//        QuasiId[] id = {QuasiId.DEPT_ID,QuasiId.WEIGHT,QuasiId.PRODUCT_ID, QuasiId.EXPIRE_YEAR};
        QuasiId[] id = {QuasiId.DEPT_ID,QuasiId.PRODUCT_ID};
        int kAnon = 1;
        int maxSup = 1;
        
        
        /* This tests individual commands

        GeneralizationTable genTable = new GeneralizationTable( id );
        GeneralizationSteps solution = new GeneralizationSteps();
        solution.setGenSteps( id[0], 3 );
        boolean isSol = genTable.testSolution( solution, kAnon, maxSup, false );
        solution.setGenSteps( id[0], 0 );
        isSol = genTable.testSolution( solution, kAnon, maxSup, false );
        String[][] output = Generalizer.getGeneralizedDataArray( solution );
        
        System.out.println(isSol);
         */
        
        
//        // Call Samarati's and display the output
        String[][] output = Samarati.kanon( kAnon, maxSup, id) ;
        
        for( String[] row : output )
        {
            for( int k = 0; k < row.length ; k++)
                System.out.print(row[k] + "\t");
            System.out.println();
        }
        
        dbManager.closeConnection( false );
        executionTime = System.currentTimeMillis() - executionTime; 
        System.out.println(executionTime);
//        String[] elements = { "0", "1", "2" , "0", "1", "2", "0", "1", "2"};
//        int[] indices;
//        CombinationGenerator x = new CombinationGenerator( elements.length, 3 );
//        StringBuffer combination;
//        while (x.hasMore())
//        {
//            combination = new StringBuffer();
//            indices = x.getNext();
//            for ( int i = 0; i < indices.length; i++ )
//            {
//                combination.append( elements[indices[i]] );
//            }
//            System.out.println( combination.toString() );
//        }

        
    }
}