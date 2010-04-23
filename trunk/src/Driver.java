
public class Driver
{
    public static void main(String[] args)
    {
        DBManager dbManager = new DBManager("", "emptyTest.xls");
        
        
        QuasiId[] id = {QuasiId.WEIGHT};
        int kAnon = 2;
        int maxSup = 0;
        
        
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
    }
}