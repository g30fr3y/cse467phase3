import java.util.Vector;

/*
 * Here are the general steps to accomplish Samarati's algorithm:
 * 1) Get quasi-identifiers, maximum suppression value, k
 *    anonymity value.
 * 2) Get maximum generalization steps for each quasi-identifier.
 * 3) Use those values from step 2 to get the lattice height.
 * 4) Create a generalization table.
 * 5) Perform the actual binary search portion of the algorithm.
 */
public class Samarati
{
    
    // Private Global Variables
    private int latticeHeight;
    private QuasiId[] quasiIdList;

    public Samarati(QuasiId... list)
    {
        quasiIdList = list;
        setLatticeHeight( list );
    }

    // TODO: A lot more work is needed here, work in progress...
    // This is the method required in the instructions...
    public static String[][] kanon(int kAnon, int maxSup, QuasiId ... list)
    {

        // Create a Samarati object and initialize the quasi-id(s)
        Samarati sam = new Samarati( list );

        
        // Create a generalization table with the
        // k-anonymity and maximum suppression values.
        GeneralizationTable genTable = new GeneralizationTable( list );
        GeneralizationSteps solution = new GeneralizationSteps();

        
        // Determine the halfway point in the lattice for all of the QuasiIds
        // and make that the initial solution testing
        int currentLatticeLevel = sam.latticeHeight/2;
        
        // Create a solution set
        int[][] solutionSet = null;

        // Binary Search 
        boolean isBinarySearch = true;
        boolean isSolution = false;
        int maxHeight = sam.latticeHeight;
        int lowestHeight = 0;
        int heightBuffer = -1;
        
        // Doing BinarySearch
        while (isBinarySearch)
        {
            // Did we find a solution?
            if( currentLatticeLevel == maxHeight || currentLatticeLevel == lowestHeight )
                isBinarySearch = false;
            // Obtain solution permutations given height and list.length
            solutionSet = createPossibleSolutions( currentLatticeLevel, list.length );
            
            // Iterate through all solutions
            for( int currentSol = 0 ; currentSol < solutionSet.length ; currentSol++)
            {
                // Add the solution set to GeneralizationSteps
                for( int currentQuasi = 0; currentQuasi < list.length ; currentQuasi++)
                {
                    solution.setGenSteps( list[currentQuasi], solutionSet[currentSol][currentQuasi] );
                }
                
                // Check if it is true
                isSolution = genTable.testSolution( solution, kAnon, maxSup, false );
                // If we found a solution stop looking in this lattice level
                if ( isSolution )
                {
//                    printSolution( solutionSet[currentSol] );
                    break;
                }
//                else
//                    solution = new GeneralizationSteps();
            }
            
            
            // did you find it? wash, rinse, repeat...
            // Adjust variables according to results
            if( isSolution )
            {
                // Go down the lattice
                maxHeight = currentLatticeLevel;
                heightBuffer = (int)Math.ceil( (maxHeight - lowestHeight)/2.0 );
                currentLatticeLevel = currentLatticeLevel - heightBuffer ;
            }
            else
            {
                // Go up the lattice
                heightBuffer = currentLatticeLevel;
                currentLatticeLevel = (maxHeight - lowestHeight)/2 + currentLatticeLevel;
                lowestHeight = heightBuffer;
            }
            
        }

        // Return a string representation of the generalized data
        return Generalizer.getGeneralizedDataArray( solution );
    }

    private static void printSolution( int[] solutionSet )
    {
        for( int i = 0 ; i < solutionSet.length; i++ )
            System.out.print( solutionSet[i] + " " );
        System.out.println();
    }
    
    /**
     * The lattice isn't needed, but we still need to know the number of levels
     * (or height) of the lattice. This method uses a simple algorithm to
     * determine the height of the lattice. The max generalizations possible for
     * each attribute is needed to calculate the height.
     */
    private void setLatticeHeight(QuasiId... list)
    {
        int levels = 0;

        for ( QuasiId id : list )
        {
            levels = levels + (id.getMaxGen() - 1);
        }
        this.latticeHeight = levels + 1;
    }

    private static int[][] createPossibleSolutions( int latticeLocation, int numTerms )
    {
        
        // First determine the number of combinations
        Vector<int[]> validCombos = new Vector<int[]>();
        
        // Create element list
        int[] elements = new int[latticeLocation + 1]; 
        for( int i = 0; i <= latticeLocation; i++)
            elements[i] = i;
        
        
        // Generate combination object
        CombinationGenerator comboGen = new CombinationGenerator( elements.length, numTerms );
        
        // Create combinations
        while( comboGen.hasMore() )
        {
            // Get the indices of the possible combination
            int[] indices = comboGen.getNext();
            int cumSum = 0;
            
            // Check if the sum of this combination adds up to the height of the lattice
            for ( int i = 0; i < indices.length ; i++ )
            {
                cumSum += elements[indices[i]];
            }
            
            // If the sum of the permutation adds up to the lattice height then we keep it
            if( cumSum == latticeLocation )
            {
                // Add a copy of that array to the list
                validCombos.add( indices.clone() );
            }
        }
        
        // Now that we have a list of valid combinations we permute them 
        Vector<int[]> validPermutations = new Vector<int[]>();
        
        // Iterate through the elements in the validCombos
        for( int i = 0; i < validCombos.size() ; i++)
        {
            // The elements are that of the first entry in the vector validCombos
            int[] comboElements = validCombos.elementAt( i );
            // Create permutation object
            PermutationGenerator permGen = new PermutationGenerator( comboElements.length );
            
            while( permGen.hasMore() )
            {
                int[] indeces = permGen.getNext();
                
                // Store the values of the permutation
                validPermutations.add( new int[indeces.length] );
                for( int j = 0; j < indeces.length; j++ )
                {
                    validPermutations.lastElement()[j] = comboElements[indeces[j]];
                }
            }
            
        }
        int[][] solutionSet = new int[validPermutations.size()][];
        int i = 0;
        // Convert Vector to an array
        for( int[] array : validPermutations )
        {
            solutionSet[i++] = array.clone();
        }
        
        return solutionSet;
    }
}
