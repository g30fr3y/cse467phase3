/**
 * Generalizes attributes 
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
     * @param attributes An array containing the IDs of the attributes to generalize  
     * @return A 2d array containing the generalized data.
     */
    public static String[][] Generalize(String[][] data, int[] attributes)
    {
        // Maybe not needed
        String[][] generalizedData = data;
        
        for( int attribute = 0 ; attribute < attributes.length ; attribute++ )
        {
            switch(attributes[attribute])
            {
                case PRODUCTID:
                    // I'm stopping here
                    break;
                    
            }
        }
        
        return generalizedData;
    }
    
}
