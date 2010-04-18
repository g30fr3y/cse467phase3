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
    static final int PRODUCT_ID   = 0, 
                     PRICE        = 1, 
                     DEPT_ID      = 2, 
                     WEIGHT       = 3, 
                     PRODUCT_YEAR = 4, 
                     EXPIRE_YEAR  = 5;
    
    // TODO: Since this is a property of PRODUCT_ID, it may be moved to the enum stuff
    static final String PRODUCT_ID_MAX_GEN = "****";
    
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
        switch(id.position)
        {
            case PRODUCT_ID:
                attribute = generalizeProductID( attribute, infoLossLevels );
                break;
            case PRICE:
                break;
            case DEPT_ID:
                break;
            case WEIGHT:
                break;
            case PRODUCT_YEAR:
                break;
            case EXPIRE_YEAR:
                break;
        }
        return attribute;
    }

    /**
     * Generalizes productID a certain amount of times
     * @param numGeneralizations 
     *              Determines the number of generalizations to apply to productID 
     * @return Returns the generalized productID
     */
    private static String generalizeProductID(String productID, int numGeneralizations)
    {
        // If numGenerlizations is 0 or less then nothing will be generalized
        if ( numGeneralizations <= 0 )
            return productID;
        
        // Determine the length of the remaining substring
        int endIndex = (productID.length() - numGeneralizations) > 0 ? productID.length() - numGeneralizations : QuasiId.PRODUCT_ID.maxGeneralization ;
        
        if ( endIndex != QuasiId.PRODUCT_ID.maxGeneralization )
        {
            // Trim the string down to it's general form
            productID = productID.substring( 0, endIndex );
            
            // Ahh!! I got distracted! Back to coding :)
            
            // Append 'i' amount of asterisks to the general string
            for(int i = 0; i < (QuasiId.PRODUCT_ID.maxGeneralization - endIndex); i++)
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
     * This method takes two string attributes and generalizes each attribute
     * until the attributes match and then returns the number of times that the
     * attributes had to be generalized.
     * 
     * @param attribute1
     * @param attribute2
     * @param id
     * @return
     */
    public static int getNumGeneralization(String attribute1, String attribute2, QuasiId id)
    {
        // TODO: please make this one Jose
        return 0;
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
