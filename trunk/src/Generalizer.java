/**
 * Generalizes attributes of a single row or a cluster
 */

/**
 * @author Jose Trigueros
 * 
 */
public class Generalizer
{

    // Index names, needed to do this nonetheless
    static final int PRODUCT_ID = 0, 
                     PRICE = 1, 
                     DEPT_ID = 2, 
                     WEIGHT = 3, 
                     PRODUCT_YEAR = 4, 
                     EXPIRE_YEAR = 5;

    // TODO: Since this is a property of PRODUCT_ID, it may be moved to the enum
    // stuff
    static final String PRODUCT_ID_MAX_GEN = "****";
    static final String WEIGHT_MAX_GEN = "<0-9>";
    static final String YEAR_MAX_GEN = "****";
    
    static final double WEIGHT_CLUSTER_FACTOR = 2.0;
    static final double DEPT_ID_CLUSTER_FACTOR = WEIGHT_CLUSTER_FACTOR;
    static final double PRICE_CLUSTER_FACTOR = 10.0;

    // Find a better way to create these global ID
    /**
     * This method takes care of generalizing a given attribute a certain amount
     * of levels
     * 
     * @param attribute
     *            The attribute that needs to be generalized.
     * @param id
     *            Determines what kind of generalization to perform.
     * @param infoLossLevels
     *            The number of levels to generalize.
     */
    public static String generalize(String attribute, QuasiId id, int infoLossLevels)
    {
        // Select the kind of generalization to perform
        switch (id.position)
        {
            case PRODUCT_ID:
                attribute = generalizeProductID( attribute, infoLossLevels );
                break;
            case PRICE:
                attribute = generalizePrice( attribute, infoLossLevels );
                break;
            case DEPT_ID:
                attribute = generalizeDeptID( attribute, infoLossLevels );
                break;
            case WEIGHT:
                attribute = generalizeWeight( attribute, infoLossLevels );
                break;
            case PRODUCT_YEAR:
                attribute = generalizeProductYear( attribute, infoLossLevels );
                break;
            case EXPIRE_YEAR:
                attribute = generalizeExpireYear( attribute, infoLossLevels );
                break;
        }
        return attribute;
    }

    /**
     * Generalizes productID a certain amount of times
     * 
     * @param numGeneralizations
     *            Determines the number of generalizations to apply to productID
     * @return Returns the generalized productID
     */
    private static String generalizeProductID(String productID, int numGeneralizations)
    {
        // If numGenerlizations is 0 or less then nothing will be generalized
        if (numGeneralizations <= 0)
            return productID;

        // Determine the length of the remaining substring
        int endIndex = (productID.length() - numGeneralizations) > 0 ? productID.length() - numGeneralizations
                : QuasiId.PRODUCT_ID.maxGeneralization;

        if (endIndex != QuasiId.PRODUCT_ID.maxGeneralization)
        {
            // Trim the string down to it's general form
            productID = productID.substring( 0, endIndex );

            // Ahh!! I got distracted! Back to coding :)

            // Append 'i' amount of asterisks to the general string
            for ( int i = 0; i < (QuasiId.PRODUCT_ID.maxGeneralization - endIndex); i++ )
            {
                productID += "*";
            }
        }
        else
        {
            productID = PRODUCT_ID_MAX_GEN;
        }

        return productID;
    }

    /**
     * Generalizes price a certain amount of times
     * 
     * @return Returns the generalized price
     */
    private static String generalizePrice(String price, int numGeneralizations)
    {
        return generalizeRanges( price, PRICE_CLUSTER_FACTOR, numGeneralizations, QuasiId.PRICE );
    }

    /**
     * Generalizes deptID a certain amount of times
     * 
     * @return Returns the generalized deptID
     */
    private static String generalizeDeptID(String deptID, int numGeneralizations)
    {
        return generalizeRanges( deptID, DEPT_ID_CLUSTER_FACTOR, numGeneralizations, QuasiId.DEPT_ID );
    }

    /**
     * Generalizes weight a certain amount of times
     * 
     * @return Returns the generalized weight
     */
    private static String generalizeWeight(String weight, int numGeneralizations)
    {
        //TODO: Not that ez to implement...but I did it!
        return generalizeRanges( weight, WEIGHT_CLUSTER_FACTOR, numGeneralizations, QuasiId.WEIGHT );
    }
    
