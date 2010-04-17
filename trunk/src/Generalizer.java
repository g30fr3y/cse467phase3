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
    
    
    /**
     * This method takes two string attributes and generalizes each attribute 
     * until the attributes match and then returns the number of times that
     * the attributes had to be generalized.  
     * 
     * @param attribute1
     * @param attribute2
     * @param id
     * @return
     */
    public static int getNumGeneralization(String attribute1, String attribute2, QuasiId id) {
    	// TODO: please make this one Jose
    	return 0;
    }
    
    
    // just messing around here
    public static String getGeneralizedData(GeneralizationSteps solution) {
    	DBManager dbManager = new DBManager();
    	String output = "";
    	QuasiId[] enabledIds = solution.getEnabledQuasiIds();

    	String quasiIds = "";
    	for (QuasiId id : enabledIds) {
    		quasiIds += "," + id.getDBName();
    	}
    	quasiIds = quasiIds.substring(1);
    	
    	String[] data = dbManager.runQuery("SELECT " + quasiIds + " FROM Student");
    	
    	dbManager.closeConnection(false);
    	
    	for (int i = 0; i < data.length; i+=enabledIds.length) {
    		for (int j = 0; j < enabledIds.length; j++) {
    			String generalizedAttribute = generalizeAttribute(data[i+j], enabledIds[j], solution.getGenStepValue(enabledIds[j]));
    			output += generalizedAttribute + " ";
    		}
    		output += "\n";
    	}
    	return output;
    }
    
    public static String generalizeAttribute(String attribute, QuasiId id, int infoLossLevels) {
    	return attribute;
    }
    
}
