// This enum is freaken hardcore!
public enum QuasiId
{
    
    // TODO: need to set these max gens to the correct value
    PRODUCT_ID(4, "`productID`", 0,9999), 
    PRICE(5, "`price`", 1, 99999), 
    DEPT_ID(7, "`deptID`", 2, 50), 
    WEIGHT(4, "`weight`", 3, 9),
    // PRODUCT_YEAR(2, "`productYear`", 4),
    // EXPIRE_YEAR(2, "`expireYear`", 5);
    // TODO: I think the maxGens are 3. 1980 -> 80s -> 19** -> ****
    PRODUCT_YEAR(3, "`productYear`", 4, 2010), 
    EXPIRE_YEAR(3, "`expireYear`", 5, 2015);

    final int maxGeneralization;
    final String dbName;
    final int position;
    final int maxValue;

    QuasiId(int maxGeneralization, String dbName, int position, int maxValue)
    {
        this.maxGeneralization = maxGeneralization;
        this.dbName = dbName;
        this.position = position;
        this.maxValue = maxValue;
    }

    public int getPosition()
    {
        return position;
    }

    public String getDBName()
    {
        return dbName;
    }

    public int getMaxGen()
    {
        return maxGeneralization;
    }
}