    private static String generalizeRanges(String range , double clusterFactor, int numGeneralizations, QuasiId id)
    {
        if ( numGeneralizations <= 0)
            return range;
        
        if( numGeneralizations < id.maxGeneralization )
        {
            // Determine cluster size for the range
            int groupSize = (int)Math.pow( clusterFactor, numGeneralizations );
            
            // Find the hashing location of the corresponding value
            int hashValue = (int)(Integer.parseInt( range )/groupSize);
            
            // Bounds for range
            int lowerBound = hashValue*groupSize;
            String upperBound = (lowerBound + ( lowerBound - 1 )) < id.maxValue ? lowerBound + ( groupSize - 1) + "" : "*";
            
            range = "<" + lowerBound + "-" + upperBound + ">";
        }
        else
        {
            range = "<0-" + id.maxValue + ">";
        }
        
        return range;
    }
    
    /**
     * Generalizes productYear a certain amount of times
     * 
     * @return Returns the generalized productYear
     */
    private static String generalizeProductYear(String productYear, int numGeneralizations)
    {
        return generalizeYear( productYear, numGeneralizations, QuasiId.PRODUCT_YEAR );
    }

    /**
     * Generalizes expireYear a certain amount of times
     * 
     * @return Returns the generalized expireYear
     */
    private static String generalizeExpireYear(String expireYear, int numGeneralizations)
    {
        // TODO: What is supposed to happen if expireYear is empty, or 0 in our
        // case
        return generalizeYear( expireYear, numGeneralizations, QuasiId.EXPIRE_YEAR );
    }

    /**
     * Generalizes year a certain amount of times, this is a template used for
     * expireYear and productYear
     * 
     * @return Returns the generalized year
     */
    private static String generalizeYear(String year, int numGeneralizations, QuasiId id)
    {
        if (numGeneralizations <= 0)
            return year;

        if (numGeneralizations < id.maxGeneralization)
        {
            // I guess the only way to do this is to brute force it
            switch (numGeneralizations)
            {
                // So if you have 1992 -> 90s
                case 1:
                    year = year.charAt( 2 ) + "0s";
                    break;
                // So if you have 1992 -> 19**
                case 2:
                    year = year.substring( 0, 2 ) + "**";
            }

        }
        else
        {
            year = YEAR_MAX_GEN;
        }

        return year;
    }

    /**
     * This method takes two string attributes and generalizes each attribute
     * until the attributes match and then returns the number of times that the
     * attributes had to be generalized.
     * 
     * @return Returns the number of times it takes for the two attributes to be generalized.
     */
    public static int getNumGeneralization(String attribute1, String attribute2, QuasiId id)
    {
        // TODO: please make this one Jose
        
        int numGeneralizations = 0;
        // First check obvious scenario
        if( attribute1.equals( attribute2 ) )
            return numGeneralizations;
        
        // Now iterate through the generalizations until both match
        for ( int i = 1; i <= id.maxGeneralization ; i++ )
        {
            numGeneralizations++;
            if( generalize( attribute1, id, i ).equals( generalize( attribute2, id, i ) ) )
                break; // It's a match!
        }
        
        return numGeneralizations;
    }
    
    // just messing around here
    public static String getGeneralizedData(GeneralizationSteps solution)
    {
        DBManager dbManager = new DBManager();
        String output = "";
        QuasiId[] enabledIds = solution.getEnabledQuasiIds();

        String quasiIds = "";
        for ( QuasiId id : enabledIds )
        {
            quasiIds += "," + id.getDBName();
        }
        quasiIds = quasiIds.substring( 1 );

        String[] data = dbManager.runQuery( "SELECT " + quasiIds + " FROM Student" );

        dbManager.closeConnection( false );

        for ( int i = 0; i < data.length; i += enabledIds.length )
        {
            for ( int j = 0; j < enabledIds.length; j++ )
            {
                String generalizedAttribute = generalize( data[i + j], enabledIds[j], solution
                        .getGenStepValue( enabledIds[j] ) );
                output += generalizedAttribute + " ";
            }
            output += "\n";
        }
        return output;
    }

}
