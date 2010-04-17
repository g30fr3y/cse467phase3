/**
 * Generalizes attributes of a single row or a cluster
 */


/**
 * @author Jose Trigueros
 *
 */
public class Generalizer
{
// Find a better way to create these global ID
    
    // Index names
    static final int PRODUCTID   = 0, 
                     PRICE       = 1, 
                     DEPTID      = 2, 
                     WEIGHT      = 3, 
                     PRODUCTYEAR = 4, 
                     EXPIREYEAR  = 5;
    /**
     * 
     * @param data A 2d array containing the rows that need to be generalized
     * @param attribute The name of the attribute to generalize
     * @return A 2d array containing the generalized data.
     */
    public static String[][] Generalize(String[][] data, int attribute)
    {
        // Maybe not needed
        String[][] generalizedData = data;
        
        switch(attribute)
        {
            case PRODUCTID:
                generalizeProductID(generalizedData);
                break;

        }
        
        return generalizedData;
    }
    
    public static String[][] generalizeProductID( String [][] generalizedData)
    {
        // Determine it's level of generalization
        
        return generalizedData;
    }
    
}
