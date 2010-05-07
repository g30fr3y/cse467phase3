import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashSet;
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

    /**
     * Performs Samarati's algorithm
     * 
     * @param kAnon
     *            The number of k-anonymous elements allowed
     * @param maxSup
     *            The number of allowed element suppression
     * @param list
     *            A list of the Quasi-Identifiers that need to be sanitized for release
     * @return
     */
    public static String[][] kanon(int kAnon, int maxSup, QuasiId... list)
    {
    	// Added to optimize the return time of k = 1.
    	// If k = 1, then we're just returning all data
    	// untouched (or ungeneralized)
    	if (kAnon < 2) {
    		GeneralizationSteps simpleSolution = new GeneralizationSteps();
    		for (QuasiId id : list) {
    			simpleSolution.setGenSteps(id, 0);
    		}
    		return Generalizer.getGeneralizedDataArray( simpleSolution , null);
    	}

        // Create a Samarati object and initialize the quasi-id(s)
        Samarati sam = new Samarati( list );

        // Create a generalization table with the k-anonymity and maximum suppression values.
        GeneralizationTable genTable = new GeneralizationTable( kAnon, list );

        // These will hold the best and current solution
        GeneralizationSteps bestSolution = null;
        GeneralizationSteps currentSolution = null;

        // Create a solution set, that means the values to be tested for example [1,1,0]
        int[][] solutionSet = null;

        // Solution flag
        boolean isSolution = false;

        // These will denote the level of where the algorithm should look and the boundaries
        int currentLatticeLevel = 0;
        int maxHeight = sam.latticeHeight;
        int lowestHeight = 0;

        // this is faster...
        ComboGeneratorTest combos = new ComboGeneratorTest(list);
        
        // Doing BinarySearch
        while (lowestHeight < maxHeight)
        {
            // Allocate new memory for the current solution
            currentSolution = new GeneralizationSteps();

            // Set the middle point
            currentLatticeLevel = lowestHeight + (maxHeight - lowestHeight) / 2;
            
            solutionSet = combos.getCombosAt(currentLatticeLevel);

            // Iterate through all solutions
            for ( int currentSol = 0; currentSol < solutionSet.length; currentSol++ )
            {
                // Add the solution set to GeneralizationSteps
                for ( int currentQuasi = 0; currentQuasi < list.length; currentQuasi++ )
                {
                    currentSolution.setGenSteps( list[currentQuasi], solutionSet[currentSol][currentQuasi] );
                }

                // Check if it is true
                isSolution = genTable.testSolution( currentSolution, maxSup );

                // If we found a solution stop looking in this lattice level
                if (isSolution)
                {
                    // Copy the current solution as the best solution
                    bestSolution = currentSolution;
                    break;
                }
            }

            // If we have a solution, look below the lattice
            if (isSolution)
            {
                maxHeight = currentLatticeLevel;
            }
            // Otherwise looking in the bottom half of the lattice
            else
            {
                lowestHeight = currentLatticeLevel + 1;
            }

        }
        
        // the highest level of generalization is the only solution
        if (currentLatticeLevel == combos.getHeight()-1) {
    		bestSolution = new GeneralizationSteps();
        	for (QuasiId id : list) {
            	bestSolution.setGenSteps(id, id.maxGeneralization);
        	}
        }

        // Returns a string with the solution
        return Generalizer.getGeneralizedDataArray( bestSolution,  genTable.getAttributesToGeneralize(bestSolution));
    }

    private static void printSolution(int[] solutionSet)
    {
        for ( int i = 0; i < solutionSet.length; i++ )
            System.out.print( solutionSet[i] + " " );
        System.out.println();
    }

    /**
     * The lattice isn't needed, but we still need to know the number of levels (or height) of the lattice. This method
     * uses a simple algorithm to determine the height of the lattice. The max generalizations possible for each
     * attribute is needed to calculate the height.
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

    private static Vector<int[]> removeDuplicates( Vector<int[]> vector)
    {
        for ( int i = 0; i < vector.size(); i++ )
        {
            for ( int j = 0; j < vector.size() ; j++ )
            {
                if( (i != j) && Arrays.equals( vector.elementAt( i ),vector.elementAt( j ) ) )
                {
                    vector.remove( j-- );
                }
            }
        }
        return vector;
    }
}
