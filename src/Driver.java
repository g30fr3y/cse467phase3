import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Driver
{

    public static void main(String[] args)
    {
        // Test out the permutations
        int[][] testing = Samarati.createPossibleSolutions( 3, 2 );
        
        
        return;
    }
//        QuasiId id = QuasiId.EXPIRE_YEAR;
//        String entry = "2000";
//        String entry2 = "0";
//        for ( int i = 0; i <= id.maxGeneralization ; i++ )
//        {
//            System.out.print( Generalizer.generalize( entry , id , i ) + "\t");
//            System.out.println( Generalizer.generalize( entry2 , id , i ) );
//        }
//        
//        System.out.print( Generalizer.getNumGeneralization( entry , entry2, id ) );
        
        // DBManager dbManager = new DBManager("", "test.xls");
        // String[] info = dbManager.getAllTableData();
        // for (String s : info) {
        // System.out.println(s);
        // }
        // dbManager.closeConnection(false);

//         GeneralizationSteps test = new GeneralizationSteps();
//         test.setGenSteps(QuasiId.DEPT_ID, 10);
//         test.setGenSteps(QuasiId.PRODUCT_ID, 4);
//         test.setGenSteps(QuasiId.PRICE, 3);
//         test.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
        //		
        // GeneralizationSteps test0 = new GeneralizationSteps();
        // test0.setGenSteps(QuasiId.DEPT_ID, 10);
        // test0.setGenSteps(QuasiId.PRODUCT_ID, 4);
        // test0.setGenSteps(QuasiId.PRICE, 3);
        // test0.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
        //		
        // GeneralizationSteps test1 = new GeneralizationSteps();
        // test1.setGenSteps(QuasiId.DEPT_ID, 9);
        // test1.setGenSteps(QuasiId.PRODUCT_ID, 4);
        // test1.setGenSteps(QuasiId.PRICE, 3);
        // test1.setGenSteps(QuasiId.EXPIRE_YEAR, 2);
        //		
        // GeneralizationSteps test2 = new GeneralizationSteps();
        // test2.setGenSteps(QuasiId.DEPT_ID, 11);
        //		
        // System.out.println(test);
        // System.out.println(test.viewStepsOnly());
        //
        // System.out.println(test.dominates(test0.getDataPairs())); // true
        // System.out.println(test.dominates(test1.getDataPairs())); // true
        // System.out.println(test.dominates(test2.getDataPairs())); // false

//         GeneralizationTable table = new GeneralizationTable(QuasiId.DEPT_ID, QuasiId.WEIGHT);
//         System.out.println(table);
        // GeneralizationSteps solution = new GeneralizationSteps();
        // solution.setGenSteps(QuasiId.DEPT_ID, 5);
        // solution.setGenSteps(QuasiId.PRODUCT_ID, 5);
        // solution.setGenSteps(QuasiId.WEIGHT, 5);
        // System.out.println(Generalizer.getGeneralizedData(solution));

//        Samarati.kanon( 0, 0, QuasiId.DEPT_ID, QuasiId.PRODUCT_ID );
//        Samarati sam = new Samarati( QuasiId.PRODUCT_ID, QuasiId.PRICE );
//        System.out.println(sam.latticeHeight);
        
        // Combinations Example
//        System.out.println(" Combinations Example ");
//        int maxSum = 3;
//        int numTerms = 2;
//        int cumSum = 0;
//        int[] indices1;
//        int[] elements1 = {3 , 2 , 1 , 0};
//        Vector<int[]> validCombinations = new Vector<int[]>();
//        
//        // Generate combinations
//        CombinationGenerator x1 = new CombinationGenerator( elements1.length, numTerms );
//        
//        int k = 0;
//        // iterate through possible combinations
//        while (x1.hasMore())
//        {
//            // returns an array 
//            indices1 = x1.getNext();
//            
//            for ( int i = 0; i < indices1.length; i++ )
//            {
//                // add the values
//                cumSum += elements1[indices1[i]];
////                combination.append( elements1[indices1[i]] );
//            }
//            
//            if ( cumSum == maxSum )
//            {
//                validCombinations.add( indices1.clone() );
//                for( int i = 0; i < numTerms ; i++ )
//                    System.out.print( validCombinations.elementAt(k)[i] );
//                System.out.println();
//                k++;
//            }
//            
//            cumSum = 0;
//        }
//        
//        
//        // From the validCombos we must add to the possible permutations
//        System.out.println(" Permutations Example ");
//        
//        Vector<int[]> possiblePermutations = new Vector<int[]>();
//        
//        for( int combination = 0; combination < validCombinations.size() ; combination++ )
//        {
//            int[] indices;
//            int[] elements = validCombinations.elementAt( combination );
//            
//            PermutationGenerator x = new PermutationGenerator( elements.length  );
//            
//            while (x.hasMore())
//            {
//                indices = x.getNext();
//                // Store actual values
//                possiblePermutations.add( new int[indices.length] );
//                for( int i = 0; i < indices.length ; i++ )
//                {
//                    possiblePermutations.lastElement()[i] = elements[indices[i]];
//                }
//            }
//        }
//        
//        // Print out all the permutations
//        for( int i = 0; i < possiblePermutations.size() ; i++ )
//        {
//            for( int j = 0; j < possiblePermutations.elementAt( i ).length ; j++)
//                System.out.print( possiblePermutations.elementAt( i )[j] + " ");
//            System.out.println();
//        }
//    }

}